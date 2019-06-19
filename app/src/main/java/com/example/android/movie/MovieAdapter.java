package com.example.android.movie;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MovieAdapter extends ArrayAdapter <Movies>{
    public MovieAdapter(Context context, ArrayList<Movies> movies) {
        super(context,0, movies);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_movie, parent, false);
        }
        Movies currentMovie = getItem(position);

        TextView titleView = (TextView)listItemView.findViewById(R.id.title);
        titleView.setText(currentMovie.getMovieTitle());

        TextView yearView = (TextView)listItemView.findViewById(R.id.year);
        yearView.setText(currentMovie.getMovieYear());

        TextView ratingView = (TextView)listItemView.findViewById(R.id.rating);
        ratingView.setText(Float.toString(currentMovie.getMovieRating()));

        TextView genereView = (TextView)listItemView.findViewById(R.id.genere);
        genereView.setText(currentMovie.getMovieGenere());

        ImageView imageView = (ImageView) listItemView.findViewById(R.id.image);
        Glide.with(imageView.getContext()).load(currentMovie.getMovieImageUrl())
                .into(imageView);
        return  listItemView;
    }

}
