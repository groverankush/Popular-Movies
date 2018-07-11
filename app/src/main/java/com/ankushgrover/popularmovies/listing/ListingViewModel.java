package com.ankushgrover.popularmovies.listing;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.ankushgrover.popularmovies.data.models.movie.Movie;
import com.ankushgrover.popularmovies.data.models.movie.NetworkResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ankush Grover(ankush.grover@finoit.co.in) on 20/6/18.
 */
public class ListingViewModel extends ViewModel {

    private List<Movie> movies;
    private NetworkResult result;
    private MutableLiveData<Boolean> isLoading;

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

    public MutableLiveData<Boolean> getIsLoading() {
        if (isLoading == null) {
            isLoading = new MutableLiveData<>();
            isLoading.setValue(false);
        }
        return isLoading;
    }

}
