package com.medha.moviefilter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.medha.moviefilter.services.PerformBackground;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MovieDetails extends AppCompatActivity implements MovieTrailerFragment.OnFragmentInteractionListener {

    MovieTrailerFragment fragment;
    ProgressDialog progressDialog = null;
    Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        movie =  (Movie) getIntent().getSerializableExtra("Mov");
        Uri trailerUri = Uri.parse(MainActivity.BASE_URL).buildUpon()
                .appendPath("3")
                .appendPath("movie")
                .appendPath(String.valueOf(movie.getMovieId()))
                .appendPath("videos")
                .appendQueryParameter("api_key", "f42ab7048f67b636eb2d7b225fa34277")
                .appendQueryParameter("language", "en-US").build();
        String url = trailerUri.toString();
        Log.d("Trailer Url",url);
        new TrailersThread().execute(url);

       /* fragment = new MovieDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("Mov",movie);
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.activity_movie_details,fragment).commit();*/
    }

    @Override
    public void onFragmentInteraction() {

    }



    class TrailersThread extends AsyncTask<String,Void,ArrayList<Trailer>>{

        @Override
        protected void onPostExecute(ArrayList<Trailer> trailers) {
            super.onPostExecute(trailers);
            progressDialog.dismiss();
            fragment = new MovieTrailerFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("Trail",trailers);
            bundle.putSerializable("Mov",movie);
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().add(R.id.activity_movie_details,fragment).commit();

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MovieDetails.this);
            progressDialog.setTitle("Loading");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(true);
            progressDialog.show();

        }

        @Override
        protected ArrayList<Trailer> doInBackground(String... strings) {

            BufferedReader reader = null;
            String urlString = strings[0];
            try {
                URL url = new URL(urlString);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.connect();
                    InputStream inputStream = con.getInputStream();
                    InputStreamReader ir = new InputStreamReader(inputStream);
                    reader = new BufferedReader(ir);
                    String line = "";
                    StringBuffer buffer = new StringBuffer();
                    while((line = reader.readLine()) != null){
                        buffer.append(line+"\n");
                    }
                    ArrayList<Trailer> trailers = JsonParsing.parseTrailers(buffer.toString());
                    return JsonParsing.parseTrailers(buffer.toString());

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
