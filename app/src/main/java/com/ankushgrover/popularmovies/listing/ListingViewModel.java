package com.ankushgrover.popularmovies.listing;

import android.arch.lifecycle.ViewModel;

import com.ankushgrover.popularmovies.data.Movie;
import com.ankushgrover.popularmovies.data.NetworkResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ankush Grover(ankush.grover@finoit.co.in) on 20/6/18.
 */
public class ListingViewModel extends ViewModel {

    private List<Movie> movies;
    private NetworkResult result;

    public List<Movie> getMovies() {
        if (movies == null)
            movies = new ArrayList<Movie>();
        return movies;
    }


    public NetworkResult getResult() {
        return result;
    }

    public void setResult(NetworkResult result) {
        this.result = result;
    }
}
