package com.ankushgrover.popularmovies.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.ankushgrover.popularmovies.app.App;


public class NetworkUtils {


    /**
     * method used to check whether device is connected to network or not
     *
     * @return true network available, false network unavailable
     */
    public static boolean isConnectedToNetwork() {

        ConnectivityManager connectivity = (ConnectivityManager) App.getApplication()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
