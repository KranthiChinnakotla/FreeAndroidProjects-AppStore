package com.medha.moviefilter.data;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import java.net.URI;

/**
 * Created by Prathyusha on 1/14/17.
 */

public class MovieContract {

    public static final String CONTENT_AUTHORITY = "com.medha.moviefilter.data";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_MOVIE = "movie";
    public static final String PATH_TRAILER = "trailer";

    public static final class MovieEntry implements BaseColumns{

        public static final Uri contentUri = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;

        public static final String TABLE_NAME = "movie";
        public static final String COLUMN_POSTERURL = "poster";
        public static final String COLUMN_BACKDROPURL = "backdrop";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_RELEASEDATE = "releasedate";
        public static final String COLUMN_SYNOPSIS = "synopsis";
        public static final String COLUMN_RATING = "rating";


        public static Uri buildMovieUri() {
            return contentUri;
        }


    }

    public static final class TrailerEntry implements BaseColumns{

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TRAILER;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TRAILER;

        public static final Uri contentUri = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TRAILER).build();
        public static final String TABLE_NAME = "trailers";
        public static final String COLUMN_MOV_KEY = "movie_id";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_KEY = "key_id";
        public static final String COLUMN_NAME = "name";

        public static Uri buildTrailerUri(long movieid ){
            return ContentUris.withAppendedId(contentUri,movieid);
        }

        public static long getMovieIdFromUri(Uri uri) {
            return Long.parseLong(uri.getPathSegments().get(1));
        }



    }





}
