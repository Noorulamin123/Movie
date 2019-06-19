package com.example.android.movie;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

public class MovieLoader extends AsyncTaskLoader {
    public static final String LOG_TAG = MovieLoader.class.getName();
    private String mUrl;

    public MovieLoader(@NonNull Context context,String url) {
        super(context);
        mUrl = url;
    }

    @Nullable
    @Override
    public List<Movies> loadInBackground() {
        if (mUrl == null){
            return null;
        }
        List<Movies> moviesList = QueryUtils.fetchMovieData(mUrl);
        return  moviesList;
    }
}
