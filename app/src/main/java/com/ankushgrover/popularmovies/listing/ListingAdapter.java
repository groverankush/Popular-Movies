package com.ankushgrover.popularmovies.listing;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
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
public class ListingAdapter extends RecyclerBaseAdapter {

    private String TAG = ListingAdapter.class.getSimpleName();
    private Context context;
    private List<Movie> movies;
    private ListingContract.View listingView;

    public ListingAdapter(Context context, List<Movie> movies) {
        super(context, 0);

        this.context = context;
        this.movies = movies;
        this.listingView = (ListingContract.View) context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_movie, parent, false));

    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ViewHolder) {
            ViewHolder holder = (ViewHolder) viewHolder;

            Picasso.get()
                    .load(makePosterPath(movies.get(position).getPosterPath()))
                    .placeholder(R.drawable.ic_image_black_24dp)
                    .into(holder.banner);
        }
    }

    @Override
    public int getTotalItemCount() {
        return movies.size();
    }

    @Override
    public int getNormalItemViewType(int position) {
        return 0;
    }

    @Override
    public boolean requireNext() {
        return listingView.fetchMoreMovies();
    }

    /*@NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "create");


        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_movie, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Log.d(TAG, "bind");

        Picasso.get()
                .load(makePosterPath(movies.get(position).getPosterPath()))
                .placeholder(R.drawable.ic_image_black_24dp)
                .into(holder.banner);
    }
*/

    /*@Override
    public int getItemCount() {

        return movies.size();
    }*/

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
