package com.cocoonshu.sina.weibo.network;

import com.cocoonshu.network.HttpAPI;
import com.cocoonshu.network.HttpMethod;
import com.cocoonshu.sina.weibo.Account;
import com.cocoonshu.sina.weibo.AccountManager;

/**
 * <p>
 * https://api.weibo.com/oauth2/authorize<br>
 *  ?client_id=988265004<br>
 *  &display=mobile<br>
 *  &redirect_uri=https://api.weibo.com/oauth2/default.html<br>
 *  &response_type=code<br>
 * </p>
 * @author Cocoonshu
 * @date   2016-01-07
 */
public class Authorize extends HttpAPI {

    public Authorize() {
        setApiHost(WeiboAPI.AUTH_URL);
        setApiInterface(WeiboAPI.API_AUTHORIZE);
        setParameter(WeiboAPI.PARAM_CLIENT_ID);
        setParameter(WeiboAPI.PARAM_DISPLAY);
        setParameter(WeiboAPI.PARAM_REDIRECT_URI);
        setParameter(WeiboAPI.PARAM_RESPONSE_TYPE);
        setMethod(HttpMethod.POST);
    }

    public String getApiParameterUrl(Account account) {
        return getApiParameterUrl(
                AccountManager.getInstance().getAppKey(),
                WeiboAPI.VALUE_MOBILE,
                WeiboAPI.AUTH_REDIRECT_URL,
                WeiboAPI.VALUE_CODE);
    }

    @Override
    public Object parseResponse(String responseContent, Object...inParams) {
        String authorizeCode = null;
        if (responseContent != null && !responseContent.isEmpty()) {
            int contentLenght  = responseContent.length();
            int paramIndex     = responseContent.indexOf(WeiboAPI.PARAM_CODE);
            int paramTailIndex = responseContent.indexOf("&", paramIndex);
            paramTailIndex = paramTailIndex == -1 ? contentLenght : paramTailIndex;
            paramTailIndex = paramIndex > paramTailIndex ? contentLenght : paramTailIndex;
            if (paramIndex >= 0) {
                // Because '=' need one byte room
                if (contentLenght - paramIndex - 1 > 0) {
                    authorizeCode = responseContent.substring(paramIndex, paramTailIndex);
                }
            }
        }
        return authorizeCode;
    }

}