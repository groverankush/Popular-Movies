package com.ankushgrover.popularmovies.listing;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.ankushgrover.popularmovies.R;
import com.ankushgrover.popularmovies.architecture.BaseActivity;
import com.ankushgrover.popularmovies.data.source.MoviesRepository;
import com.ankushgrover.popularmovies.settings.SettingsActivity;

public class ListingActivity extends BaseActivity implements ListingContract.View {

    private static final int SETTINGS_REQUEST_CODE = 1000;
    private ListingPresenter presenter;
    private ListingViewModel model;
    private ListingAdapter adapter;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == SETTINGS_REQUEST_CODE) {
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


        model = ViewModelProviders.of(this).get(ListingViewModel.class);
        presenter = new ListingPresenter(MoviesRepository.getInstance(), model, this);

        initAdapter();


    }

    private void initAdapter() {
        RecyclerView recycler = findViewById(R.id.recycler);
        adapter = new ListingAdapter(this, model.getMovies());
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(manager);
        recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
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
        adapter.notifyDataSetChanged();
    }
}
