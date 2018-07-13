package com.ankushgrover.popularmovies.data.source.repositories;

import com.ankushgrover.popularmovies.data.models.trailer.Trailer;
import com.ankushgrover.popularmovies.data.models.trailer.TrailerResult;
import com.ankushgrover.popularmovies.data.source.DataContract;

import java.util.ArrayList;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * Created by Ankush Grover(ankush.grover@finoit.co.in) on 13/7/18.
 */
public class TrailersRepository implements DataContract.TrailersContract {
    @Override
    public Single<TrailerResult> fetchTrailers() {
        return null;
    }

    @Override
    public Completable insertTrailers(ArrayList<Trailer> trailers, int movieId) {
        return null;
    }
}
