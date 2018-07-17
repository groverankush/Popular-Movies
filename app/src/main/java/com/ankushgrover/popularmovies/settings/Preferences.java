package com.ankushgrover.popularmovies.settings;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.StringRes;

import com.ankushgrover.popularmovies.R;
import com.ankushgrover.popularmovies.app.App;


/**
 * Created by Ankush Grover(ankushgrover02@gmail.com) on 11/6/18.
 */
public class Preferences {

    /**
     * Method to get default shared preferences
     *
     * @return
     */
    private static SharedPreferences getPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(App.getApplication());
    }

    /**
     * Method to get default shared preferences
     *
     * @return
     */
    private static SharedPreferences.Editor getEditor() {
        return PreferenceManager.getDefaultSharedPreferences(App.getApplication()).edit();
    }


    private static String getString(@StringRes int key, @StringRes int defaultValueId) {
        return getPreferences().getString(App.getApplication().getString(key), App.getApplication().getString(defaultValueId));
    }

    public static String getOrderPreference() {
        return getString(R.string.key_order_by, R.string.val_popularity);
    }

    public static boolean isPopularMoviesSelected() {
        return getOrderPreference().equals(App.getApplication().getString(R.string.val_popularity));
    }

    public static boolean isFavouritesSelected() {
        return getOrderPreference().equals(App.getApplication().getString(R.string.val_favourites));
    }

    public static String getScreenType() {

        int textId;
        String orderPreference = getOrderPreference();

        if (orderPreference.equals(App.getApplication()
                .getString(R.string.val_popularity))) {
            textId = R.string.popular_movies;
        } else if (orderPreference.equals(App.getApplication()
                .getString(R.string.val_ratings)))
            textId = R.string.top_rated;
        else textId = R.string.val_favourites;

        return App.getApplication().getString(textId);

    }

}
