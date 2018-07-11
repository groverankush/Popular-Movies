package com.ankushgrover.popularmovies.detail;

import android.arch.lifecycle.ViewModel;

import com.ankushgrover.popularmovies.data.models.movie.Movie;
import com.ankushgrover.popularmovies.data.models.review.Review;
import com.ankushgrover.popularmovies.data.models.trailer.Trailer;

import java.util.ArrayList;
import java.util.List;

public class DetailViewModel extends ViewModel {

    private Movie movie;
    private ArrayList<Trailer> trailers;
    private ArrayList<Review> reviews;

    public DetailViewModel() {
        this.trailers = new ArrayList<>();
        this.reviews = new ArrayList<>();
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public ArrayList<Trailer> getTrailers() {
        return trailers;
    }

    public void setTrailers(List<Trailer> trailers) {

        this.trailers.clear();
        this.trailers.addAll(trailers);
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {

        this.reviews.clear();
        this.reviews.addAll(reviews);
    }
}
