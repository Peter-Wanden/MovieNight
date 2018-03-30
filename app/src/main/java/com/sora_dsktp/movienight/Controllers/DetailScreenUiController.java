/*
 * Copyright © 2018 by Georgios Kostogloudis
 * All rights reserved.
 */

package com.sora_dsktp.movienight.Controllers;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 29/3/2018.
 * The name of the project is MovieNight and it was created as part of
 * UDACITY ND programm.
 */


import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.widget.Toast;

import com.sora_dsktp.movienight.Model.DatabaseContract;
import com.sora_dsktp.movienight.Model.Movie;
import com.sora_dsktp.movienight.R;
import com.sora_dsktp.movienight.Screens.DetailsScreen;

import static com.sora_dsktp.movienight.BroadcastReceivers.DbBroadcastReceiver.ACTION_DATABASE_CHANGED;

/**
 * Helper class containing methods to update the UI
 * in Detail Screen
 */
public class DetailScreenUiController
{
    private final DetailsScreen mDetailScreen;
    private final AsyncQueryHelper mQueryHelper;
    private boolean mIsFavourite = false;
    private final String DEBUG_TAG = getClass().getSimpleName();



    /**
     * Default constructor
     * @param detailsScreen the DetailScreen object
     */
    public DetailScreenUiController(DetailsScreen detailsScreen)
    {
        this.mDetailScreen = detailsScreen;
        mQueryHelper = new AsyncQueryHelper(mDetailScreen.getContentResolver());
    }

    public void addTheMovieToTheDatabase(Movie movieClicked)
    {
        // Create content values to put the movie object inside
        final ContentValues cv = new ContentValues();

        cv.put(DatabaseContract.FavouriteMovies.COLUMN_MOVIE_TITLE, movieClicked.getMovieTitle());
        cv.put(DatabaseContract.FavouriteMovies.COLUMN_RELEASE_DATE, movieClicked.getReleaseDate());
        cv.put(DatabaseContract.FavouriteMovies.COLUMN_MOVIE_DESCRIPTION, movieClicked.getMovieDescription());
        cv.put(DatabaseContract.FavouriteMovies.COLUMN_MOVIE_RATING, movieClicked.getMovieRating());
        cv.put(DatabaseContract.FavouriteMovies.COLUMN_POSTER_PATH, movieClicked.getImagePath());
        // Save the movie to the database using the Content uri with the contentValues

        //Database operation must be on a background thread
        mQueryHelper.startInsert(2,null, DatabaseContract.FavouriteMovies.CONTENT_URI,cv);
    }

    public void deleteTheMovieFromDatabase(Movie movieClicked)
    {
        final String selection = DatabaseContract.FavouriteMovies.COLUMN_MOVIE_TITLE + "=?";
        final String[] selectionArgs = new String[]{movieClicked.getMovieTitle()};

        // Database operation must run on a background thread
        mQueryHelper.startDelete(-1,null,DatabaseContract.FavouriteMovies.CONTENT_URI,selection,selectionArgs);
    }


    /**
     * Helper class to query asynchronously the database
     */
    private class AsyncQueryHelper extends AsyncQueryHandler
    {

        public AsyncQueryHelper(ContentResolver cr) {
            super(cr);
        }

        @Override
        protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
            super.onQueryComplete(token, cookie, cursor);

            Movie movieClciked = (Movie) cookie;
            if (cursor.getCount() > 0) // if the cursor has results that means that the movie is favourite
            {
                Log.d(DEBUG_TAG,"Query completed and a movie found in the database with title = " + movieClciked.getMovieTitle());
                paintTheHeartButton();
                mIsFavourite = true;
            }
            else Log.d(DEBUG_TAG,"Query completed found no movie with that title = " + movieClciked.getMovieTitle());

        }

        @Override
        protected void onInsertComplete(int token, Object cookie, Uri uri) {
            super.onInsertComplete(token, cookie, uri);

            if( uri != null)
            {
                //Added successfully
                Toast.makeText(mDetailScreen.getApplication(),"Added to your favourite movies",Toast.LENGTH_SHORT).show();
                //Paint the heart button to red
                paintTheHeartButton();
                mIsFavourite = true;
            }
        }

        @Override
        protected void onDeleteComplete(int token, Object cookie, int itemsDeleted) {
            super.onDeleteComplete(token, cookie, itemsDeleted);

            if(itemsDeleted == 1) // the itemsDeleted must equal to 1 otherwise something has gone wrong
            {
                //successfully removed from the favourites
                mIsFavourite = false;
                // inform the user using a toast message
                Toast.makeText(mDetailScreen.getApplicationContext(),"Removed from favourites",Toast.LENGTH_SHORT).show();
                unPaintTheHeartButton(); // call this method to unpaint the heart to indicate that the movie is not favourite anymore
                //send broadcast to update the UI
                Intent intent = new Intent(ACTION_DATABASE_CHANGED);
                // put in extra the movie object to delete with the adapter position of the movie
                // to the recyclerView.
                intent.putExtra(mDetailScreen.getResources().getString(R.string.EXTRA_MOVIE_TO_DEL_OBJ), mDetailScreen.getmMovieClicked());
                intent.putExtra(mDetailScreen.getResources().getString(R.string.EXTRA_KEY_MOVIE_ID), mDetailScreen.getmMovieAdapterPosition());
                // send a broadcast that the database has been changed
                mDetailScreen.sendBroadcast(intent);
            }
        }
    }

    /**
     * Helper method to check if this movie is already a favourite by checking
     * the local database using a mQuery Helper startQuery() method
     */
    public void checkTheMovieOnDatabase(Movie movieClicked)
    {
        final String selection = DatabaseContract.FavouriteMovies.COLUMN_MOVIE_TITLE + "=?";
        final String [] SelectionArgs = new String[]{movieClicked.getMovieTitle()};
        // execute the query in a background thread
        mQueryHelper.startQuery(1,movieClicked, DatabaseContract.FavouriteMovies.CONTENT_URI,null,selection,SelectionArgs,null);

    }



    /**
     * Helper method that get's a reference to the imageButton
     * and set's the color to RED
     */
    public void paintTheHeartButton()
    {
        //mark the movie as favourite
        //by painting the button
        FloatingActionButton button = mDetailScreen.findViewById(R.id.fab_favourite);
        button.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
        button.setBackgroundTintMode(null);
    }

    /**
     * Helper method that get's a reference to the imageButton
     * and set's the color to TRANSPARENT
     */
    public void unPaintTheHeartButton()
    {
        //mark the movie as not favourite
        //by repainting the heart button
        FloatingActionButton button = mDetailScreen.findViewById(R.id.fab_favourite);
        button.setBackgroundTintList(ColorStateList.valueOf(Color.TRANSPARENT));
        button.setBackgroundTintMode(null);
    }

    public boolean getIsFavourite() {
        return mIsFavourite;
    }


}