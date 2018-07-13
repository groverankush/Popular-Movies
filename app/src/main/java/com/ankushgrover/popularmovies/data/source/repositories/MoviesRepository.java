package com.ankushgrover.popularmovies.data.source.repositories;

import com.ankushgrover.popularmovies.BuildConfig;
import com.ankushgrover.popularmovies.app.App;
import com.ankushgrover.popularmovies.data.models.movie.Movie;
import com.ankushgrover.popularmovies.data.models.movie.NetworkResult;
import com.ankushgrover.popularmovies.data.models.review.ReviewResult;
import com.ankushgrover.popularmovies.data.models.trailer.TrailerResult;
import com.ankushgrover.popularmovies.data.source.DataContract;
import com.ankushgrover.popularmovies.data.source.local.AppDatabase;
import com.ankushgrover.popularmovies.data.source.remote.MoviesDataSource;
import com.ankushgrover.popularmovies.utils.NetworkUtils;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ankush Grover(ankushgrover02@gmail.com) on 5/6/18.
 */
public class MoviesRepository implements DataContract.MoviesContract {

    private static MoviesRepository repository;
    private MoviesDataSource mSource;

    private MoviesRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        mSource = retrofit.create(MoviesDataSource.class);
    }

    public static MoviesRepository getInstance() {
        if (repository == null)
            repository = new MoviesRepository();
        return repository;
    }

    public MoviesDataSource getSource() {
        return mSource;
    }

    @Override
    public Single<NetworkResult> fetchPopularMovies(int pageNumber) {
        return mSource.fetchPopularMovies(BuildConfig.MOVIE_DB_API_KEY, pageNumber);
    }

    @Override
    public Single<NetworkResult> fetchTopMovies(int pageNumber) {
        return mSource.fetchTopMovies(BuildConfig.MOVIE_DB_API_KEY, pageNumber);
    }

    @Override
    public Single<TrailerResult> fetchTrailers(String movieId) {
        /*if (NetworkUtils.isConnectedToNetwork())
            return mSource.fetchTrailers(movieId, BuildConfig.MOVIE_DB_API_KEY);
        else return App.getApplication().getDatabase().TrailerDao().getTrailers(movieId);*/
        return null;
    }

    @Override
    public Single<ReviewResult> fetchReviews(String movieId) {
        return null;
    }

    @Override
    public Single<NetworkResult> fetchFavouriteMovies() {
        return null;
    }

    @Override
    public Completable insertMovie(Movie movie) {
        return null;
    }

    @Override
    public Single<Movie> getMovie(int movieId) {
        return null;
    }
}
