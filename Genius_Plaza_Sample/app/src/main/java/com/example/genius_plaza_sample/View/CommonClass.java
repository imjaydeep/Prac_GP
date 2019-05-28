package com.example.genius_plaza_sample.View;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class CommonClass {

    private Context context;
    private Activity activity;

    public CommonClass(Context context) {
        this.context = context;
        this.activity = (Activity) context;
    }
    public void showToast(String message) {
        MessageUtils.showToast(context, message);
    }

    public void showToast(int resId) {
        MessageUtils.showToast(context, resId);
    }

    public void showComingSoon(String moduleName) {
        MessageUtils.showToast(context, moduleName + " - Coming Soon…");
    }
    public static void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (view == null) {
            view = new View(context);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
