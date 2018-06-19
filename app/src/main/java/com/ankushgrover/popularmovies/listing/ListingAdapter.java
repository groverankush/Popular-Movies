package com.ankushgrover.popularmovies.listing;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ankushgrover.popularmovies.R;
import com.ankushgrover.popularmovies.data.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Ankush Grover(ankush.grover@finoit.co.in) on 19/6/18.
 */
public class ListingAdapter extends RecyclerView.Adapter<ListingAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Movie> movies;

    public ListingAdapter(Context context, ArrayList<Movie> movies) {

        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_movie, parent));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.get()
                .load(makePosterPath(movies.get(position).getPosterPath()))
                .into(holder.banner);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    private String makePosterPath(String part) {

        //http://image.tmdb.org/t/p/w185/gBmrsugfWpiXRh13Vo3j0WW55qD.jpg


        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("image.tmdb.org")
                .appendPath("t")
                .appendPath("p")
                .appendPath("w185")
                .appendPath(part);
        return builder.build().toString();


    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView banner;

        public ViewHolder(View itemView) {
            super(itemView);

            banner = itemView.findViewById(R.id.iv_banner);

        }
    }

}
