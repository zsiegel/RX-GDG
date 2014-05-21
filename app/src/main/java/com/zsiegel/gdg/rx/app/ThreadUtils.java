package com.zsiegel.gdg.rx.app;

import android.os.Looper;

/**
 * @author zsiegel (zac.s@akta.com)
 */
public class ThreadUtils {

    public static void errorOnUIThread() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new RuntimeException("This method should not be called on the main thread");
        }
    }
}
