package com.ankushgrover.popularmovies.data.source;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ankush Grover(ankushgrover02@gmail.com) on 5/6/18.
 */
public class MoviesRepository {

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
}
