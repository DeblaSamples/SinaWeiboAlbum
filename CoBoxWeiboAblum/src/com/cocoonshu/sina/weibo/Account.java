package com.cocoonshu.sina.weibo;

public class Account {

    private String mAppKey            = null;
    private String mAppSecret         = null;
    private String mAuthorizationCode = null;
    private String mAccessToken       = null;
    private String mUID               = null;
    private long   mRemindIn          = 0;
    private long   mExpiresIn         = 0;

    public Account() {
        
    }

    public Account(String appKey, String appSecret) {
        mAppKey    = appKey;
        mAppSecret = appSecret;
    }

    public Account(Account account) {
        mAppKey    = account.getAppKey();
        mAppSecret = account.getAppSecret();
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

    public final void setExpiresIn(long expiresIn) {
        mExpiresIn = expiresIn;
    }
    
    public final long getExpiresIn() {
        return mExpiresIn;
    }
    
    public final void setRemindIn(long remindIn) {
        mRemindIn = remindIn;
    }
    
    public final long getRemindIn() {
        return mRemindIn;
    }
    
    public final void setUid(String uid) {
        mUID = uid;
    }
    
    public final String getUid() {
        return mUID;
    }
}
