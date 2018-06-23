package com.ankushgrover.popularmovies.detail;

import android.arch.lifecycle.ViewModel;

import com.ankushgrover.popularmovies.data.Movie;

public class DetailViewModel extends ViewModel {

    private Movie movie;

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
