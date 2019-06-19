package com.example.android.movie;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CatalogueActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Movies>> {

    public static final String LOG_TAG = CatalogueActivity.class.getName();
    private static final int MOVIE_LOADER_ID = 1;
    public static final String LIST_MOVIE_URL = "https://yts.am/api/v2/list_movies.json";
    private MovieAdapter movieAdapter;
    private TextView mEmptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogue);
        final ListView movieListView = (ListView)findViewById(R.id.list_movie);
        mEmptyStateTextView = (TextView)findViewById(R.id.empty_view);
        movieListView.setEmptyView(mEmptyStateTextView);
        movieAdapter = new MovieAdapter(this,new ArrayList<Movies>());
        movieListView.setAdapter(movieAdapter);

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if (isConnected){
            LoaderManager loaderManager = getSupportLoaderManager();
            loaderManager.initLoader(MOVIE_LOADER_ID,null,this);
        }
        else {
            mEmptyStateTextView.setText(getText(R.string.no_connection));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movie_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.search:
                search();
                return true;
            case R.id.signin:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void search() {
        Intent intent = new Intent(CatalogueActivity.this,SearchActivity.class);
        startActivity(intent);

    }

    @NonNull
    @Override
    public Loader<List<Movies>> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new MovieLoader(this,LIST_MOVIE_URL);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Movies>> loader, List<Movies> movies) {
        mEmptyStateTextView.setText(R.string.no_earthquakes);
        movieAdapter.clear();
        if (movies != null && !movies.isEmpty()) {
            movieAdapter.addAll(movies);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Movies>> loader) {
        movieAdapter.clear();
    }

}
