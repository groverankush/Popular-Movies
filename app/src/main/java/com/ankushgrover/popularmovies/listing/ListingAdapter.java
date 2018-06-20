package com.ankushgrover.popularmovies.listing;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ankushgrover.popularmovies.R;
import com.ankushgrover.popularmovies.data.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Ankush Grover(ankush.grover@finoit.co.in) on 19/6/18.
 */
public class ListingAdapter extends RecyclerView.Adapter<ListingAdapter.ViewHolder> {

    private String TAG = ListingAdapter.class.getSimpleName();
    private Context context;
    private List<Movie> movies;

    public ListingAdapter(Context context, List<Movie> movies) {

        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "create");


        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_movie,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Log.d(TAG, "bind");

        Picasso.get()
                .load(makePosterPath(movies.get(position).getPosterPath()))
                .placeholder(R.drawable.ic_image_black_24dp)
                .into(holder.banner);
    }


    @Override
    public int getItemCount() {

        Log.d(TAG, "item_count");
        return movies.size();
    }

    private String makePosterPath(String part) {

        //http://image.tmdb.org/t/p/w185/gBmrsugfWpiXRh13Vo3j0WW55qD.jpg


        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("image.tmdb.org")
                .appendPath("t")
                .appendPath("p")
                .appendPath("w185")
                .appendEncodedPath(part);
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
