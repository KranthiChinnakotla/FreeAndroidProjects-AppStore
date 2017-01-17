package com.medha.moviefilter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.medha.moviefilter.data.MovieContract;
import com.medha.moviefilter.services.PerformBackground;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    Uri.Builder builder;
   GridView gridView;



    GridView new_GridView;

    ArrayList<Movie> moviesFromDatabase;
    final static String INITIAL_CATEGORY = "Now Playing";

    final static String BASE_URL = "https://api.themoviedb.org";
    private IntentResults receiver;
    // Register a Shared Preference listener during onResume


    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String moviesListChoice = sharedPref.getString("pref_list", "");
        Uri uri = formUri(moviesListChoice);
        if (uri != null) {
            String url = uri.toString();
            //Register a receiver
            IntentFilter filter = new IntentFilter(IntentResults.RESPONSE);
            filter.addCategory(Intent.CATEGORY_DEFAULT);
            receiver = new IntentResults();
            registerReceiver(receiver, filter);
            //Start an intent service
            Intent servicesIntent = new Intent(MainActivity.this, PerformBackground.class);
            servicesIntent.setData(Uri.parse(url));
            startService(servicesIntent);

        } else {
            Toast.makeText(MainActivity.this, "The category is not selected", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_map);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        new_GridView = (GridView) findViewById(R.id.gridview1);
        new_GridView.setVisibility(View.GONE);

        // Ensuring the network connection is available
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        boolean isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting();


        if (isConnected) {
            // Setting default values to the preferences
            PreferenceManager.setDefaultValues(this, R.xml.pref, false);
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
            String moviesListChoice = sharedPref.getString("pref_list", "");
            // building an url with the help of Uri Builder
            if (moviesListChoice.equals("Settings")) {
                Uri uri = formUri(INITIAL_CATEGORY);
                if (uri != null) {
                    String url = uri.toString();

                    //Register a receiver
                    IntentFilter filter = new IntentFilter(IntentResults.RESPONSE);
                    filter.addCategory(Intent.CATEGORY_DEFAULT);
                    receiver = new IntentResults();
                    registerReceiver(receiver, filter);

                    //Start an intent service
                    Intent servicesIntent = new Intent(MainActivity.this, PerformBackground.class);
                    servicesIntent.setData(Uri.parse(url));
                    startService(servicesIntent);

                } else {
                    Toast.makeText(MainActivity.this, "The category is not selected", Toast.LENGTH_SHORT).show();
                }
            } else if (!moviesListChoice.equals("Settings")) {
                Uri uri = formUri(moviesListChoice);
                if (uri != null) {
                    String url = uri.toString();

                    //Register a receiver
                    IntentFilter filter = new IntentFilter(IntentResults.RESPONSE);
                    filter.addCategory(Intent.CATEGORY_DEFAULT);
                    receiver = new IntentResults();
                    registerReceiver(receiver, filter);

                    //Start an intent service
                    Intent servicesIntent = new Intent(MainActivity.this, PerformBackground.class);
                    servicesIntent.setData(Uri.parse(url));
                    startService(servicesIntent);

                } else {
                    Toast.makeText(MainActivity.this, "The category is not selected", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "There is no network connection", Toast.LENGTH_SHORT).show();
            }


        }
    }


    public Uri formUri(String category) {

        Uri buildUri = null;
        switch (category) {
            case "Now Playing":
                buildUri = Uri.parse(BASE_URL).buildUpon()
                        .appendPath("3")
                        .appendEncodedPath("movie/now_playing")
                        .appendQueryParameter("api_key", "f42ab7048f67b636eb2d7b225fa34277")
                        .appendQueryParameter("language", "en-US")
                        .appendQueryParameter("page", "1")
                        .build();
                return buildUri;

            case "Popular Movies":
                buildUri = Uri.parse(BASE_URL).buildUpon()
                        .appendPath("3")
                        .appendEncodedPath("movie/popular")
                        .appendQueryParameter("api_key", "f42ab7048f67b636eb2d7b225fa34277")
                        .appendQueryParameter("language", "en-US")
                        .appendQueryParameter("page", "1")
                        .build();
                return buildUri;
            case "UpComing Movies":
                buildUri = Uri.parse(BASE_URL).buildUpon()
                        .appendPath("3")
                        .appendEncodedPath("movie/upcoming")
                        .appendQueryParameter("api_key", "f42ab7048f67b636eb2d7b225fa34277")
                        .appendQueryParameter("language", "en-US")
                        .appendQueryParameter("page", "1")
                        .build();
                return buildUri;
            default:
                return buildUri;


        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.action_favourites:
                if(new_GridView.getVisibility() == View.GONE){
                    getSupportLoaderManager().initLoader(1,null,this);
                }

                break;
            case R.id.action_all:
                if(gridView.getVisibility() == View.GONE){
                    gridView.setVisibility(View.VISIBLE);
                }
                if(new_GridView.getVisibility() == View.VISIBLE){
                    new_GridView.setVisibility(View.GONE);
                }
                break;
            case R.id.action_clear:
                getContentResolver().delete(MovieContract.MovieEntry.buildMovieUri(),null,null);
                if(new_GridView.getVisibility() == View.VISIBLE){
                    new_GridView.setVisibility(View.GONE);
                }
                if(gridView.getVisibility() == View.GONE){
                    gridView.setVisibility(View.VISIBLE);
                }
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(receiver != null){
            this.unregisterReceiver(receiver);
        }

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {


        moviesFromDatabase = new ArrayList<>();
        Uri movieUri = MovieContract.MovieEntry.buildMovieUri();
        CursorLoader cL = new CursorLoader(getApplicationContext(),movieUri,null,null,null,null);
        return cL;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        data.moveToFirst();
        moviesFromDatabase.clear();
        while(!data.isAfterLast()){
            Movie moviefromDb = new Movie();
            moviefromDb.setMovieId(data.getLong(data.getColumnIndex(MovieContract.MovieEntry._ID)));
            moviefromDb.setTitle(data.getString(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_TITLE)));
            moviefromDb.setPosterUrl(data.getString(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_POSTERURL)));
            moviefromDb.setBackdropUrl(data.getString(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_BACKDROPURL)));
            moviefromDb.setRating(data.getString(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_RATING)));
            moviefromDb.setSynopsis(data.getString(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_SYNOPSIS)));
            moviefromDb.setRelease_date(data.getString(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_RELEASEDATE)));

            data.moveToNext();
            moviesFromDatabase.add(moviefromDb);

        }

        if(moviesFromDatabase.size() > 0){

            new_GridView.setVisibility(View.VISIBLE);
            gridView.setVisibility(View.GONE);
            new_GridView.setAdapter(new ImageAdapter(MainActivity.this,moviesFromDatabase));
            new_GridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(getApplicationContext(), MovieDetails.class);
                    intent.putExtra("Mov", moviesFromDatabase.get(i));
                    startActivity(intent);
                }
            });
        }else{
            Toast.makeText(getApplicationContext(),"There are no movies in the favourites",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {



    }


    public class IntentResults extends BroadcastReceiver {


        public static final String RESPONSE = "com.medha.moviefilter.intent.action.PROCESS_RESPONSE";

        @Override
        public void onReceive(final Context context, Intent intent) {

            final ArrayList<Movie> movies = (ArrayList<Movie>) intent.getSerializableExtra("Mov");
            Log.d("Receiver", movies.toString());
            gridView = (GridView) findViewById(R.id.gridview);
            gridView.setAdapter(new ImageAdapter(context, movies));
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                    Intent intent = new Intent(context, MovieDetails.class);
                    intent.putExtra("Mov", movies.get(i));
                    startActivity(intent);


                }
            });


        }


    }


}
