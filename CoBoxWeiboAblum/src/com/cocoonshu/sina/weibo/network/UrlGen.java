package com.cocoonshu.sina.weibo.network;

import com.cocoonshu.sina.weibo.Account;

public class UrlGen {

    private Account mAccount = null;
    
    public UrlGen(Account account) {
        mAccount = account;
    }
    
    /**
     * Generate authorize url
     * @return If account is null, return null
     */
    public final String genAuthorizeUrl() {
        return mAccount == null ? null
                : String.format(UrlConfig.AUTH_URL, 
                        mAccount.getAppKey(), UrlConfig.AUTH_REDIRECT_URL);
    }

    /**
     * Url configuration of the weibo api
     * @author Cocoonshu
     * @date   2016-01-06
     */
    private static class UrlConfig {
        public static final String AUTH_URL           = "https://open.weibo.cn/oauth2/authorize?client_id=%s&display=mobile&redirect_uri=%s&response_type=code";
        public static final String AUTH_REDIRECT_URL  = "https://api.weibo.com/oauth2/default.html";
        public static final String GET_TOKEN_INFO_URL = "https://api.weibo.com/oauth2/get_token_info"; 
    }
}
