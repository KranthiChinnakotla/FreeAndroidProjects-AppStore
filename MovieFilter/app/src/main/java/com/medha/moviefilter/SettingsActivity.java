package com.medha.moviefilter;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity implements SettingsFragment.MovieCategory {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_settings);
        getFragmentManager().beginTransaction().replace(android.R.id.content,new SettingsFragment()).commit();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

    }


    @Override
    public void movieCategory(String category) {
        Toast.makeText(SettingsActivity.this,"Selected "+category,Toast.LENGTH_SHORT).show();
    }
}
