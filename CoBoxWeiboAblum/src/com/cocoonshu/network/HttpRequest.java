package com.cocoonshu.network;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.cocoonshu.sina.weibo.util.Config;
import com.cocoonshu.sina.weibo.util.Debugger;


/**
 * Http request
 * @author Cocoonshu
 * @date   2016-01-06
 */
@SuppressWarnings("deprecation")
public abstract class HttpRequest {

    private static final String TAG = "HttpRequest";
    
    private HttpCode      mHttpStatusCode = HttpCode.Undefined;
    private HttpAPI       mHttpAPI        = null;
    private HttpResponse  mHttpResponse   = null;
    private HttpURLConnection mConnection = null;
    
    public HttpRequest() {
        
    }
    
    public final void setHttpApi(HttpAPI api) {
        mHttpAPI = api;
    }
    
    /**
     * Execute an http request.
     */
    public void execute() {
        if (onRequestPrepare()) {
            try {
                URL url = new URL(mHttpAPI.getMethodedUrl());
                mConnection = (HttpURLConnection) url.openConnection();
                mConnection.setConnectTimeout(Config.Network.HttpConnectTimeout);
                mConnection.setReadTimeout(Config.Network.HttpReadTimeout);
                mConnection.setInstanceFollowRedirects(true);
                mConnection.setRequestMethod(mHttpAPI.getMehthod().getMethodString());
                
                HttpMethod method = mHttpAPI.getMehthod();
                switch (method) {
                case GET:
                    mConnection.setDoInput(true);
                    mConnection.setDoOutput(false);
                    break;

                case POST:
                    mConnection.setDoInput(true);
                    mConnection.setDoOutput(true);
                    break;
                    
                case OPTIONS:
                    // FIXME
                    break;
                    
                case DELETE:
                    // FIXME
                    break;
                    
                case HEAD:
                    // FIXME
                    break;
                    
                case PUT:
                    // FIXME
                    break;
                    
                case TRACE:
                    // FIXME
                    break;
                  
                }
            } catch (MalformedURLException exp) {
                Debugger.printTrace(TAG, "[execute] Url is invalidable.", exp);
            } catch (IOException exp) {
                Debugger.printTrace(TAG, "[execute] Connection open failed.", exp);
            }
            mHttpResponse = new HttpResponse();
            mHttpResponse.setResponseData(onRequest());
        }
        onPostRequest();
    }

    /**
     * Prepare for http requesting.
     * @return If prepare completed and has enough conditions
     *         to request, return true
     */
    abstract protected boolean onRequestPrepare();
    
    /**
     * On the processing of requesting
     * @return Return the result data of requesting operation.
     */
    abstract protected Object onRequest();
    
    /**
     * Request completed. You can notify UI to responded
     * by this request result here.
     */
    abstract protected void onPostRequest();
    
    /**
     * Does request go wrong ?
     * @return false if request successed or not execute yet
     */
    public boolean isError() {
        int statusCode = mHttpStatusCode.getStatusCode();
        return statusCode >= 400 || statusCode < 0;
    }

    /**
     * Get an http response.
     * @return An http response, or null if not request yet
     */
    public HttpResponse getResponse() {
        return mHttpResponse;
    }

    /**
     * Get current http status code.
     * @return
     */
    public HttpCode getStatusCode() {
        return mHttpStatusCode;
    }

}
