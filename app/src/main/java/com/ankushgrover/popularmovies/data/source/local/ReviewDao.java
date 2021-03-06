package com.ankushgrover.popularmovies.data.source.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.ankushgrover.popularmovies.data.models.review.Review;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

/**
 * Created by Ankush Grover(ankushgrover02@gmail.com) on 13/7/18.
 */
@Dao
public interface ReviewDao {
    @Insert()
    void insert(ArrayList<Review> reviews);

    @Query("Select * from Review where movieId = :movieId")
    Single<List<Review>> getReviews(int movieId);
}
