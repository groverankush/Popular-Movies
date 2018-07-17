package com.ankushgrover.popularmovies.data.source;

import com.ankushgrover.popularmovies.app.App;
import com.ankushgrover.popularmovies.data.source.local.AppDatabase;
import com.ankushgrover.popularmovies.data.source.remote.MoviesDataSource;
import com.ankushgrover.popularmovies.data.source.repositories.MoviesRepository;
import com.ankushgrover.popularmovies.data.source.repositories.ReviewsRepository;
import com.ankushgrover.popularmovies.data.source.repositories.TrailersRepository;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ankush Grover(ankush.grover@finoit.co.in) on 13/7/18.
 */
public class DataManager {

    private static DataManager instance;
    private MoviesRepository moviesRepository;
    private ReviewsRepository reviewsRepository;
    private TrailersRepository trailersRepository;
    private MoviesDataSource remoteDataSource;
    private AppDatabase database;

    private DataManager() {


        remoteDataSource = initRemoteDataSource();
        database = App.getApplication().getDatabase();


        moviesRepository = new MoviesRepository(remoteDataSource, database);
        reviewsRepository = new ReviewsRepository(remoteDataSource, database);
        trailersRepository = new TrailersRepository(remoteDataSource, database);
    }

    public static DataManager getInstance() {
        if (instance == null)
            instance = new DataManager();
        return instance;
    }

    private MoviesDataSource initRemoteDataSource() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit.create(MoviesDataSource.class);
    }

    public MoviesRepository getMoviesRepository() {
        return moviesRepository;
    }

    public ReviewsRepository getReviewsRepository() {
        return reviewsRepository;
    }

    public TrailersRepository getTrailersRepository() {
        return trailersRepository;
    }
}
