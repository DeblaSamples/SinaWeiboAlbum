package com.cocoonshu.sina.weibo.network;

import com.cocoonshu.network.HttpAPI;
import com.cocoonshu.network.HttpRequest;
import com.cocoonshu.network.HttpResponse;

/**
 * Default request with the specified http api
 * @author Cocoonshu
 * @date   2016-01-11
 */
public class DefaultJSONRequest extends HttpRequest {

    private OnResponseListener mOnResponseListener = null;
    
    public static interface OnResponseListener {
        void onJsonResponse(String json, Object parsedObject);
    }
    
    public final void setOnResponseListener(OnResponseListener listener) {
        mOnResponseListener = listener;
    }
    
    @Override
    protected boolean onRequestPrepare() {
        return getHttpApi() != null;
    }

    @Override
    protected void onPostRequest(HttpResponse response) {
        String  json = (String)response.getResponseData();
        HttpAPI api  = getHttpApi();
        if (api != null) {
            Object result = api.parseResponse(json, null);
            if (mOnResponseListener != null) {
                mOnResponseListener.onJsonResponse(json, result);
            }
        }
    }

}
