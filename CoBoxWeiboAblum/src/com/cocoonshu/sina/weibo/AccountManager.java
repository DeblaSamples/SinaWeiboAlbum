package com.cocoonshu.sina.weibo;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

public class AccountManager {

    private static final String    KEY_APPKEY       = "SinaWeiboAppKey";
    private static final String    KEY_APPSECRET    = "SinaWeiboAppSecret";
    private static AccountManager  sInstance        = null;
    private WeakReference<Context> mRefContext      = null;
    private Account                mAccount         = null;
    private List<OnAuthListener>   mOnAuthListeners = null;

    public static interface OnAuthListener {
        void OnAuthSuccessed(Account account, String accessToken);
    }

    /**
     * Setup account manager, if called {@link #getInstance()} before invoking
     * this method, null will be return.
     * @param context Application context
     * @throws Exception If context is null, exception will be thrown
     * @see #getInstance()
     */
    public static void setup(Context context) throws Exception {
        if (sInstance == null) {
            sInstance = new AccountManager(context);
        } else {
            sInstance.setContext(context);
        }
    }

    /**
     * Get a instance of Account manager, if called this method before invoking
     * {@link #setup(Context)}, null will be return.
     * @return
     * @see #setup(Context)
     */
    public static AccountManager getInstance() {
        return sInstance;
    }

    private AccountManager(Context context) throws Exception {
        mOnAuthListeners = new LinkedList<OnAuthListener>();
        setContext(context);
        findAuthenticationCredentials();
    }

    private final void setContext(Context context) {
        if (mRefContext != null) {
            mRefContext.clear();
            mRefContext = null;
        }
        mRefContext = new WeakReference<Context>(context);
    }

    private void findAuthenticationCredentials() throws Exception {
        Context context = mRefContext.get();
        if (context == null) {
            throw new Exception("Context cannot be null");
        }

        String          packageName     = null;
        PackageManager  packageManager  = null;
        ApplicationInfo applicationInfo = null;
        Bundle          appMetaData     = null;
        String          appKey          = null;
        String          appSecret       = null;

        packageName     = context.getPackageName();
        packageManager  = context.getPackageManager();
        applicationInfo = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
        appMetaData     = applicationInfo.metaData;
        appKey          = appMetaData.getString(KEY_APPKEY);
        appSecret       = appMetaData.getString(KEY_APPSECRET);

        if (appKey == null || appKey.isEmpty()
         || appSecret == null || appSecret.isEmpty()) {
            mAccount = null;
        } else {
            mAccount = new Account(appKey, appSecret);
        }
    }

    /**
     * Add a listener to listen Authentication state
     * @param listener
     * @see #removeOnAuthListener(OnAuthListener)
     */
    public final void addOnAuthListener(OnAuthListener listener) {
        if (!mOnAuthListeners.contains(listener)) {
            mOnAuthListeners.add(listener);
        }
    }

    /**
     * Remove an Authentication state listener
     * @param listener
     * @see #addOnAuthListener(OnAuthListener)
     */
    public final void removeOnAuthListener(OnAuthListener listener) {
        mOnAuthListeners.remove(listener);
    }

    /**
     * Get the app key of the sina weibo api
     * @return An app key string, or null
     */
    public final String getAppKey() {
        return mAccount == null ? null : mAccount.getAppKey();
    }

    /**
     * Get the app secret of the sina weibo api
     * @return An app secret string, or null
     * @see #getAppKey()
     */
    public final String getAppSecret() {
        return mAccount == null ? null : mAccount.getAppSecret();
    }

    /**
     * Authorize to the weibo api of the sina
     * @see #unauthorize()
     */
    public void authorize() {
        // TODO
    }

    /**
     * Unauthorize from the weibo api of the sina
     * @see #authorize()
     */
    public void unauthorize() {
        // TODO
    }
}
