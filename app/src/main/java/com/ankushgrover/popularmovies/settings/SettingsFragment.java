package com.ankushgrover.popularmovies.settings;


import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;

import com.ankushgrover.popularmovies.R;


public class SettingsFragment extends PreferenceFragment {


    public SettingsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);


    }

}
