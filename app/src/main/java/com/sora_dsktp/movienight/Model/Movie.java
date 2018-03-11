/*
 * Copyright © 2018 by Georgios Kostogloudis
 * All rights reserved.
 */

package com.sora_dsktp.movienight.Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.os.ParcelableCompatCreatorCallbacks;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 This file created by Georgios Kostogloudis on 23/2/2018
 and was last modified on 23/2/2018.
 The name of the project is MovieNight and it was created as part of
 UDACITY ND programm.
 */

/**
 * This is the model we use
 * to represent the movies we get from the
 * API
 */
public class Movie implements Parcelable {

    @SerializedName("vote_average")
    private float movieRating;
    @SerializedName("title")
    private String movieTitle;
    @SerializedName("poster_path")
    private String imagePath;
    @SerializedName("overview")
    private String movieDescription;
    @SerializedName("release_date")
    private String releaseDate;

    /**
     * Default Movie constructor
     * @param movieRating represents the voting average of the movie
     * @param movieTitle represents the movie title
     * @param imagePath represents the path to the movie poster
     * @param movieDescription represents a movie synopsis
     * @param releaseDate represents the release date of the movie
     */
    public Movie(int movieRating, String movieTitle, String imagePath, String movieDescription, String releaseDate) {
        this.movieRating = movieRating;
        this.movieTitle = movieTitle;
        this.imagePath = imagePath;
        this.movieDescription = movieDescription;
        this.releaseDate = releaseDate;
    }

    /**
     *
     * @return returns a represantion of the movie object as a string
     */
    @Override
    public String toString() {
        return "This movie has a title: " +
                getMovieTitle() +
                " With a rating of " +
                getMovieRating() +
                " and the following description: " +
                getMovieDescription() +
                " and it was release on:" +
                getReleaseDate();
    }

    public float getMovieRating() {
        return movieRating;
    }

    public void setMovieRating(float movieRating) {
        this.movieRating = movieRating;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getMovieDescription() {
        return movieDescription;
    }

    public void setMovieDescription(String movieDescription) {
        this.movieDescription = movieDescription;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * This method is used to write a movie object into a Parcel object
     * so we can pass later on our activity a movie object via intent.passExtra()
     * @param out the Parcel object to write our object values
     * @param flags any flags we want to add
     */
    public void writeToParcel(Parcel out, int flags) {
        out.writeFloat(getMovieRating());
        out.writeString(getMovieTitle());
        out.writeString(getMovieDescription());
        out.writeString(getImagePath());
        out.writeString(getReleaseDate());

    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>()
    {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    /**
     * This Movie constructor creates a Movie object from  a Parcel object as a parameter
     * and read's the values for a Movie object in the same
     * order we wrote in writeToParcel() method
     * @param in the Parcel object that contains an Movie object
     */
    private Movie(Parcel in) {
        setMovieRating(in.readFloat());
        setMovieTitle(in.readString());
        setMovieDescription( in.readString());
        setImagePath(in.readString());
        setReleaseDate( in.readString());
    }
}
