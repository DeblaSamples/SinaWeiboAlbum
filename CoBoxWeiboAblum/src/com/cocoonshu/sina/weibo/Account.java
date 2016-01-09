package com.cocoonshu.sina.weibo;

public class Account {

    private String mAppKey            = null;
    private String mAppSecret         = null;
    private String mAuthorizationCode = null;
    private String mAccessToken       = null;

    public Account() {
        
    }

    public Account(String appKey, String appSecret) {
        mAppKey    = appKey;
        mAppSecret = appSecret;
    }

    public final String getAppKey() {
        return mAppKey;
    }

    public final void setAppKey(String appKey) {
        mAppKey = appKey;
    }

    public final String getAppSecret() {
        return mAppSecret;
    }

    public final void setAppSecret(String appSecret) {
        mAppSecret = appSecret;
    }
    
    public final String getAccessToken() {
        return mAccessToken;
    }

    public final void setAccessToken(String accessToken) {
        mAccessToken = accessToken;
    }

    public final void setAuthorizationCode(String authorizationCode) {
        mAuthorizationCode = authorizationCode;
    }

    public final String getAuthorizationCode() {
        return mAuthorizationCode;
    }

}
