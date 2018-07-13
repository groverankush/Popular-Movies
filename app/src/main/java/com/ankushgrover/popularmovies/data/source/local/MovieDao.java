package com.ankushgrover.popularmovies.data.source.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.ankushgrover.popularmovies.data.models.movie.Movie;

import io.reactivex.Single;

/**
 * Created by Ankush Grover(ankushgrover02@gmail.com) on 13/7/18.
 */
@Dao
public interface MovieDao {

    @Insert
    void insertMovie(Movie movie);

    @Query("Select * from movie where id = :id")
    Single<Movie> getMovie(int id);
}
