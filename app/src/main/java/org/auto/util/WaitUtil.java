package org.auto.util;

import android.os.SystemClock;

public class WaitUtil {

    public static void sleep(long m) {
        SystemClock.sleep(m);
    }

    public static void sleep() {
        sleep(500);
    }

}
