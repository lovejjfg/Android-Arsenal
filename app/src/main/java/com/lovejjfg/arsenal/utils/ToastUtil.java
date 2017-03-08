package com.lovejjfg.arsenal.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Joe on 2017/1/4.
 * Email lovejjfg@gmail.com
 */
public class ToastUtil {
    private static Toast TOAST;

    public static void initToast(Context context) {
        if (TOAST == null) {
            TOAST = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        }
    }

    public static void showToast(Context context, String msg) {
        showToast(context, msg, Toast.LENGTH_SHORT);
    }

    public static void showToast(Context context, String msg, String duration) {
        initToast(context);
        TOAST.setText(msg);
        TOAST.setDuration(Toast.LENGTH_SHORT);
        TOAST.show();
    }

    public static void showToast(Context context, String msg, int duration) {
        initToast(context);
        TOAST.setText(msg);
        TOAST.setDuration(duration);
        TOAST.show();
    }
}
