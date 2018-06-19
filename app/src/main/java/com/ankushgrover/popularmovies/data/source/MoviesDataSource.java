package com.ankushgrover.popularmovies.data.source;

import com.ankushgrover.popularmovies.data.NetworkResult;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
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
}
