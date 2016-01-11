package com.cocoonshu.sina.weibo.network;

import com.cocoonshu.network.HttpRequest;
import com.cocoonshu.sina.weibo.Account;

/**
 * Request with the access token api
 * @author Cocoonshu
 * @date   2016-01-11
 */
public class AccessTokenRequest extends HttpRequest {

    private Account mAccount = null;
    
    public AccessTokenRequest(Account account) {
        mAccount = account;
    }
    
    @Override
    protected boolean onRequestPrepare() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    protected Object onRequest() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected void onPostRequest() {
        // TODO Auto-generated method stub
        
    }

}
