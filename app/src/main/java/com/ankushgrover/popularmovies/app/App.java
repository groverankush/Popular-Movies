package com.ankushgrover.popularmovies.app;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.ankushgrover.popularmovies.data.source.local.AppDatabase;

/**
 * Created by Ankush Grover(ankushgrover02@gmail.com) on 11/6/18.
 */
public class App extends Application {

    private static App sApp;
    private AppDatabase database;

    public static App getApplication() {
        return sApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sApp = this;

        database = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "popular-movie").build();

    }

    public AppDatabase getDatabase() {
        return database;
    }

}
