package com.ankushgrover.popularmovies.listing;

import com.ankushgrover.popularmovies.BuildConfig;
import com.ankushgrover.popularmovies.data.source.MoviesRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ankush Grover(ankushgrover02@gmail.com) on 5/6/18.
 */
public class ListingPresenter implements ListingContract.Presenter {

    private CompositeDisposable mDisposables;
    private MoviesRepository repository;


    ListingPresenter(MoviesRepository repository) {
        this.repository = repository;
        this.mDisposables = new CompositeDisposable();
    }

    @Override
    public void fetchPopularMovies() {
        Disposable disposable = repository.getSource().fetchPopularMovies(BuildConfig.MOVIE_DB_API_KEY, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(networkResult -> {
                        },
                        throwable -> {
                        });

        mDisposables.add(disposable);
    }

    @Override
    public void fetchTopMovies() {

    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        mDisposables.dispose();
    }
}
