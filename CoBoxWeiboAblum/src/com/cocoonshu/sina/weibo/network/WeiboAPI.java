package com.cocoonshu.sina.weibo.network;

import com.cocoonshu.sina.weibo.Account;

/**
 * API repository
 * @author Cocoonshu@Plf.MediaCenter.Gallery
 * @date   2016-01-07
 */
public class WeiboAPI {

    public static final String AUTH_URL                 = "https://open.weibo.cn/";
    public static final String API_URL                  = "https://api.weibo.com/";
    public static final String AUTH_REDIRECT_URL        = "https://api.weibo.com/oauth2/default.html";
    
    public static final String API_AUTHORIZE            = "oauth2/authorize";
    public static final String API_ACCESS_TOKEN         = "oauth2/access_token";
    public static final String API_GET_TOKEN_INFO       = "oauth2/get_token_info";
    public static final String API_REVOKE_OAUTH2        = "oauth2/revokeoauth2";
    
    public static final String PARAM_CODE               = "code";
    public static final String PARAM_CLIENT_ID          = "client_id";
    public static final String PARAM_CLIENT_SECRET      = "client_secret";
    public static final String PARAM_DISPLAY            = "display";
    public static final String PARAM_FORCE_LOGIN        = "forcelogin";
    public static final String PARAM_GRANT_TYPE         = "grant_type";
    public static final String PARAM_REDIRECT_URI       = "redirect_uri";
    public static final String PARAM_RESPONSE_TYPE      = "response_type";
    
    public static final String VALUE_CODE               = "code";
    public static final String VALUE_MOBILE             = "mobile";
    public static final String VALUE_AUTHORIZATION_CODE = "authorization_code";
    
    public static class JSON {
        public static final String KEY_ACCESS_TOKEN = "access_token";
        public static final String KEY_EXPIRES_IN   = "expires_in";
        public static final String KEY_REMIND_IN    = "remind_in";
        public static final String KEY_UID          = "uid";
        public static final String KEY_APPKEY       = "appkey";
        public static final String KEY_CREATE_AT    = "create_at";
        public static final String KEY_EXPIRE_IN    = "expires_in";
        public static final String KEY_RESULT       = "result";
    }
    
    private Account mAccount = null;
    
    public WeiboAPI(Account account) {
        mAccount = account;
    }
}
