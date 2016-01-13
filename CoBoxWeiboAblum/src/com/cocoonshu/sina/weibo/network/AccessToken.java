package com.cocoonshu.sina.weibo.network;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.json.JSONException;
import org.json.JSONObject;

import com.cocoonshu.network.HttpAPI;
import com.cocoonshu.network.HttpMethod;
import com.cocoonshu.sina.weibo.Account;
import com.cocoonshu.sina.weibo.Weibo;
import com.cocoonshu.sina.weibo.util.Debugger;

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

    private static final String TAG = "AccessToken";

    public AccessToken() {
        setMethod(HttpMethod.POST);
        setApiHost(WeiboAPI.API_URL);
        setApiInterface(WeiboAPI.API_ACCESS_TOKEN);
        setParameterValue(WeiboAPI.PARAM_CLIENT_ID,     "");
        setParameterValue(WeiboAPI.PARAM_CLIENT_SECRET, "");
        setParameterValue(WeiboAPI.PARAM_GRANT_TYPE,    WeiboAPI.VALUE_AUTHORIZATION_CODE);
        setParameterValue(WeiboAPI.PARAM_CODE,          "");
        setParameterValue(WeiboAPI.PARAM_REDIRECT_URI,  WeiboAPI.AUTH_REDIRECT_URL);
    }

    public void setAccount(Account account) {
        setParameterValue(
                WeiboAPI.PARAM_CLIENT_ID,     Weibo.getInstance().getAccountManager().getAppKey(),
                WeiboAPI.PARAM_CLIENT_SECRET, Weibo.getInstance().getAccountManager().getAppSecret(),
                WeiboAPI.PARAM_CODE,          account.getAuthorizationCode());
    }

    @Override
    public Object parseResponse(String responseContent, Object...inParams) {
        String  accessToken = null;
        long    expiresIn   = 0;
        String  remindIn    = null;
        String  uid         = null;
        Account account     = new Account();
        
        Debugger.i(TAG, "[parseResponse] Response: " + responseContent);
        if (responseContent != null && !responseContent.isEmpty()) {
            try {
                JSONObject jsonRoot = new JSONObject(responseContent);
                accessToken = jsonRoot.optString(WeiboAPI.JSON.KEY_ACCESS_TOKEN);
                expiresIn   = jsonRoot.optLong(WeiboAPI.JSON.KEY_EXPIRES_IN);
                remindIn    = jsonRoot.optString(WeiboAPI.JSON.KEY_REMIND_IN);
                uid         = jsonRoot.optString(WeiboAPI.JSON.KEY_UID);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            
            account.setAccessToken(accessToken);
            account.setExpiresIn(expiresIn);
            account.setRemindIn(Long.valueOf(remindIn));
            account.setUid(uid);
        }
        
        return account;
    }

    @Override
    protected void onSecuritySetup(HttpsURLConnection connection) {
        super.onSecuritySetup(connection);
        
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{new WeiboTrustManager()}, new SecureRandom());  
            connection.setHostnameVerifier(new WeiboHostnameVerifier());
            connection.setSSLSocketFactory(sslContext.getSocketFactory());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }  
    }
    
    private class WeiboHostnameVerifier implements HostnameVerifier{  

        @Override  
        public boolean verify(String hostname, SSLSession session) {  
            return true;  
        }
        
    }  

    private class WeiboTrustManager implements X509TrustManager{

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
            // TODO Auto-generated method stub
            
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            // TODO Auto-generated method stub
            return null;
        }  

    }    
}
