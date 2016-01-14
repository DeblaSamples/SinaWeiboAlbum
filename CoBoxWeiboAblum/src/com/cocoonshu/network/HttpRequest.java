package com.cocoonshu.network;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.cocoonshu.sina.weibo.util.Config;
import com.cocoonshu.sina.weibo.util.Debugger;


/**
 * Http request
 * @author Cocoonshu
 * @date   2016-01-06
 */
public abstract class HttpRequest {

    private static final String TAG = "HttpRequest";
    
    private HttpCode            mHttpStatusCode = HttpCode.Undefined;
    private HttpAPI             mHttpAPI        = null;
    private HttpResponse        mHttpResponse   = null;
    private HttpURLConnection   mConnection     = null;
    private volatile boolean    mIsCanceled     = false;
    
    public HttpRequest() {
        
    }
    
    public final void setHttpApi(HttpAPI api) {
        mHttpAPI = api;
    }
    
    protected final HttpAPI getHttpApi() {
        return mHttpAPI;
    }
    
    /**
     * Execute an http request.
     */
    public void execute() {
        boolean isPrepared = false;
        
        try {
            isPrepared = onRequestPrepare();
        } finally {
            // Do nothing
        }
        
        if (isPrepared) {
            try {
                URL url = new URL(mHttpAPI.getMethodedUrl());
                mConnection = (HttpURLConnection) url.openConnection();
                mConnection.setConnectTimeout(Config.Network.HttpConnectTimeout);
                mConnection.setReadTimeout(Config.Network.HttpReadTimeout);
                mConnection.setInstanceFollowRedirects(true);
                mConnection.setRequestMethod(mHttpAPI.getMehthod().getMethodString());
                
                HttpMethod method = mHttpAPI.getMehthod();
                switch (method) {
                case GET: {
                    mConnection.setDoInput(true);
                    mConnection.setDoOutput(false);
                    mConnection.connect();
                }
                    break;

                case POST: {
                    mConnection.setDoInput(true);
                    mConnection.setDoOutput(true);
                    mConnection.connect();

                    // Write out request data
                    OutputStream sout = mConnection.getOutputStream();
                    try {
                        if (sout != null) {
                            onRequest(sout);
                        }
                    } finally {
                        if (sout != null) {
                            sout.flush();
                            sout.close();
                        }
                    }
                }
                    break;
                    
                case OPTIONS: {
                    mConnection.setDoInput(true);
                    mConnection.setDoOutput(false);
                    mConnection.connect();
                    // FIXME Finish this implements
                }
                    break;
                    
                case DELETE: {
                    mConnection.setDoInput(true);
                    mConnection.setDoOutput(false);
                    mConnection.connect();
                    // FIXME Finish this implements
                }
                    break;
                    
                case HEAD: {
                    mConnection.setDoInput(true);
                    mConnection.setDoOutput(false);
                    mConnection.connect();
                    // FIXME Finish this implements
                }
                    break;
                    
                case PUT: {
                    mConnection.setDoInput(true);
                    mConnection.setDoOutput(true);
                    mConnection.connect();
                    // FIXME Finish this implements
                }
                    break;
                }
                
                // Read response from server
                InputStream sin = null;
                try {
                    sin             = new BufferedInputStream(mConnection.getInputStream());
                    mHttpStatusCode = HttpCode.valueOf(mConnection.getResponseCode());
                    mHttpResponse   = new HttpResponse(mHttpAPI, mHttpStatusCode);
                    try {
                        mHttpResponse.setParentRequest(this);
                        mHttpResponse.processResponding(mConnection.getHeaderFields(), sin);
                    } finally {
                        if (sin != null) {
                            sin.close();
                        }
                    }
                } catch (IOException exp) {
                    sin             = new BufferedInputStream(mConnection.getErrorStream());
                    mHttpStatusCode = HttpCode.valueOf(mConnection.getResponseCode());
                    mHttpResponse   = new HttpResponse(mHttpAPI, mHttpStatusCode);
                    try {
                        mHttpResponse.setParentRequest(this);
                        mHttpResponse.processRespondingError(sin);
                    } finally {
                        if (sin != null) {
                            sin.close();
                        }
                    }
                }
                
            } catch (MalformedURLException exp) {
                Debugger.printTrace(TAG, "[execute] Url is invalidable.", exp);
            } catch (IOException exp) {
                Debugger.printTrace(TAG, "[execute] Connection open failed.", exp);
            } finally {
                // Disconnect
                if (mConnection != null) {
                    mConnection.disconnect();
                }
            }

        }
        
        try {
            onPostRequest(mHttpResponse);
        } finally {
            // Do nothing
        }
    }

    /**
     * Prepare for http requesting.
     * @return If prepare completed and has enough conditions
     *         to request, return true
     */
    abstract protected boolean onRequestPrepare();
    
    /**
     * On the processing of requesting
     * @param responseHeaders 
     * @param buffer 
     * @return Return the result data of requesting operation.
     */
    protected void onRequest(OutputStream sout) throws IOException {
        if (mHttpAPI != null) {
            sout.write(mHttpAPI.getMethodedParameters().getBytes());
        }
    }
    
    /**
     * Request completed. You can notify UI to responded
     * by this request result here.
     * @param response 
     */
    abstract protected void onPostRequest(HttpResponse response);
    
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
