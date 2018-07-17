package com.ankushgrover.popularmovies.data.source;

import com.ankushgrover.popularmovies.data.models.movie.Movie;
import com.ankushgrover.popularmovies.data.models.movie.NetworkResult;
import com.ankushgrover.popularmovies.data.models.review.Review;
import com.ankushgrover.popularmovies.data.models.review.ReviewResult;
import com.ankushgrover.popularmovies.data.models.trailer.Trailer;
import com.ankushgrover.popularmovies.data.models.trailer.TrailerResult;

import java.util.ArrayList;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * Created by Ankush Grover(ankush.grover@finoit.co.in) on 13/7/18.
 */
public interface DataContract {

    interface MoviesContract {


        Single<NetworkResult> fetchPopularMovies(int pageNumber);

        Single<NetworkResult> fetchTopMovies(int pageNumber);

        Single<TrailerResult> fetchTrailers(String movieId);

        Single<ReviewResult> fetchReviews(String movieId);

        Single<NetworkResult> fetchFavouriteMovies();

        Completable insertMovie(Movie movie);

        Single<Movie> getMovie(int movieId);

    }

    interface TrailersContract {

        Single<TrailerResult> fetchTrailers(int movieId);

        Completable insertTrailers(ArrayList<Trailer> trailers, int movieId);

    }

    interface ReviewsContract {

        Single<ReviewResult> fetchReviews(int movieId);

        Completable insertReviews(ArrayList<Review> reviews, int movieId);

    }

}
