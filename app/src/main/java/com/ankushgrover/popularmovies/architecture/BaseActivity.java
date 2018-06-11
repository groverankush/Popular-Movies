package com.ankushgrover.popularmovies.architecture;

import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by Ankush Grover(ankush.grover@finoit.co.in) on 11/6/18.
 */
abstract public class BaseActivity extends AppCompatActivity {
    /**
     * Method used to display short duration toast
     *
     * @param message message to be displayed
     */
    public void displayToast(String message) {
        if (TextUtils.isEmpty(message) || message.equalsIgnoreCase("null"))
            return;
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    /**
     * Method used to display short duration toast
     *
     * @param resId resource id of the message string to be displayed
     */
    public void displayToast(int resId) {
        displayToast(getString(resId));
    }
}
