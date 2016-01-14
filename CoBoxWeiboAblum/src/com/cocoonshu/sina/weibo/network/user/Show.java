package com.cocoonshu.sina.weibo.network.user;

import com.cocoonshu.network.HttpAPI;
import com.cocoonshu.network.HttpMethod;
import com.cocoonshu.sina.weibo.Account;
import com.cocoonshu.sina.weibo.Weibo;
import com.cocoonshu.sina.weibo.network.WeiboAPI;

/**
 * Show user information
 * @author Cocoonshu
 * @date   2016-01-14
 */
public class Show extends HttpAPI {
    
    public Show() {
        setMethod(HttpMethod.GET);
        setApiHost(WeiboAPI.API_URL);
        setParameterValue(WeiboAPI.PARAM_ACCESS_TOKEN, "");
        setParameterValue(WeiboAPI.PARAM_UID,          "");
    }

    public void setAccount(Account account) {
        setParameterValue(
                WeiboAPI.PARAM_ACCESS_TOKEN, Weibo.getInstance().getAccountManager().getAccessToken(),
                WeiboAPI.PARAM_UID,          Weibo.getInstance().getAccountManager().getUID());
    }
    
    @Override
    public Object parseResponse(String responseContent, Object... inParams) {
        // TODO Auto-generated method stub
        return null;
    }

}
