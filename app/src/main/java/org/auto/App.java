package org.auto;

import android.app.Application;
import android.os.Handler;


public class App extends Application {

    private static App mInstance;

    public static App getInstance() {
        return mInstance;
    }

    public static Handler mainHandler = new Handler() {
    };

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static void run(Runnable r) {
        mainHandler.post(r);
    }

    public static void run(Runnable r, long delay) {
        mainHandler.postDelayed(r, delay);
    }


}

