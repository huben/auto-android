package org.auto.util;

import android.content.Context;
import android.util.DisplayMetrics;

public class ScreenUtils {

    public static int getWindowsWidth(Context ctx) {
        DisplayMetrics outMetrics = ctx.getApplicationContext().getResources().getDisplayMetrics();
        return outMetrics.widthPixels;
    }

    public static int getWindowsHeight (Context ctx) {
        DisplayMetrics outMetrics = ctx.getApplicationContext().getResources().getDisplayMetrics();
        return outMetrics.heightPixels;
    }
}
