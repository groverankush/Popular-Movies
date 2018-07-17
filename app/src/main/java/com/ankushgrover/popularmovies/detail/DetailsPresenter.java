package com.ankushgrover.popularmovies.detail;

import android.util.Log;

import com.ankushgrover.popularmovies.data.source.DataManager;
import com.ankushgrover.popularmovies.utils.NetworkUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ankush Grover(ankushgrover02@gmail.com) on 5/6/18.
 */
public class DetailsPresenter implements DetailsContract.Presenter {

    private final CompositeDisposable disposables;
    private DataManager dataManager;
    private DetailViewModel viewModel;
    private DetailsContract.View view;


    public DetailsPresenter(DataManager dataManager, DetailViewModel viewModel, DetailsContract.View view) {
        this.dataManager = dataManager;

        this.viewModel = viewModel;
        this.view = view;
        this.disposables = new CompositeDisposable();
    }

    @Override
    public void loadTrailers() {
        if (NetworkUtils.isConnectedToNetwork()) {

            Disposable disposable = dataManager.getTrailersRepository().fetchTrailers(viewModel.getMovie().getId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(trailerResult -> {
                        viewModel.setTrailers(trailerResult.getResults());
                        view.onReceiveTrailers();
                    }, throwable -> view.errorLoadingTrailers());
            disposables.add(disposable);

        } else {
            viewModel.getTrailers().clear();
            view.errorLoadingTrailers();
        }

    }

    @Override
    public void loadReviews() {

        if (NetworkUtils.isConnectedToNetwork()) {
            Disposable disposable = dataManager.getReviewsRepository().fetchReviews(viewModel.getMovie().getId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(reviewResult -> {
                        viewModel.setReviews(reviewResult.getResults());
                        view.onReceiveReviews();
                    }, throwable -> view.errorLoadingReviews());
            disposables.add(disposable);
        } else {
            viewModel.getReviews().clear();
            view.errorLoadingReviews();
        }


    }

    @Override
    public void likeMovie() {

    }

    @Override
    public void subscribe() {
        view.setupHead();

        if (viewModel.getTrailers().size() == 0)
            loadTrailers();
        else view.onReceiveTrailers();

        if (viewModel.getReviews().size() == 0)
            loadReviews();
        else view.onReceiveReviews();
    }

    @Override
    public void unsubscribe() {
        disposables.clear();
    }

    @Override
    public void errorLog(Throwable throwable, int generalResponse, int errorResponse) {
        Log.e(getTag(), throwable.getMessage(), throwable);
        view.generalResponse(generalResponse);
        view.onError(errorResponse);
    }

    @Override
    public String getTag() {
        return DetailsPresenter.class.getSimpleName();
    }
}
