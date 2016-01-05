package com.cocoonshu.coboxweiboablum;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class AuthorizeActivity extends Activity {

    private WebView mWebAuthorize = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorize);
        setupViews();
        setupListeners();
    }

    private void setupViews() {
        mWebAuthorize = (WebView) findViewById(R.id.WebViewAuthorize);
    }

    private void setupListeners() {
        mWebAuthorize.setWebViewClient(new WebViewClient() {
            
        });
    }
}
