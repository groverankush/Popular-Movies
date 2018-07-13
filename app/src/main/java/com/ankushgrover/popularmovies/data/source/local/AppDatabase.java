package com.ankushgrover.popularmovies.data.source.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.graphics.Movie;

import com.ankushgrover.popularmovies.data.models.review.Review;
import com.ankushgrover.popularmovies.data.models.trailer.Trailer;

/**
 * Created by Ankush Grover(ankush.grover@finoit.co.in) on 13/7/18.
 */
@Database(entities = {Movie.class, Trailer.class, Review.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MovieDao movieDao();

    public abstract MovieDao ReviewDao();

    public abstract MovieDao TrailerDao();
}
