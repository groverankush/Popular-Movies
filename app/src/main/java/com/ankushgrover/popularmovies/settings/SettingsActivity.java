package com.ankushgrover.popularmovies.settings;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ankushgrover.popularmovies.R;

public class SettingsActivity extends AppCompatActivity {

    private String currentSortPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        currentSortPreference = Preferences.getOrderPreference();


        getFragmentManager().beginTransaction()
                .replace(R.id.container, new SettingsFragment())
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (!currentSortPreference.equals(Preferences.getOrderPreference()))
            setResult(RESULT_OK);
        finish();
    }
}
