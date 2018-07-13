package com.ankushgrover.popularmovies.detail;

import com.ankushgrover.popularmovies.architecture.BasePresenter;
import com.ankushgrover.popularmovies.architecture.BaseView;

/**
 * Created by Ankush Grover(ankushgrover02@gmail.com) on 5/6/18.
 */
public interface DetailsContract {

    interface View extends BaseView {


        void setupHead();

        void onReceiveTrailers();

        void errorLoadingTrailers();

        void onReceiveReviews();

        void errorLoadingReviews();


    }

    interface Presenter extends BasePresenter {

        /**
         * Method to request movie trailers.
         */
        void loadTrailers();

        /**
         * Method to load movie reviews.
         */
        void loadReviews();

        /**
         * Method to start db transaction to like
         */
        void likeMovie();

    }


}
