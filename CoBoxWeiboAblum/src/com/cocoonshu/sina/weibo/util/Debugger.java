package com.cocoonshu.sina.weibo.util;

import junit.framework.Assert;
import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;

/**
 * Debug manager
 * @author Cocoonshu
 * @date   2016-01-11
 */
public class Debugger {

    public static void i(String tag, String message) {
        if (Config.Debug.ENABLE && Config.Debug.ENABLE_INFO) {
            Log.i(tag, message);
        }
    }

    public static void v(String tag, String msg) {
        if (Config.Debug.ENABLE && Config.Debug.ENABLE_VERBOSE) {
            Log.v(tag, msg);
        }
    }

    public static void d(String tag, String message) {
        if (Config.Debug.ENABLE && Config.Debug.ENABLE_INFO) {
            Log.d(tag, message);
        }
    }
    
    public static void w(String tag, String message) {
        if (Config.Debug.ENABLE && Config.Debug.ENABLE_INFO) {
            Log.w(tag, message);
        }
    }
    
    public static void e(String tag, String message) {
        if (Config.Debug.ENABLE && Config.Debug.ENABLE_INFO) {
            Log.e(tag, message);
        }
    }

    public static void printTrace(String tag, String message, Throwable throwable) {
        if (Config.Debug.ENABLE && Config.Debug.ENABLE_PRINT_TRACE) {
            Log.v(tag, message, throwable);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void throwAssert(String tag, String msg, String expected, String actual) {
        if (Config.Debug.ENABLE && Config.Debug.ENABLE_ASSERT) {
            Assert.format("[" + tag + "]" + msg, expected, actual);
        }
    }
}
