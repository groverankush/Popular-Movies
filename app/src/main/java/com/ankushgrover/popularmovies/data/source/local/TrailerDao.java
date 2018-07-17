package com.ankushgrover.popularmovies.data.source.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.ankushgrover.popularmovies.data.models.trailer.Trailer;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * Created by Ankush Grover(ankushgrover02@gmail.com) on 13/7/18.
 */
@Dao
public interface TrailerDao {

    @Insert()
    void insert(ArrayList<Trailer> trailers);

    @Query("Select * from Trailer where movieId = :movieId")
    Single<List<Trailer>> getTrailers(int movieId);

}
