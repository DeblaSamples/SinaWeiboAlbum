package com.cocoonshu.sina.weibo;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.cocoonshu.network.HttpCode;
import com.cocoonshu.network.HttpListener;
import com.cocoonshu.network.HttpRequest;
import com.cocoonshu.network.HttpResponse;
import com.cocoonshu.sina.weibo.network.auth.AccessTokenRequest;
import com.cocoonshu.sina.weibo.util.Config;
import com.cocoonshu.sina.weibo.util.Debugger;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.Log;

public class AccountManager {

    private static final String    TAG              = "AccountManager";
    private static final String    KEY_APPKEY       = "SinaWeiboAppKey";
    private static final String    KEY_APPSECRET    = "SinaWeiboAppSecret";
    private WeakReference<Context> mRefContext      = null;
    private Account                mAccount         = new Account();
    private List<OnAuthListener>   mOnAuthListeners = null;
    private List<Runnable>         mAuthCallbacks   = null;

    public static interface OnAuthListener {
        void OnAuthSuccessed(Account account, String accessToken);
    }

    AccountManager(Context context) {
        mOnAuthListeners = new LinkedList<OnAuthListener>();
        mAuthCallbacks   = new LinkedList<Runnable>();
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

    private void findAuthenticationCredentials() {
        Context context = mRefContext.get();

        String          packageName     = null;
        PackageManager  packageManager  = null;
        ApplicationInfo applicationInfo = null;
        Bundle          appMetaData     = null;
        String          appKey          = null;
        String          appSecret       = null;

        try {
            packageName     = context.getPackageName();
            packageManager  = context.getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
            appMetaData     = applicationInfo.metaData;
            appKey          = String.valueOf(appMetaData.getInt(KEY_APPKEY));
            appSecret       = appMetaData.getString(KEY_APPSECRET);
        } catch (NameNotFoundException exp) {
            Debugger.printTrace(
                    TAG,
                    "[findAuthenticationCredentials] Credentials read failed. Cannot find package " + packageName,
                    exp);
        }
        Log.i(TAG, "[findAuthenticationCredentials] Authorize credential： \n"
                 + "                                                 AppKey:    " + appKey + "\n"
                 + "                                                 AppSecret: " + appSecret);

        synchronized (mAccount) {
            mAccount.setAppKey(appKey);
            mAccount.setAppSecret(appSecret);
        }
    }

    /**
     * Obtain an new Account instance that has
     * copied account content of AccountManager.
     * @return
     */
    public Account obtainAccount() {
        return new Account(mAccount);
    }
    
    /**
     * Get the app key of the sina weibo api
     * @return An app key string, or null.
     */
    public final String getAppKey() {
        synchronized (mAccount) {
            return mAccount.getAppKey();
        }
    }

    /**
     * Get the app secret of the sina weibo api
     * @return An app secret string, or null.
     * @see #getAppKey()
     */
    public final String getAppSecret() {
        synchronized (mAccount) {
            return mAccount.getAppSecret();
        }
    }
    
    /**
     * Get access token.
     */
    public final String getAccessToken() {
        synchronized (mAccount) {
            return mAccount.getAccessToken();
        }
    }
    
    /**
     * Get uid of current user
     * @return
     */
    public final String getUID() {
        synchronized (mAccount) {
            return mAccount.getUid();
        }
    }
    
    /**
     * Set an authorization code to the inner account
     * instance of AccountManager.
     * @param authorizeCode
     */
    final void setAuthorizationCode(String authorizeCode) {
        synchronized (mAccount) {
            mAccount.setAuthorizationCode(authorizeCode);
        }
    }
    
    /**
     * Add a listener to listen authentication state.
     * @param listener
     * @see #removeOnAuthListener(OnAuthListener)
     */
    public final void addOnAuthListener(OnAuthListener listener) {
        if (!mOnAuthListeners.contains(listener)) {
            mOnAuthListeners.add(listener);
        }
    }

    /**
     * Remove an Authentication state listener.
     * @param listener
     * @see #addOnAuthListener(OnAuthListener)
     */
    public final void removeOnAuthListener(OnAuthListener listener) {
        mOnAuthListeners.remove(listener);
    }

    /**
     * Authorize to the weibo api of the sina.
     * @see #unauthorize()
     */
    public void authorize(Context context) {
        if (context != null) {
            Intent intent = new Intent(context, AuthorizePage.class);
            context.startActivity(intent);
        }
    }

    /**
     * Start authorize workflow, and after authorizing, callback
     * will be invoked.
     * @param callback
     */
    public void authorize(Context context, Runnable callback) {
        if (context != null) {
            mAuthCallbacks.add(callback);
            Intent intent = new Intent(Config.Intent.START_AUTHORIZE_PAGE_ACTION);
            intent.putExtra(AuthorizePage.CallbackID, callback.hashCode());
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }
    
    /**
     * Take access token.
     */
    public void takeAccessToken(final int authorizeCallbackID) {
        HttpRequest request = new AccessTokenRequest(new Account(mAccount));
        Weibo.getInstance().submitHttpRequest(request, new HttpListener() {
            
            @Override
            public void onResponed(HttpResponse response) {
                Account account = ((AccessTokenRequest)response.getResquest()).getAccount();
                synchronized (mAccount) {
                    mAccount.setUid(account.getUid());
                    mAccount.setRemindIn(account.getRemindIn());
                    mAccount.setExpiresIn(account.getExpiresIn());
                    mAccount.setAccessToken(account.getAccessToken());
                }
                notifyAuthCallback();
            }
            
            @Override
            public void onError(HttpResponse response, HttpCode code) {
                notifyAuthCallback();
            }
            
            private void notifyAuthCallback() {
                Iterator<Runnable> itr = mAuthCallbacks.iterator();
                while (itr.hasNext()) {
                    Runnable callback = itr.next();
                    if (callback.hashCode() == authorizeCallbackID) {
                        try {
                            callback.run();
                        } finally {
                            itr.remove();
                        }
                        break;
                    }
                }
            }
            
        });
        
    }
    
    /**
     * Unauthorize from the weibo api of the sina.
     * @see #authorize()
     */
    public void unauthorize(Context context) {
        // TODO
    }

}
