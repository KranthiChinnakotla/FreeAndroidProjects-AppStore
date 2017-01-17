package com.medha.moviefilter;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.widget.Toast;

/**
 * Created by Prathyusha on 1/10/17.
 */

public class SettingsFragment extends PreferenceFragment  {

    MovieCategory movieCategory;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        movieCategory = (MovieCategory) activity;

    }



    @Override
    public void onPause() {
        super.onPause();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        movieCategory.movieCategory(sharedPreferences.getString("pref_list",""));

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref);
    }


    public interface MovieCategory{
        public void movieCategory(String category);
    }
}
