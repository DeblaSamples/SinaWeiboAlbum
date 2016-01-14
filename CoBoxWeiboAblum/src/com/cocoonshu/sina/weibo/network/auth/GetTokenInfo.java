package com.cocoonshu.sina.weibo.network.auth;

import org.json.JSONException;
import org.json.JSONObject;

import com.cocoonshu.network.HttpAPI;
import com.cocoonshu.network.HttpMethod;
import com.cocoonshu.sina.weibo.Account;
import com.cocoonshu.sina.weibo.Weibo;
import com.cocoonshu.sina.weibo.network.WeiboAPI;
import com.cocoonshu.sina.weibo.network.WeiboAPI.JSON;
import com.cocoonshu.sina.weibo.util.Debugger;

/**
 * https://api.weibo.com/oauth2/get_token_info
 * ?access_token=faf678ayhu3ia6738fg728qr67
 * @author Cocoonshu
 * @date   2016-01-14
 */
public class GetTokenInfo extends HttpAPI {

    private static final String TAG = "GetTokenInfo";
    
    public GetTokenInfo() {
        setMethod(HttpMethod.POST);
        setApiHost(WeiboAPI.API_URL);
        setApiInterface(WeiboAPI.API_ACCESS_TOKEN);
        setParameterValue(WeiboAPI.PARAM_ACCESS_TOKEN, "");
    }

    public void setAccount(Account account) {
        setParameterValue(
                WeiboAPI.PARAM_ACCESS_TOKEN, Weibo.getInstance().getAccountManager().getAccessToken());
    }
    
    @Override
    public Object parseResponse(String responseContent, Object... inParams) {
        String  uid      = "";
        String  appkey   = "";
        long    expireIn = 0;
        Account account  = new Account();
        
        Debugger.i(TAG, "[parseResponse] Response: " + responseContent);
        if (responseContent != null && !responseContent.isEmpty()) {
            try {
                JSONObject jsonRoot = new JSONObject(responseContent);
                uid      = jsonRoot.optString(WeiboAPI.JSON.KEY_UID);
                appkey   = jsonRoot.optString(WeiboAPI.JSON.KEY_APPKEY);
                expireIn = jsonRoot.optLong(WeiboAPI.JSON.KEY_EXPIRE_IN);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            
            account.setUid(uid);
            account.setAppKey(appkey);
            account.setExpiresIn(expireIn);
        }
        
        return account;
    }
}
