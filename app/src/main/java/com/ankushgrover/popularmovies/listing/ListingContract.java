package com.ankushgrover.popularmovies.listing;

import com.ankushgrover.popularmovies.architecture.BasePresenter;
import com.ankushgrover.popularmovies.architecture.BaseView;

/**
 * Created by Ankush Grover(ankushgrover02@gmail.com) on 5/6/18.
 */
public interface ListingContract {

    interface View extends BaseView {

        void moviesAdded();

        boolean fetchMoreMovies();


    }

    interface Presenter extends BasePresenter {

        /**
         * Method to fetch popular movies from the database.
         */
        void fetchPopularMovies();

        /**
         * Method to fetch top movies from the database.
         */
        void fetchTopMovies();

        /**
         * Method to fetch favourite movies.
         */
        void fetchFavourites();

        /**
         * Responsible for fetching movies based on user preference and current requests.
         *
         * @return
         * @param: Forcibly fetch movies. Ignore the previous requests.
         */
        boolean fetchMovies(boolean force);
    }

}
