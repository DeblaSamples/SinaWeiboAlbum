package com.cocoonshu.network;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.cocoonshu.network.HttpResponseHeader.ContentType;
import com.cocoonshu.sina.weibo.util.Config;

/**
 * Http response
 * @author Cocoonshu
 * @date   2016-01-06
 */
public class HttpResponse {

    private static final String TAG = "HttpResponse";
    
    private HttpCode mHttpStatusCode = HttpCode.Undefined;
    
    private HttpAPI  mHttpAPI        = null;
    private Object   mHttpResponse   = null;
    
    protected HttpResponse(HttpAPI api, HttpCode statusCode) {
        mHttpAPI = api;
        mHttpStatusCode = statusCode;
    }
    
    protected void processRespondingError(InputStream sin) {
        onRespondErroring(sin);
    }
    
    protected void processResponding(Map<String, List<String>> headerFields, InputStream sin) {
        onResponding(headerFields, sin);
    }
    
    /**
     * If the HTTP response indicates that an error occurred,
     * use this {@code sin} to read the error response, else
     * {@link #onResponding(Map, InputStream)} will be invoked
     * instead of this method.
     * @param sin
     * @see   {@link #onResponding(Map, InputStream)}
     */
    public void onRespondErroring(InputStream sin) {
        // Let subclass implemention to deal with this
    }

    /**
     * This method will be invoked while the server is responding.
     * If the HTTP response indicates that an error occurred, 
     * {@link #onRespondErroring(InputStream)} will be invoked instead
     * of this method.
     * @param headerFields
     * @param sin
     * @see   {@link #onRespondErroring(InputStream)}
     */
    public void onResponding(Map<String, List<String>> headerFields, InputStream sin) {
        HttpResponseHeader headerMap   = new HttpResponseHeader(headerFields);
        String             contentType = headerMap.getString(HttpResponseHeader.ContentType);
        
        if (ContentType.isTextType(contentType)) {
            byte[]        buffer  = new byte[Config.Network.HttpResponseByteBufferSize];
            StringBuilder content = new StringBuilder();
            
            try {
                int readCount = 0;
                do {
                    readCount = sin.read(buffer);
                    if (readCount > 0) {
                        content.append(new String(buffer, 0, readCount));
                    }
                } while (readCount > -1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            mHttpResponse = content.toString();
        }
    }
    
    /**
     * Get responding data, if the content type is
     * text, this return type will be string.
     * @return
     */
    public Object getResponseData() {
        return mHttpResponse;
    }

    /**
     * Get responding status code that feedback
     * from server.
     * @return
     */
    public final HttpCode getHttpStatusCode() {
        return mHttpStatusCode;
    }
    
    /**
     * Get the http api what is the requesting operation
     * bound.
     * @return
     */
    public final HttpAPI getHttpAPI() {
        return mHttpAPI;
    }
}
