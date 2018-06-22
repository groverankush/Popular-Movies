package com.ankushgrover.popularmovies.app;

import android.app.Application;

/**
 * Created by Ankush Grover(ankush.grover@finoit.co.in) on 11/6/18.
 */
public class App extends Application {

    private static App sApp;

    public static App getApplication() {
        return sApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sApp = this;

    }

}
