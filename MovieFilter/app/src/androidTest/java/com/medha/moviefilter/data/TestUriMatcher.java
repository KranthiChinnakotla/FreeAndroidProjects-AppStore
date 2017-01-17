package com.medha.moviefilter.data;

import android.content.UriMatcher;
import android.net.Uri;
import android.test.AndroidTestCase;

import com.medha.moviefilter.Movie;

/**
 * Created by Prathyusha on 1/14/17.
 */

public class TestUriMatcher extends AndroidTestCase {

    private static final long MOVIE_QUERY = 328111;



    private static final Uri TEST_TRAILER_DIR = MovieContract.TrailerEntry.contentUri;
    private static final Uri TEST_TRAILER_WITH_MOVIEID_DIR = MovieContract.TrailerEntry.buildTrailerUri(MOVIE_QUERY);

    private static final Uri TEST_MOVIE_DIR = MovieContract.MovieEntry.contentUri;

    /*
        Students: This function tests that your UriMatcher returns the correct integer value
        for each of the Uri types that our ContentProvider can handle.  Uncomment this when you are
        ready to test your UriMatcher.
     */
    public void testUriMatcher() {
        UriMatcher testMatcher = MovieProvider.buildUriMatcher();

        assertEquals("Error: The TRAILER URI was matched incorrectly.",
                testMatcher.match(TEST_TRAILER_DIR), MovieProvider.TRAILER);
        assertEquals("Error: The TRAILER WITH MOVIEID URI was matched incorrectly.",
                testMatcher.match(TEST_TRAILER_WITH_MOVIEID_DIR), MovieProvider.TRAILER_WITH_MOVIEID);
        assertEquals("Error: The MOVIE URI was matched incorrectly.",
                testMatcher.match(TEST_MOVIE_DIR), MovieProvider.MOVIE);
    }
}
