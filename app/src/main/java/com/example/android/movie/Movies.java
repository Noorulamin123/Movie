package com.example.android.movie;

import java.util.ArrayList;

class Movies {
    private long movieID;
    private String movieImageUrl;
    private String movieTitle;
    private float movieRating;
    private String movieImdbCode;
    private String movieYoutubeTrailerCode;
    private int movieYear;
    private int movieRuntime;
    private String movieLanguage;
    private String movieDescription;
    private String movieTorrent720P;
    private String movieTorrent1080P;
    private String movieTorrent3D;
    private ArrayList<String> movieGenere;



    public Movies(long movieID, String movieImageUrl, String movieTitle, float movieRating, String movieImdbCode, String movieYoutubeTrailerCode, int movieYear, int movieRuntime, String movieLanguage, String movieDescription, String movieTorrent720P, String movieTorrent1080P, String movieTorrent3D, ArrayList<String> movieGenere) {
        this.movieID = movieID;
        this.movieImageUrl = movieImageUrl;
        this.movieTitle = movieTitle;
        this.movieRating = movieRating;
        this.movieImdbCode = movieImdbCode;
        this.movieYoutubeTrailerCode = movieYoutubeTrailerCode;
        this.movieYear = movieYear;
        this.movieRuntime = movieRuntime;
        this.movieLanguage = movieLanguage;
        this.movieDescription = movieDescription;
        this.movieTorrent720P = movieTorrent720P;
        this.movieTorrent1080P = movieTorrent1080P;
        this.movieTorrent3D = movieTorrent3D;
        this.movieGenere = movieGenere;
    }

    public long getMovieID() {
        return movieID;
    }

    public String getMovieImageUrl() {
        return movieImageUrl;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public float getMovieRating() {
        return movieRating;
    }

    public String getMovieImdbCode() {
        return movieImdbCode;
    }

    public String getMovieYoutubeTrailerCode() {
        return movieYoutubeTrailerCode;
    }

    public int getMovieYear() {
        return movieYear;
    }

    public int getMovieRuntime() {
        return movieRuntime;
    }

    public String getMovieLanguage() {
        return movieLanguage;
    }

    public String getMovieDescription() {
        return movieDescription;
    }

    public String getMovieTorrent720P() {
        return movieTorrent720P;
    }

    public String getMovieTorrent1080P() {
        return movieTorrent1080P;
    }

    public String getMovieTorrent3D() {
        return movieTorrent3D;
    }

    public String getMovieGenere() {
        String MovieGenere ="";
        int i=0;
        while(!movieGenere.isEmpty()){
            MovieGenere = MovieGenere + " " + movieGenere.get(i);
        }
        return MovieGenere;
    }
}
