package com.cocoonshu.sina.weibo;

import com.cocoonshu.coboxweiboablum.R;
import com.cocoonshu.sina.weibo.network.Authorize;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class AuthorizePage extends Activity {

    private WebView mWebAuthorize = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorize);
        setupViews();
        setupListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Authorize authorize = new Authorize();
        Account   account   = AccountManager.getInstance().getAccount();
        mWebAuthorize.loadUrl(authorize.getApiUrl() + authorize.getApiParameterUrl(account));
    }

    private void setupViews() {
        mWebAuthorize = (WebView) findViewById(R.id.WebViewAuthorize);
    }

    private void setupListeners() {
        mWebAuthorize.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                
                return super.shouldOverrideUrlLoading(view, url);
            }

        });
    }
}
