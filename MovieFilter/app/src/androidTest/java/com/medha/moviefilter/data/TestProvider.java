package com.medha.moviefilter.data;

import android.test.AndroidTestCase;

/**
 * Created by Prathyusha on 1/14/17.
 */

public class TestProvider extends AndroidTestCase {

    public void testGetType(){


        // content://com.example.android.sunshine.app/weather/
        String type = mContext.getContentResolver().getType(MovieContract.TrailerEntry.contentUri);
        // vnd.android.cursor.dir/com.example.android.sunshine.app/weather
        assertEquals("Error: the TraielrEntry CONTENT_URI should return WeatherEntry.CONTENT_TYPE",MovieContract.TrailerEntry.CONTENT_TYPE, type);

        long testMovieId = 328111;

        type = mContext.getContentResolver().getType(
                MovieContract.TrailerEntry.buildTrailerUri(testMovieId));

        assertEquals("Error: the TrailerEntry CONTENT_URI with movie id should return WeatherEntry.CONTENT_TYPE",
                MovieContract.TrailerEntry.CONTENT_ITEM_TYPE, type);




        type = mContext.getContentResolver().getType(MovieContract.MovieEntry.contentUri);
        // vnd.android.cursor.dir/com.example.android.sunshine.app/location
        assertEquals("Error: the MovieEntry CONTENT_URI should return MovieEntry.CONTENT_TYPE",
                MovieContract.MovieEntry.CONTENT_TYPE, type);

    }


}
