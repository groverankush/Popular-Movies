package com.ankushgrover.popularmovies.detail;

import android.util.Log;

import com.ankushgrover.popularmovies.R;
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


            Disposable disposable = dataManager.getTrailersRepository().fetchTrailers(viewModel.getMovie().getId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(trailerResult -> {
                        viewModel.setTrailers(trailerResult.getResults());
                        view.onReceiveTrailers();
                    }, throwable -> {
                        viewModel.getTrailers().clear();
                        view.errorLoadingTrailers();
                    });
            disposables.add(disposable);

    }

    @Override
    public void loadReviews() {


            Disposable disposable = dataManager.getReviewsRepository().fetchReviews(viewModel.getMovie().getId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(reviewResult -> {
                        viewModel.setReviews(reviewResult.getResults());
                        view.onReceiveReviews();
                    }, throwable -> {
                        viewModel.getReviews().clear();
                        view.errorLoadingReviews();
                    });
            disposables.add(disposable);





    }

    @Override
    public void onLikeButtonPress() {
        if (viewModel.getMovie().isLiked()) {
            Disposable disposable = dataManager.getMoviesRepository().deleteMovie(viewModel.getMovie())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> {
                        viewModel.getMovie().setLiked(false);
                        view.manageLikeStatus();
                        view.generalResponse(R.string.removed_from_favourites);
                    }, throwable -> {
                        viewModel.getMovie().setLiked(false);
                        view.manageLikeStatus();
                        errorLog(throwable, R.string.msg_something_went_wrong, -1);
                    });
            disposables.add(disposable);
        } else {
            Disposable disposable = dataManager.getMoviesRepository().insertMovie(viewModel.getMovie())
                    .andThen(dataManager.getTrailersRepository().insertTrailers(viewModel.getTrailers(), viewModel.getMovie().getId()))
                    .andThen(dataManager.getReviewsRepository().insertReviews(viewModel.getReviews(), viewModel.getMovie().getId()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> {
                        viewModel.getMovie().setLiked(true);
                        view.manageLikeStatus();
                        view.generalResponse(R.string.added_to_favourites);
                    }, throwable -> {
                        viewModel.getMovie().setLiked(false);
                        view.manageLikeStatus();
                        errorLog(throwable, R.string.msg_something_went_wrong, -1);
                    });

            disposables.add(disposable);
        }

    }

    @Override
    public void subscribe() {
        view.setupHead();

        Disposable disposable = dataManager.getMoviesRepository().fetchLocalMovie(viewModel.getMovie().getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movie -> {
                    viewModel.getMovie().setLiked(true);
                    view.manageLikeStatus();
                }, throwable -> {
                    viewModel.getMovie().setLiked(false);
                    view.manageLikeStatus();
                });
        disposables.add(disposable);

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
