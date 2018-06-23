package com.ankushgrover.popularmovies.utils;

import android.app.Activity;
import android.net.Uri;
import android.support.annotation.IdRes;
import android.widget.TextView;

public class TextUtils {

    /**
     * Method to create Poster path.
     *
     * @param part
     * @return
     */
    public static String makePosterPath(String part) {

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

    /**
     * Method to create Banner path.
     *
     * @param part
     * @return
     */
    public static String makeBannerPath(String part) {

        //http://image.tmdb.org/t/p/w185/gBmrsugfWpiXRh13Vo3j0WW55qD.jpg


        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("image.tmdb.org")
                .appendPath("t")
                .appendPath("p")
                .appendPath("w500")
                .appendEncodedPath(part);
        return builder.build().toString();

    }

    public static void setText(Activity activity, @IdRes int viewId, String text) {
        ((TextView) activity.findViewById(viewId)).setText(text);
    }

    public static boolean isEmpty(String s) {

        return android.text.TextUtils.isEmpty(s);
    }


}
