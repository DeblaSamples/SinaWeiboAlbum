package com.cocoonshu.sina.weibo.util;

import android.os.Looper;

/**
 * Some utils for thread function
 * @author Cocoonshu
 * @date   2016-01-06
 */
public class ThreadUtil {

    /**
     * Detect wether current thread is Main thread
     * @return Current is running in Main thread return true
     */
    public static boolean isCurrentInMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }
    
}
