package com.cocoonshu.sina.weibo.network;

import com.cocoonshu.network.HttpRequest;
import com.cocoonshu.network.HttpResponse;
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
        if (mAccount != null) {
            
        }
        return false;
    }

    @Override
    protected void onPostRequest(HttpResponse response) {
        // TODO Auto-generated method stub
        
    }

}
