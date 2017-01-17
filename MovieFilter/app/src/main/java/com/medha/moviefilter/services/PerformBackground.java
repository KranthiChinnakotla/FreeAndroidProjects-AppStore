package com.medha.moviefilter.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.medha.moviefilter.MainActivity.IntentResults;
import com.medha.moviefilter.JsonParsing;
import com.medha.moviefilter.Movie;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Prathyusha on 1/8/17.
 */

public class PerformBackground extends IntentService {

    public PerformBackground() {
        super("MovieFilter");

    }

    @Override
    protected void onHandleIntent(Intent intent) {

        String moviesList = null;
        BufferedReader reader = null;
        HttpURLConnection con = null;
        try {
            URL url = new URL(intent.getDataString());
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();

            // Check the input stream from the http connect method

            InputStream inputStream = con.getInputStream();
            if(inputStream == null){
                //then nothing to do

                moviesList = null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer buffer = new StringBuffer();
            String line;
            while ((line = reader.readLine()) != null){

                buffer.append(line + "\n");
            }

            if(buffer.length() == 0){
                moviesList = null;
            }
            moviesList = buffer.toString();
            ArrayList<Movie> movies = JsonParsing.parseMoviePosters(moviesList);


            Intent broadCastIntent = new Intent();
            broadCastIntent.setAction(IntentResults.RESPONSE);
            broadCastIntent.addCategory(Intent.CATEGORY_DEFAULT);
            broadCastIntent.putExtra("Mov",movies);
            sendBroadcast(broadCastIntent);

           // Log.d("MoviesList",movies.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
