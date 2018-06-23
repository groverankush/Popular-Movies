package com.ankushgrover.popularmovies.listing;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ankushgrover.popularmovies.R;
import com.ankushgrover.popularmovies.architecture.BaseActivity;
import com.ankushgrover.popularmovies.data.source.MoviesRepository;
import com.ankushgrover.popularmovies.detail.DetailsActivity;
import com.ankushgrover.popularmovies.settings.Preferences;
import com.ankushgrover.popularmovies.settings.SettingsActivity;

public class ListingActivity extends BaseActivity implements ListingContract.View {

    private static final int SETTINGS_REQUEST_CODE = 1000;
    private ListingPresenter presenter;
    private ListingViewModel model;
    private ListingAdapter adapter;
    private TextView errorTV;
    private RecyclerView recycler;
    private SwipeRefreshLayout swipe;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == SETTINGS_REQUEST_CODE) {
                getSupportActionBar().setTitle(Preferences.getScreenType());
                model.getMovies().clear();
                model.setResult(null);
                adapter.notifyDataSetChanged();
            }
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.unsubscribe();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.subscribe();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing);


        getSupportActionBar().setTitle(Preferences.getScreenType());
        model = ViewModelProviders.of(this).get(ListingViewModel.class);
        presenter = new ListingPresenter(MoviesRepository.getInstance(), model, this);

        initView();
        initAdapter();


    }

    private void initView() {
        errorTV = findViewById(R.id.tv_error);
        recycler = findViewById(R.id.recycler);
        swipe = findViewById(R.id.swipe);
        model.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                swipe.setRefreshing(aBoolean);
            }
        });

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.fetchMovies(true);
            }
        });

    }

    private void initAdapter() {


        adapter = new ListingAdapter(this, model.getMovies());
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        adapter.setRecyclerView(recycler);
        recycler.setLayoutManager(manager);
        adapter.setOnItemCLickListener(position -> {
            Bundle bundle = new Bundle();
            bundle.putParcelable(DetailsActivity.MOVIE_DETAIL, model.getMovies().get(position));
            switchActivity(DetailsActivity.class, bundle);
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_settings:
                switchActivity(SettingsActivity.class, SETTINGS_REQUEST_CODE);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void generalResponse(int message) {
        displayToast(message);
    }

    @Override
    public void moviesAdded() {
        swipe.setRefreshing(false);
        errorTV.setVisibility(View.GONE);
        recycler.setVisibility(View.VISIBLE);

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onError(int errorId) {

        swipe.setRefreshing(false);

        recycler.setVisibility(model.getMovies().size() == 0 ? View.INVISIBLE : View.VISIBLE);
        errorTV.setVisibility(model.getMovies().size() == 0 ? View.VISIBLE : View.GONE);
        errorTV.setVisibility(View.VISIBLE);

    }


    @Override
    public boolean fetchMoreMovies() {
        return presenter.fetchMovies(false);
    }

}
