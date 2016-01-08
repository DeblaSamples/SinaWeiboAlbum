package com.cocoonshu.sina.weibo.network;

import org.json.JSONException;
import org.json.JSONObject;

import com.cocoonshu.network.HttpAPI;
import com.cocoonshu.network.HttpMethod;
import com.cocoonshu.sina.weibo.Account;
import com.cocoonshu.sina.weibo.AccountManager;

/**
 * <p>
 * https://api.weibo.com/oauth2/access_token<br>
 *  ?client_id=988265004<br>
 *  &client_secret=8f3d5c6751c02c33e1d65f8d11bf726b<br>
 *  &grant_type=authorization_code<br>
 *  &code=c51d3ea3ef9a5dad714cb51bf62e71fd<br>
 *  &redirect_uri=https://api.weibo.com/oauth2/default.html<br>
 * </p>
 * @author Cocoonshu
 * @date   2016-01-07
 */
public class AccessToken extends HttpAPI {

    public AccessToken() {
        setApiHost(WeiboAPI.API_URL);
        setApiInterface(WeiboAPI.API_ACCESS_TOKEN);
        setParameter(WeiboAPI.PARAM_CLIENT_ID);
        setParameter(WeiboAPI.PARAM_CLIENT_SECRET);
        setParameter(WeiboAPI.PARAM_GRANT_TYPE);
        setParameter(WeiboAPI.PARAM_CODE);
        setParameter(WeiboAPI.PARAM_REDIRECT_URI);
        setMethod(HttpMethod.POST);
    }

    public String getApiParameterUrl(Account account) {
        return getApiParameterUrl(
                AccountManager.getInstance().getAppKey(),
                AccountManager.getInstance().getAppSecret(),
                WeiboAPI.VALUE_AUTHORIZATION_CODE,
                account.getAuthorizationCode(),
                WeiboAPI.AUTH_REDIRECT_URL);
    }

    @Override
    public Object parseResponse(String responseContent, Object...inParams) {
        String accessToken = null;
        String expiresIn   = null;
        String remindIn    = null;
        String uid         = null;
        if (responseContent != null && !responseContent.isEmpty()) {
            try {
                JSONObject jsonRoot = new JSONObject(responseContent);
                accessToken = jsonRoot.optString(WeiboAPI.JSON.KEY_ACCESS_TOKEN);
                expiresIn   = jsonRoot.optString(WeiboAPI.JSON.KEY_EXPIRES_IN);
                remindIn    = jsonRoot.optString(WeiboAPI.JSON.KEY_REMIND_IN);
                uid         = jsonRoot.optString(WeiboAPI.JSON.KEY_UID);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

}
