package com.ankushgrover.popularmovies.listing;

import android.util.Log;

import com.ankushgrover.popularmovies.R;
import com.ankushgrover.popularmovies.data.source.DataManager;
import com.ankushgrover.popularmovies.settings.Preferences;
import com.ankushgrover.popularmovies.utils.NetworkUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ankush Grover(ankushgrover02@gmail.com) on 5/6/18.
 */
public class ListingPresenter implements ListingContract.Presenter {

    private CompositeDisposable mDisposables;
    private DataManager dataManager;
    private ListingViewModel viewModel;
    private ListingContract.View view;


    ListingPresenter(DataManager dataManager, ListingViewModel viewModel, ListingContract.View view) {
        this.dataManager = dataManager;

        this.viewModel = viewModel;
        this.view = view;
        this.mDisposables = new CompositeDisposable();
    }

    @Override
    public void fetchPopularMovies() {
        isLoading(true);
        Disposable disposable = dataManager.getMoviesRepository().fetchPopularMovies(viewModel.getResult() == null ? 1 : viewModel.getResult().getPage() + 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(networkResult -> {
                            viewModel.setResult(networkResult);
                            viewModel.getMovies().addAll(networkResult.getResults());
                            checkMoviesListBeforeDisplay();
                            isLoading(false);
                        },
                        throwable -> {
                            errorLog(throwable, R.string.msg_something_went_wrong, R.string.genereal_error);
                            isLoading(false);
                        });

        mDisposables.add(disposable);
    }

    @Override
    public void fetchTopMovies() {
        isLoading(true);
        Disposable disposable = dataManager.getMoviesRepository().fetchTopMovies(viewModel.getResult() == null ? 1 : viewModel.getResult().getPage() + 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(networkResult -> {
                            isLoading(false);
                            viewModel.setResult(networkResult);
                            viewModel.getMovies().addAll(networkResult.getResults());
                            checkMoviesListBeforeDisplay();
                        },
                        throwable -> {
                            errorLog(throwable, R.string.msg_something_went_wrong, R.string.genereal_error);
                            isLoading(false);
                        });


        mDisposables.add(disposable);
    }

    @Override
    public void fetchFavourites() {
        if (viewModel.getMovies().size() > 0)
            return;
        isLoading(true);
        Disposable disposable = dataManager.getMoviesRepository().fetchFavouriteMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(networkResult -> {
                    isLoading(false);
                    viewModel.setResult(networkResult);
                    viewModel.getMovies().addAll(networkResult.getResults());
                    checkMoviesListBeforeDisplay();
                }, throwable -> {
                    errorLog(throwable, R.string.msg_something_went_wrong, R.string.genereal_error);
                    isLoading(false);
                });
        mDisposables.add(disposable);
    }

    @Override
    public boolean fetchMovies(boolean force) {

        if (force) {
            viewModel.getMovies().clear();
            viewModel.setResult(null);
            unsubscribe();
            isLoading(false);
        }

        if (!viewModel.getIsLoading().getValue()) {
            if (Preferences.isFavouritesSelected()) {
                fetchFavourites();
                return true;
            } else if (NetworkUtils.isConnectedToNetwork()) {
                if (Preferences.isPopularMoviesSelected())
                    fetchPopularMovies();
                else fetchTopMovies();
                return true;
            } else {
                if (!Preferences.isFavouritesSelected()) {
                    view.generalResponse(R.string.network_error);
                    view.onError(R.string.network_error);
                }

            }
        }
        return false;
    }

    @Override
    public void subscribe() {
        fetchMovies(false);
    }

    @Override
    public void unsubscribe() {
        isLoading(false);
        mDisposables.clear();
    }

    @Override
    public void errorLog(Throwable throwable, int generalResponse, int errorResponse) {
        Log.e(getTag(), throwable.getMessage());
        view.generalResponse(generalResponse);
        view.onError(errorResponse);
    }

    @Override
    public String getTag() {
        return ListingPresenter.class.getSimpleName();
    }

    private void checkMoviesListBeforeDisplay() {
        if (viewModel.getMovies().size() == 0)
            view.onError(R.string.no_movies);
        else view.moviesAdded();

    }

    private void isLoading(boolean isLoading) {
        viewModel.getIsLoading().setValue(isLoading);
    }
}
