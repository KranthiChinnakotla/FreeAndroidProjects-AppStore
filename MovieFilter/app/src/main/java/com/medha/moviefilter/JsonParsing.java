package com.medha.moviefilter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Prathyusha on 1/8/17.
 */

public class JsonParsing {


    public static ArrayList<Movie> parseMoviePosters(String in) throws JSONException {

         ArrayList<Movie> moviesPosters = new ArrayList<>();
         JSONObject root = new JSONObject(in);
         JSONArray  results = root.getJSONArray("results");

        for(int i=0; i< results.length(); i++){
            Movie mov = new Movie();
            JSONObject movie = results.getJSONObject(i);
            mov.setPosterUrl("http://image.tmdb.org/t/p/w185/"+movie.getString("poster_path"));
            moviesPosters.add(mov);
            mov.setTitle(movie.getString("title"));
            mov.setRelease_date(movie.getString("release_date"));
            mov.setSynopsis(movie.getString("overview"));
            mov.setBackdropUrl("http://image.tmdb.org/t/p/w300/"+movie.getString("backdrop_path"));
            mov.setRating(movie.getString("vote_average"));
            mov.setMovieId(movie.getLong("id"));

        }
       return moviesPosters;
    }

    public static ArrayList<Trailer> parseTrailers(String in) throws JSONException{

        ArrayList<Trailer> trailers = new ArrayList<>();
        JSONObject root = new JSONObject(in);
        JSONArray  results = root.getJSONArray("results");
        for(int i=0; i< results.length(); i++){
            Trailer trailer = new Trailer();
            JSONObject teaser = results.getJSONObject(i);
            trailer.setKey(teaser.getString("key"));
            trailer.setName(teaser.getString("name"));
            trailer.setType(teaser.getString("type"));
            trailers.add(trailer);

        }

      return trailers;
    }

}
