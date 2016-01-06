package com.cocoonshu.network;

/**
 * Http request
 * @author Cocoonshu
 * @date   2016-01-06
 */
public abstract class HttpRequest {

    private HttpCode     mHttpStatusCode = HttpCode.Undefined;
    private HttpResponse mHttpResponse   = null;
    
    /**
     * Execute an http request.
     */
    public void execute() {
        if (onRequestPrepare()) {
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
