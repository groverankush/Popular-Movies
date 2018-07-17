package com.ankushgrover.popularmovies.architecture;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by Ankush Grover(ankushgrover02@gmail.com) on 11/6/18.
 */
abstract public class BaseActivity extends AppCompatActivity {

    private Toast toast;

    /**
     * Method used to switch from current activity to other
     *
     * @param destinationActivity activity to open
     */
    public void switchActivity(Class<?> destinationActivity) {
        startActivity(new Intent(this, destinationActivity));
    }

    /**
     * Method used to switch from current activity to other with data
     *
     * @param destinationActivity activity to open
     * @param bundle              data that carry to destination activity
     */
    public void switchActivity(Class<?> destinationActivity, Bundle bundle) {
        Intent intent = new Intent(this, destinationActivity);
        if (bundle != null)
            intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * method used to starting another activity for result
     *
     * @param destinationActivity activity to open
     * @param bundle              data that carry to destination activity
     * @param requestCode         result code
     */
    public void switchActivity(Class<?> destinationActivity, Bundle bundle, int requestCode) {
        Intent intent = new Intent(this, destinationActivity);
        if (bundle != null)
            intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
    }


    /**
     * method used to starting another activity for result
     *
     * @param destinationActivity activity to open
     * @param requestCode         result code
     */
    public void switchActivity(Class<?> destinationActivity, int requestCode) {
        Intent intent = new Intent(this, destinationActivity);
        startActivityForResult(intent, requestCode);
    }

    /**
     * Method to back activity with result ok with out data
     */
    public void backActivityWithResultOk() {
        backActivityWithResultOk(null);
    }

    /**
     * Method to back activity with result ok with data
     * @param bundle
     */
    public void backActivityWithResultOk(Bundle bundle) {
        if (bundle != null) {
            Intent intent = new Intent();
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
        } else
            setResult(RESULT_OK);
        finish();
    }


    /**
     * Method used to display short duration toast
     *
     * @param message message to be displayed
     */
    public void displayToast(String message) {
        displayToast(message, true);
    }


    /**
     * Method used to display short duration toast
     *
     * @param resId resource id of the message string to be displayed
     */
    public void displayToast(int resId) {
        displayToast(getString(resId));
    }

    public void displayToast(@StringRes int resId, boolean replaceExisting) {
        displayToast(getString(resId), replaceExisting);
    }

    public void displayToast(String message, boolean replaceExisting) {

        if (TextUtils.isEmpty(message) || message.equalsIgnoreCase("null"))
            return;
        if (toast != null && replaceExisting)
            toast.cancel();
        toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }
}
