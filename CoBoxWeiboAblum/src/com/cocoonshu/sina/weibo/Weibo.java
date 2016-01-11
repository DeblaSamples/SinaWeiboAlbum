package com.cocoonshu.sina.weibo;

import java.lang.ref.WeakReference;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.cocoonshu.network.HttpListener;
import com.cocoonshu.network.HttpRequest;
import com.cocoonshu.network.HttpRequestor;
import com.cocoonshu.sina.weibo.util.Config;

import android.content.Context;

/**
 * Weibo interface for Android
 * @author Cocoonshu
 * @date   2016-01-11
 */
public class Weibo {

    private static Weibo           sInstance        = null;
    private WeakReference<Context> mRefContext      = null;
    private AccountManager         mAccountManager  = null;
    private HttpRequestor          mHttpRequestor   = null;
    private ExecutorService        mHttpExecutor    = null;
    
    public static Weibo createInstance(Context context) {
        if (sInstance == null) {
            sInstance = new Weibo(context);
        }
        return sInstance;
    }
    
    public static Weibo getInstance() {
        return sInstance;
    }
    
    private Weibo(Context context) {
        mRefContext     = new WeakReference<Context>(context);
        mAccountManager = new AccountManager(context);
        mHttpRequestor  = new HttpRequestor();
        mHttpExecutor   = Executors.newFixedThreadPool(Config.Network.HttpRequestThreadCount);
        mHttpRequestor.setExecutor(mHttpExecutor);
    }
    
    public AccountManager getAccountManager() {
        return mAccountManager;
    }

    /**
     * 
     * @param context
     * @param callback
     */
    public void authorize(Context context, Runnable callback) {
        mAccountManager.authorize(context, callback);
    }

    /**
     * 
     * @param request
     * @param listener
     */
    final void submitHttpRequest(HttpRequest request, HttpListener listener) {
        mHttpRequestor.request(request, listener);
    }

}
