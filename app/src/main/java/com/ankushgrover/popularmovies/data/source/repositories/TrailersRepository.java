package com.ankushgrover.popularmovies.data.source.repositories;

import com.ankushgrover.popularmovies.BuildConfig;
import com.ankushgrover.popularmovies.data.models.trailer.Trailer;
import com.ankushgrover.popularmovies.data.models.trailer.TrailerResult;
import com.ankushgrover.popularmovies.data.source.DataContract;
import com.ankushgrover.popularmovies.data.source.local.AppDatabase;
import com.ankushgrover.popularmovies.data.source.remote.MoviesDataSource;
import com.ankushgrover.popularmovies.utils.NetworkUtils;

import java.util.ArrayList;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * Created by Ankush Grover(ankush.grover@finoit.co.in) on 13/7/18.
 */
public class TrailersRepository implements DataContract.TrailersContract {

    private MoviesDataSource remoteDataSource;
    private AppDatabase database;

    public TrailersRepository(MoviesDataSource remoteDataSource, AppDatabase database) {

        this.remoteDataSource = remoteDataSource;
        this.database = database;
    }

    @Override
    public Single<TrailerResult> fetchTrailers(int movieId) {
        if (NetworkUtils.isConnectedToNetwork())
            return remoteDataSource.fetchTrailers(movieId, BuildConfig.MOVIE_DB_API_KEY);
        else
            return database.TrailerDao().getTrailers(movieId)
                    .map(trailers -> {
                        TrailerResult result = new TrailerResult();
                        result.setResults(trailers);
                        return result;
                    });

    }

    @Override
    public Completable insertTrailers(ArrayList<Trailer> trailers, int movieId) {

        return Completable.fromAction(() -> {
            for (Trailer trailer : trailers) {
                trailer.setMovieId(movieId);
            }


            database.TrailerDao().insert(trailers);
        });


    }
}
