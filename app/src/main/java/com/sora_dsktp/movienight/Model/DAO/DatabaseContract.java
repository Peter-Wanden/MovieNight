/*
 * Copyright © 2018 by Georgios Kostogloudis
 * All rights reserved.
 */

package com.sora_dsktp.movienight.Model.DAO;

import android.net.Uri;
import android.provider.BaseColumns;

import com.sora_dsktp.movienight.BuildConfig;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 23/3/2018.
 * The name of the project is MovieNight and it was created as part of
 * UDACITY ND programm.
 */

/**
 * This class define's the contract between the content provider and the local
 * SQlite database.
 */
public class DatabaseContract
{
    //Log tag for LogCat usage
    private final String DEBUG_TAG = "#" + getClass().getSimpleName();

    private DatabaseContract()  { }


    // The authority, which is how your code knows which Content Provider to access
    public static final String AUTHORITY = "com.sora_dsktp.movienight.Model.DAO.FavouritesContentProvider";

    // The base content URI = "content://" + <authority>
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    // Define the possible paths for accessing data in this contract
    // This is the path for the "favourite_movies" directory
    public static final String PATH_FAVOURITE_MOVIES = "favourite_movies";


    public static final class FavouriteMovies implements BaseColumns
    {

        //  content URI = base content URI + path
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVOURITE_MOVIES).build(); // This URI defines the URI to fetch movies from the database

        public static final String TABLE_NAME = "fav_movies";
        public static final String COLUMN_MOVIE_TITLE = "movie_title";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_MOVIE_RATING = "movie_rating";
        public static final String COLUMN_MOVIE_DESCRIPTION = "movie_description";
        public static final String COLUMN_POSTER_PATH = "movie_poster_path";
        public static final String COLUMN_MOVIE_ID = "movie_id"; // not the primary id just the movie ID from the Movies API DB
    }


}
