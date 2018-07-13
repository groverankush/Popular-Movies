package com.ankushgrover.popularmovies.data.source.remote;

import com.ankushgrover.popularmovies.data.models.movie.NetworkResult;
import com.ankushgrover.popularmovies.data.models.review.ReviewResult;
import com.ankushgrover.popularmovies.data.models.trailer.TrailerResult;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Ankush Grover(ankushgrover02@gmail.com) on 5/6/18.
 */
public interface MoviesDataSource {


    @GET("movie/popular")
    Single<NetworkResult> fetchPopularMovies(@Query("api_key") String key,
                                             @Query("page") int pageNumber);

    @GET("movie/top_rated")
    Single<NetworkResult> fetchTopMovies(@Query("api_key") String key,
                                         @Query("page") int pageNumber);

    @GET("movie/{id}/videos")
    Single<TrailerResult> fetchTrailers(@Path("id") String movieId,
                                        @Query("api_key") String key);

    @GET("movie/{id}/reviews")
    Single<ReviewResult> fetchReviews(@Path("id") String movieId,
                                      @Query("api_key") String key);
}
