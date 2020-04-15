package org.auto.util;

import android.util.Log;

public class LogUtils {

    public static String TAG = "AUOT";

    public static void d(String msg) {
        Log.d("AUTO", msg);
    }

    public static void d(Boolean b) {
        Log.d("AUTO", b.toString());
    }

    public static void e(String msg) {
        Log.e(TAG, msg);
    }
}
