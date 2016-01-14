package com.cocoonshu.sina.weibo.network.auth;

import com.cocoonshu.network.HttpRequest;
import com.cocoonshu.network.HttpResponse;
import com.cocoonshu.sina.weibo.Account;

/**
 * Request with the access token api
 * @author Cocoonshu
 * @date   2016-01-11
 */
public class AccessTokenRequest extends HttpRequest {

    private static final String TAG = "AccessTokenRequest";
    
    private Account mAccount = null;
    
    public AccessTokenRequest(Account account) {
        mAccount = account;
        setHttpApi(new AccessToken());
    }
    
    public Account getAccount() {
        return mAccount;
    }
    
    @Override
    protected boolean onRequestPrepare() {
        if (mAccount != null) {
            AccessToken api = (AccessToken) getHttpApi();
            api.setAccount(mAccount);
            return true;
        } else {
            return true;
        }
    }

    @Override
    protected void onPostRequest(HttpResponse response) {
        String json = (String) response.getResponseData();
        AccessToken api = (AccessToken) getHttpApi();
        Account account = (Account) api.parseResponse(json, null);
        mAccount.setUid(account.getUid());
        mAccount.setRemindIn(account.getRemindIn());
        mAccount.setExpiresIn(account.getExpiresIn());
        mAccount.setAccessToken(account.getAccessToken());
    }

}
