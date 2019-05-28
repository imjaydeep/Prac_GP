package com.example.genius_plaza_sample.View;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.view.Gravity;
import android.widget.Toast;

public class MessageUtils {
    /* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- SHOW TOAST -=-=-=-=-=-=-=-=-=-=-=-=-=- */
    public static void showToast(@NonNull Context context, @NonNull String message) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void showToast(@NonNull Context context, @StringRes int resId) {
        Toast toast = Toast.makeText(context, resId, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
