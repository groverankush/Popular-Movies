package com.ankushgrover.popularmovies.data.source.repositories;

import com.ankushgrover.popularmovies.BuildConfig;
import com.ankushgrover.popularmovies.data.models.movie.Movie;
import com.ankushgrover.popularmovies.data.models.movie.NetworkResult;
import com.ankushgrover.popularmovies.data.source.DataContract;
import com.ankushgrover.popularmovies.data.source.local.AppDatabase;
import com.ankushgrover.popularmovies.data.source.remote.MoviesDataSource;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.functions.Action;

/**
 * Created by Ankush Grover(ankushgrover02@gmail.com) on 5/6/18.
 */
public class MoviesRepository implements DataContract.MoviesContract {

    private MoviesDataSource remoteDataSource;
    private AppDatabase database;

    public MoviesRepository(MoviesDataSource remoteDataSource, AppDatabase database) {
        this.remoteDataSource = remoteDataSource;
        this.database = database;
    }


    @Override
    public Single<NetworkResult> fetchPopularMovies(int pageNumber) {
        return remoteDataSource.fetchPopularMovies(BuildConfig.MOVIE_DB_API_KEY, pageNumber);
    }

    @Override
    public Single<NetworkResult> fetchTopMovies(int pageNumber) {
        return remoteDataSource.fetchTopMovies(BuildConfig.MOVIE_DB_API_KEY, pageNumber);
    }

    @Override
    public Single<NetworkResult> fetchFavouriteMovies() {
        return database.movieDao().getALlMovies()
                .map(movies -> {
                    NetworkResult networkResult = new NetworkResult();
                    networkResult.setResults(movies);
                    return networkResult;
                });
    }

    @Override
    public Completable insertMovie(Movie movie) {
        return Completable.fromAction(() -> database.movieDao().insertMovie(movie));

    }

    @Override
    public Completable deleteMovie(Movie movie) {
        return  Completable.fromAction(() -> database.movieDao().deleteMovie(movie));

    }

    @Override
    public Single<Movie> fetchLocalMovie(int movieId) {
        return database.movieDao().getMovie(movieId);
    }
}
