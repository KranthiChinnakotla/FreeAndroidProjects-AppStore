package com.medha.moviefilter.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.Settings;
import android.test.AndroidTestCase;

import com.medha.moviefilter.Movie;

import java.util.Map;
import java.util.Set;

/**
 * Created by Prathyusha on 1/14/17.
 */

public class TestUtilities extends AndroidTestCase {

    static ContentValues createDeparteMoviedValues() {
        // Create a new map of values, where column names are the keys
        ContentValues testValues = new ContentValues();
        testValues.put(MovieContract.MovieEntry.COLUMN_BACKDROPURL, "backdrop");
        testValues.put(MovieContract.MovieEntry.COLUMN_TITLE, "The Departed");
        testValues.put(MovieContract.MovieEntry.COLUMN_POSTERURL, "Poster");
        testValues.put(MovieContract.MovieEntry.COLUMN_RELEASEDATE, "Today's date");
        testValues.put(MovieContract.MovieEntry.COLUMN_SYNOPSIS,"Undercover police story");

        return testValues;
    }

    static ContentValues createDepartedTrailerrValues(long movieRowId) {
        ContentValues weatherValues = new ContentValues();
        weatherValues.put(MovieContract.TrailerEntry.COLUMN_MOV_KEY, movieRowId);
        weatherValues.put(MovieContract.TrailerEntry.COLUMN_KEY, "trailer key");
        weatherValues.put(MovieContract.TrailerEntry.COLUMN_NAME, "New Trailer");
        weatherValues.put(MovieContract.TrailerEntry.COLUMN_TYPE,"Trailer");
        return weatherValues;
    }

    static long insertDepartedMoviedValues(Context context) {
        // insert our test records into the database
        MovieDBHelper dbHelper = new MovieDBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues testValues = TestUtilities.createDeparteMoviedValues();

        long movieRowId;
        movieRowId = db.insert(MovieContract.MovieEntry.TABLE_NAME, null, testValues);

        // Verify we got a row back.
        assertTrue("Error: Failure to insert Departed  Movie  Values", movieRowId != -1);

        return movieRowId;
    }

    static void validateCursor(String error, Cursor valueCursor, ContentValues expectedValues) {
        assertTrue("Empty cursor returned. " + error, valueCursor.moveToFirst());
        validateCurrentRecord(error, valueCursor, expectedValues);
        valueCursor.close();
    }

    static void validateCurrentRecord(String error, Cursor valueCursor, ContentValues expectedValues) {
        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();
        for (Map.Entry<String, Object> entry : valueSet) {
            String columnName = entry.getKey();
            int idx = valueCursor.getColumnIndex(columnName);
            assertFalse("Column '" + columnName + "' not found. " + error, idx == -1);
            String expectedValue = entry.getValue().toString();
            assertEquals("Value '" + entry.getValue().toString() +
                    "' did not match the expected value '" +
                    expectedValue + "'. " + error, expectedValue, valueCursor.getString(idx));
        }
    }


}
