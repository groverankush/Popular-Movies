package com.ankushgrover.popularmovies.detail;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ankushgrover.popularmovies.R;
import com.ankushgrover.popularmovies.architecture.BaseActivity;
import com.ankushgrover.popularmovies.data.models.movie.Movie;
import com.ankushgrover.popularmovies.data.models.review.Review;
import com.ankushgrover.popularmovies.data.models.trailer.Trailer;
import com.ankushgrover.popularmovies.data.source.DataManager;
import com.ankushgrover.popularmovies.data.source.repositories.MoviesRepository;
import com.ankushgrover.popularmovies.utils.TextUtils;
import com.squareup.picasso.Picasso;

import java.util.Locale;
import java.util.Random;

public class DetailsActivity extends BaseActivity implements DetailsContract.View {

    public static final String MOVIE_DETAIL = "movieDetail";
    private static final int COLORS[] = {R.color.color_1, R.color.color_2, R.color.color_3, R.color.color_4, R.color.color_5, R.color.color_6, R.color.color_7, R.color.color_8, R.color.color_9, R.color.color_10};
    private DetailViewModel model;
    private Random random;
    private DetailsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);

        Movie movie = getIntent().getParcelableExtra(MOVIE_DETAIL);

        random = new Random();
        model = ViewModelProviders.of(this).get(DetailViewModel.class);
        model.setMovie(movie);

        presenter = new DetailsPresenter(DataManager.getInstance(), model, this);

        getSupportActionBar().setTitle(movie.getTitle());


    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.subscribe();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.unsubscribe();
    }

    @Override
    public void setupHead() {
        Movie movie = model.getMovie();
        Picasso.get()
                .load(TextUtils.makePosterPath(movie.getPosterPath()))
                .placeholder(R.drawable.placeholder_portrait)
                .into((ImageView) findViewById(R.id.iv_poster));

        Picasso.get()
                .load(TextUtils.makeBannerPath(movie.getBackdropPath()))
                .placeholder(R.drawable.placeholder_landscape)
                .into((ImageView) findViewById(R.id.iv_banner));


        TextUtils.setText(this, R.id.tv_name, TextUtils.isEmpty(movie.getTitle()) ? getString(R.string.title_not_available) : movie.getTitle());
        TextUtils.setText(this, R.id.tv_year, TextUtils.isEmpty(movie.getReleaseDate()) ? getString(R.string.release_date_not_available) : movie.getReleaseDate().split("-")[0]);
        TextUtils.setText(this, R.id.tv_ratings, String.format(Locale.ENGLISH, "%.1f", movie.getVoteAverage()));
        TextUtils.setText(this, R.id.tv_description, TextUtils.isEmpty(movie.getOverview()) ? getString(R.string.description_not_available) : movie.getOverview());
        FloatingActionButton like = findViewById(R.id.btn_like);
        like.setOnClickListener(v -> {
        });
    }


    @Override
    public void onReceiveTrailers() {
        LinearLayout trailersLayout = findViewById(R.id.trailers);
        if (trailersLayout.getChildCount() != model.getTrailers().size() || model.getTrailers().size() == 0) {
            trailersLayout.removeAllViews();
            if (model.getTrailers().size() == 0) {
                TextView message = (TextView) LayoutInflater.from(this).inflate(R.layout.layout_message, trailersLayout, false);
                message.setText(R.string.no_trailers);
                trailersLayout.addView(message);
            } else {
                for (Trailer trailer : model.getTrailers()) {

                    if (trailer.getSite().equals("YouTube")) {
                        View trailerLayout = LayoutInflater.from(this).inflate(R.layout.item_trailer, trailersLayout, false);
                        trailerLayout.setOnClickListener(v -> openWebPage(TextUtils.makeTrailerPath(trailer.getKey())));
                        Picasso.get()
                                .load(TextUtils.makeTrailerThumbPath(trailer.getKey()))
                                .placeholder(R.drawable.placeholder_landscape)
                                .into((ImageView) trailerLayout.findViewById(R.id.thumb));
                        trailersLayout.addView(trailerLayout);
                    }
                }
            }
        }
    }

    @Override
    public void errorLoadingTrailers() {

        LinearLayout trailersLayout = findViewById(R.id.trailers);
        trailersLayout.removeAllViews();
        TextView message = (TextView) LayoutInflater.from(this).inflate(R.layout.layout_message, trailersLayout, false);
        message.setText(R.string.error_loading_trailers);
        trailersLayout.addView(message);

    }

    @Override
    public void onReceiveReviews() {

        LinearLayout reviewsLayout = findViewById(R.id.reviews);
        if (reviewsLayout.getChildCount() != model.getReviews().size() || model.getReviews().size() == 0) {
            reviewsLayout.removeAllViews();
            if (model.getReviews().size() == 0) {
                TextView message = (TextView) LayoutInflater.from(this).inflate(R.layout.layout_message, reviewsLayout, false);
                message.setText(R.string.no_reviews);
                reviewsLayout.addView(message);
            } else {
                for (Review review : model.getReviews()) {


                    View reviewLayout = LayoutInflater.from(this).inflate(R.layout.item_review, reviewsLayout, false);

                    String name = review.getAuthor() == null ? "Unknown" : review.getAuthor();

                    ((TextView) reviewLayout.findViewById(R.id.tv_name)).setText(name);
                    ((TextView) reviewLayout.findViewById(R.id.tv_review)).setText(review.getContent());
                    TextView letter = reviewLayout.findViewById(R.id.tv_letter);
                    letter.setText(String.valueOf(name.charAt(0)));
                    setLetterColor(letter);


                    reviewsLayout.addView(reviewLayout);

                }
            }
        }

    }

    @Override
    public void errorLoadingReviews() {
        LinearLayout trailersLayout = findViewById(R.id.trailers);
        trailersLayout.removeAllViews();
        TextView message = (TextView) LayoutInflater.from(this).inflate(R.layout.layout_message, trailersLayout, false);
        message.setText(R.string.error_loading_reviews);
        trailersLayout.addView(message);
    }

    @Override
    public void generalResponse(int message) {

    }

    @Override
    public void onError(int errorId) {

    }

    private void openWebPage(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private void setLetterColor(View v) {

        GradientDrawable bgShape = (GradientDrawable) v.getBackground();
        bgShape.setColor(ContextCompat.getColor(this, COLORS[random.nextInt(COLORS.length)]));
    }
}
