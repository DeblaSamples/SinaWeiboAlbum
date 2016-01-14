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
    
    public static final String API_SHOW                 = "users/show";
    public static final String API_DOMAIN_SHOW          = "users/domain_show";
    public static final String API_COUNTS               = "users/counts";
    
    public static final String PARAM_ACCESS_TOKEN       = "access_token";
    public static final String PARAM_CODE               = "code";
    public static final String PARAM_CLIENT_ID          = "client_id";
    public static final String PARAM_CLIENT_SECRET      = "client_secret";
    public static final String PARAM_DISPLAY            = "display";
    public static final String PARAM_FORCE_LOGIN        = "forcelogin";
    public static final String PARAM_GRANT_TYPE         = "grant_type";
    public static final String PARAM_REDIRECT_URI       = "redirect_uri";
    public static final String PARAM_RESPONSE_TYPE      = "response_type";
    public static final String PARAM_SCREEN_NAME        = "screen_name";
    public static final String PARAM_SOURCE             = "source";
    public static final String PARAM_UID                = "uid";
    
    public static final String VALUE_CODE               = "code";
    public static final String VALUE_MOBILE             = "mobile";
    public static final String VALUE_AUTHORIZATION_CODE = "authorization_code";
    
    public static class JSON {

        public static final String KEY_ACCESS_TOKEN            = "access_token";
        public static final String KEY_ALLOW_ALL_ACT_MSG       = "allow_all_act_msg";
        public static final String KEY_ALLOW_ALL_COMMENT       = "allow_all_comment";
        public static final String KEY_ANNOTATIONS             = "annotations";
        public static final String KEY_APPKEY                  = "appkey";
        public static final String KEY_AVATAR_LARGE            = "avatar_large";
        public static final String KEY_BI_FOLLOWERS_COUNT      = "bi_followers_count";
        public static final String KEY_CREATE_AT               = "create_at";
        public static final String KEY_CITY                    = "city";
        public static final String KEY_COMMENTS_COUNT          = "comments_count";
        public static final String KEY_CREATED_AT              = "created_at";
        public static final String KEY_DESCRIPTION             = "description";
        public static final String KEY_DOMAIN                  = "domain";
        public static final String KEY_EXPIRE_IN               = "expires_in";
        public static final String KEY_EXPIRES_IN              = "expires_in";
        public static final String KEY_FAVOURITED              = "favorited";
        public static final String KEY_FAVOURITES_COUNT        = "favourites_count";
        public static final String KEY_FOLLOW_ME               = "follow_me";
        public static final String KEY_FOLLOWERS_COUNT         = "followers_count";
        public static final String KEY_FOLLOWING               = "following";
        public static final String KEY_FRIENDS_COUNT           = "friends_count";
        public static final String KEY_GENDER                  = "gender";
        public static final String KEY_GEO                     = "geo";
        public static final String KEY_GEO_ENABLED             = "geo_enabled";
        public static final String KEY_ID                      = "id";
        public static final String KEY_IN_REPLY_TO_SCREEN_NAME = "in_reply_to_screen_name";
        public static final String KEY_IN_REPLY_TO_STATUS_ID   = "in_reply_to_status_id";
        public static final String KEY_IN_REPLY_TO_USER_ID     = "in_reply_to_user_id";
        public static final String KEY_LANG                    = "lang";
        public static final String KEY_LOCATION                = "location";
        public static final String KEY_MID                     = "mid";
        public static final String KEY_NAME                    = "name";
        public static final String KEY_ONLINE_STATUS           = "online_status";
        public static final String KEY_PROFILE_IMAGE_URL       = "profile_image_url";
        public static final String KEY_PROVINCE                = "province";
        public static final String KEY_REMIND_IN               = "remind_in";
        public static final String KEY_REPORTS_COUNT           = "reposts_count";
        public static final String KEY_RESULT                  = "result";
        public static final String KEY_SCREEN_NAME             = "screen_name";
        public static final String KEY_SOURCE                  = "source";
        public static final String KEY_STATUS                  = "status";
        public static final String KEY_STATUSES_COUNT          = "statuses_count";
        public static final String KEY_TEXT                    = "text";
        public static final String KEY_TRUNCATED               = "truncated";
        public static final String KEY_UID                     = "uid";
        public static final String KEY_URL                     = "url";
        public static final String KEY_VERIFIED                = "verified";
        public static final String KEY_VERIFIED_REASON         = "verified_reason";
    }
    
    private Account mAccount = null;
    
    public WeiboAPI(Account account) {
        mAccount = account;
    }
}


