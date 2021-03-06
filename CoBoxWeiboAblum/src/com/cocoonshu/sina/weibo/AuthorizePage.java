package com.cocoonshu.sina.weibo;

import com.cocoonshu.coboxweiboalbum.R;
import com.cocoonshu.sina.weibo.network.WeiboAPI;
import com.cocoonshu.sina.weibo.network.auth.Authorize;
import com.cocoonshu.sina.weibo.util.Debugger;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Authorize page for user
 * @author Cocoonshu
 * @date   2016-01-13
 */
public class AuthorizePage extends Activity {

    protected static final String TAG             = "AuthorizePage";
    public    static final String CallbackID      = "AuthorizeCallbackID";
    public    static final int    ValidCallbackID = 0; 
    
    private WebView   mWebAuthorize        = null;
    private Authorize mAuthorizeApi        = null;
    private String    mAuthorizeUrl        = null;
    private int       mAuthorizeCallbackID = ValidCallbackID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorize);
        setupViews();
        setupListeners();
        setupIntents();
    }

    private void setupIntents() {
        Intent intent = getIntent();
        if (intent != null) {
            mAuthorizeCallbackID = intent.getIntExtra(CallbackID, ValidCallbackID);
        } else {
            mAuthorizeCallbackID = ValidCallbackID;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Account account = Weibo.getInstance().getAccountManager().obtainAccount();
        mAuthorizeApi = new Authorize(account);
        mAuthorizeUrl = mAuthorizeApi.getMethodedUrl();
        mWebAuthorize.loadUrl(mAuthorizeUrl);
        Debugger.i(TAG, "[onResume] Load authorize url: " + mAuthorizeUrl);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setupViews() {
        mWebAuthorize = (WebView) findViewById(R.id.WebViewAuthorize);
        WebSettings webSettings = mWebAuthorize.getSettings();
        webSettings.setBlockNetworkImage(false);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
    }

    private void setupListeners() {
        mWebAuthorize.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Debugger.i(TAG, "[shouldOverrideUrlLoading] Url: " + url);
                if (url.startsWith(WeiboAPI.AUTH_REDIRECT_URL)) {
                    processAuthorize(url);
                    return true;
                } else {
                    return super.shouldOverrideUrlLoading(view, url);
                }
            }

        });
    }
    
    private void processAuthorize(String url) {
        AccountManager accountManager = Weibo.getInstance().getAccountManager();
        String         authorizeCode  = (String) mAuthorizeApi.parseResponse(url, null);
        
        Debugger.i(TAG, "[processAuthorize] Authorize code: " + authorizeCode);
        accountManager.setAuthorizationCode(authorizeCode);
        accountManager.takeAccessToken(mAuthorizeCallbackID);
        finish();
    }
}
