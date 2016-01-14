package com.cocoonshu.sina.weibo.network.auth;

import org.json.JSONException;
import org.json.JSONObject;

import com.cocoonshu.network.HttpAPI;
import com.cocoonshu.network.HttpMethod;
import com.cocoonshu.sina.weibo.Account;
import com.cocoonshu.sina.weibo.Weibo;
import com.cocoonshu.sina.weibo.network.WeiboAPI;
import com.cocoonshu.sina.weibo.util.Debugger;

/**
 * https://api.weibo.com/oauth2/revokeoauth2
 * ?access_token=fahkjefwa6987fg21109r2g1h0c12
 * @author Cocoonshu
 * @date   2016-01-14
 */
public class Unauthorize extends HttpAPI {

    private static final String TAG = "Unauthorize";
    
    public Unauthorize() {
        setMethod(HttpMethod.POST);
        setApiHost(WeiboAPI.API_URL);
        setApiInterface(WeiboAPI.API_REVOKE_OAUTH2);
        setParameterValue(WeiboAPI.PARAM_ACCESS_TOKEN, "");
    }

    public void setAccount(Account account) {
        setParameterValue(
                WeiboAPI.PARAM_ACCESS_TOKEN, Weibo.getInstance().getAccountManager().getAccessToken());
    }
    
    @Override
    public Object parseResponse(String responseContent, Object... inParams) {
        String result = "";
        
        Debugger.i(TAG, "[parseResponse] Response: " + responseContent);
        if (responseContent != null && !responseContent.isEmpty()) {
            try {
                JSONObject jsonRoot = new JSONObject(responseContent);
                result = jsonRoot.optString(WeiboAPI.JSON.KEY_RESULT);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        
        return result;
    }

}
