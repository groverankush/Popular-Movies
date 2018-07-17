package com.ankushgrover.popularmovies.data.source.repositories;

import com.ankushgrover.popularmovies.BuildConfig;
import com.ankushgrover.popularmovies.data.models.review.Review;
import com.ankushgrover.popularmovies.data.models.review.ReviewResult;
import com.ankushgrover.popularmovies.data.source.DataContract;
import com.ankushgrover.popularmovies.data.source.local.AppDatabase;
import com.ankushgrover.popularmovies.data.source.remote.MoviesDataSource;

import java.util.ArrayList;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * Created by Ankush Grover(ankush.grover@finoit.co.in) on 13/7/18.
 */
public class ReviewsRepository implements DataContract.ReviewsContract {


    private MoviesDataSource remoteDataSource;
    private AppDatabase database;

    public ReviewsRepository(MoviesDataSource remoteDataSource, AppDatabase database) {

        this.remoteDataSource = remoteDataSource;
        this.database = database;
    }


    @Override
    public Single<ReviewResult> fetchReviews(int movieId) {

        return remoteDataSource.fetchReviews(movieId, BuildConfig.MOVIE_DB_API_KEY);

    }

    @Override
    public Completable insertReviews(ArrayList<Review> reviews, int movieId) {
        return null;
    }
}
