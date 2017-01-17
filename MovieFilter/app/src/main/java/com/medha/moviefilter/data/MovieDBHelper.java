package com.medha.moviefilter.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Prathyusha on 1/14/17.
 */

public class MovieDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 4;
    static final String DATABASE_NAME = "moviefilter.db";
    public MovieDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
      final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " + MovieContract.MovieEntry.TABLE_NAME + " (" +
              MovieContract.MovieEntry._ID + " LONG PRIMARY KEY," +
              MovieContract.MovieEntry.COLUMN_BACKDROPURL + " TEXT NOT NULL, " +
              MovieContract.MovieEntry.COLUMN_POSTERURL + " TEXT NOT NULL, " +
              MovieContract.MovieEntry.COLUMN_RELEASEDATE + " TEXT NOT NULL, " +
              MovieContract.MovieEntry.COLUMN_SYNOPSIS + " TEXT NOT NULL, " +
              MovieContract.MovieEntry.COLUMN_TITLE + " VARCHAR NOT NULL, " +
              MovieContract.MovieEntry.COLUMN_RATING + " VARCHAR NOT NULL " +
              " );";

        final String SQL_CREATE_TRAILER_TABLE = "CREATE TABLE " + MovieContract.TrailerEntry.TABLE_NAME + " (" +
                MovieContract.TrailerEntry._ID + " INTEGER PRIMARY KEY," +
                MovieContract.TrailerEntry.COLUMN_MOV_KEY + " LONG NOT NULL," +
                MovieContract.TrailerEntry.COLUMN_KEY + " TEXT NOT NULL, " +
                MovieContract.TrailerEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                MovieContract.TrailerEntry.COLUMN_TYPE + " TEXT NOT NULL, " +
                " FOREIGN KEY (" + MovieContract.TrailerEntry.COLUMN_MOV_KEY + ") REFERENCES " +
                MovieContract.MovieEntry.TABLE_NAME + " (" + MovieContract.MovieEntry._ID +
                " ));";

        sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_TRAILER_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ MovieContract.MovieEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ MovieContract.TrailerEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
