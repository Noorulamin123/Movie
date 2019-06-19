package com.example.android.movie;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getName();
    private QueryUtils() {
    }


    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the movie JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    public static List<Movies> fetchMovieData(String mUrl) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Create URL object
        URL url = createUrl(mUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link Earthquake}s
        List<Movies> moviesList = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link Earthquake}s
        return moviesList;
    }

    private static List<Movies> extractFeatureFromJson(String jsonResponse) {

        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }

        List<Movies> moviesList = new ArrayList<>();
        try{
            JSONObject data = new JSONObject(jsonResponse).getJSONObject("data");
            JSONArray movies = data.getJSONArray("movies");

            for (int i=0;i<movies.length();i++){
                JSONObject movie = movies.getJSONObject(i);
                long id = movie.getLong("id");
                String imageUrl = movie.getString("medium_cover_image");
                String title = movie.getString("title");
                float rating = (float) movie.getDouble("rating");
                String imdbCode = movie.getString("imdb_code");
                String ytTrailerCode = movie.getString("yt_trailer_code");
                int year = movie.getInt("year");
                int runtime = movie.getInt("runtime");
                String language = movie.getString("language");
                String description = movie.getString("description_full");
                String torrent720P = movie.getJSONArray("torrents").getJSONObject(0).getString("url");
                String torrent1080P = movie.getJSONArray("torrents").getJSONObject(1).getString("url");
                String torrent3D = movie.getJSONArray("torrents").getJSONObject(2).getString("url");
                int index = 0;
                ArrayList<String> genere = new ArrayList<>();
                while (movie.getJSONArray("genres").getString(index) != null){
                    genere.add(movie.getJSONArray("genres").getString(index));
                    index++;
                }
                moviesList.add(new Movies(id,imageUrl,title,rating,imdbCode,ytTrailerCode,year,runtime,language,description,torrent720P,torrent1080P,torrent3D,genere));
            }
        }
        catch (JSONException e){
            Log.e(LOG_TAG, "Problem parsing the movies JSON results", e);
        }
        return moviesList;
    }
}
