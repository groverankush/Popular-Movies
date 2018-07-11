package com.ankushgrover.popularmovies.data.models.movie;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ankush Grover(ankush.grover@finoit.co.in) on 12/6/18.
 */
public class NetworkResult implements Parcelable {

    public static final Creator<NetworkResult> CREATOR = new Creator<NetworkResult>() {
        @Override
        public NetworkResult createFromParcel(Parcel in) {
            return new NetworkResult(in);
        }

        @Override
        public NetworkResult[] newArray(int size) {
            return new NetworkResult[size];
        }
    };
    private int page;
    @SerializedName("total_results")
    @Expose
    private long totalResults;
    @SerializedName("total_pages")
    @Expose
    private long totalPages;
    private List<Movie> results;

    protected NetworkResult(Parcel in) {
        page = in.readInt();
        totalResults = in.readLong();
        totalPages = in.readLong();
        results = in.createTypedArrayList(Movie.CREATOR);
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public long getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(long totalResults) {
        this.totalResults = totalResults;
    }

    public long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(page);
        dest.writeLong(totalResults);
        dest.writeLong(totalPages);
        dest.writeTypedList(results);
    }
}
