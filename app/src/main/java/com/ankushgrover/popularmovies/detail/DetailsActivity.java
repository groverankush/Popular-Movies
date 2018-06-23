package com.ankushgrover.popularmovies.detail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.ankushgrover.popularmovies.R;
import com.ankushgrover.popularmovies.data.Movie;
import com.ankushgrover.popularmovies.utils.TextUtils;
import com.squareup.picasso.Picasso;

import java.util.Locale;

public class DetailsActivity extends AppCompatActivity {

    public static final String MOVIE_DETAIL = "movieDetail";
    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //model = ViewModelProviders.of(this).get(DetailViewModel.class);

        getSupportActionBar().setTitle(R.string.movie_detail);
        movie = getIntent().getParcelableExtra(MOVIE_DETAIL);

        Picasso.get()
                .load(TextUtils.makePosterPath(movie.getPosterPath()))
                .placeholder(R.drawable.ic_image_black_24dp)
                .into((ImageView) findViewById(R.id.iv_poster));

        Picasso.get()
                .load(TextUtils.makeBannerPath(movie.getBackdropPath()))
                .into((ImageView) findViewById(R.id.iv_banner));


        TextUtils.setText(this, R.id.tv_name, TextUtils.isEmpty(movie.getTitle()) ? "Title not available" : movie.getTitle());
        TextUtils.setText(this, R.id.tv_year, TextUtils.isEmpty(movie.getReleaseDate()) ? "Release date not available" : movie.getReleaseDate().split("-")[0]);
        TextUtils.setText(this, R.id.tv_ratings, movie.getVoteAverage() == 0.0f ? "Votes not available" : String.format(Locale.ENGLISH, "%.1f/10", movie.getVoteAverage()));
        TextUtils.setText(this, R.id.tv_description, TextUtils.isEmpty(movie.getOverview()) ? "Description not available" : movie.getOverview());


    }
}
