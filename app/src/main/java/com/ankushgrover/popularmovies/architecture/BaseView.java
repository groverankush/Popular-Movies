package com.ankushgrover.popularmovies.architecture;

import android.support.annotation.StringRes;

/**
 * Created by Ankush Grover(ankushgrover02@gmail.com) on 5/6/18.
 */
public interface BaseView {

    void generalResponse(@StringRes int message);

    void onError(@StringRes int errorId);


}
