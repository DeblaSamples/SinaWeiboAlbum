package com.cocoonshu.network;

import java.util.HashMap;
import java.util.Map;

/**
 * Http API package
 * @author Cocoonshu
 * @date   2016-01-06
 */
public abstract class HttpAPI {

    private HttpMethod          mHttpMethod                = HttpMethod.GET;
    private String              mApiHost                   = null;
    private String              mApiInterface              = null;
    private Map<String, String> mApiParamMap               = new HashMap<String, String>();
    private boolean             mIsApiUrlInvalidate        = false;
    private boolean             mIsApiParametersInvalidate = false;
    private String              mApiUrlFormatedStr         = "";
    private String              mApiParametersFormatedStr  = "";

    public final HttpMethod getMehthod() {
        return mHttpMethod;
    }

    protected final void setMethod(HttpMethod httpMethod) {
        mHttpMethod = httpMethod;
    }

    protected final void setApiHost(String apiHost) {
        mApiHost = apiHost;
        mIsApiUrlInvalidate = true;
    }

    protected final void setApiInterface(String apiInterface) {
        mApiInterface = apiInterface;
        mIsApiUrlInvalidate = true;
    }

    protected final void setParameter(String apiParam) {
        mApiParamMap.put(apiParam, null);
        mIsApiParametersInvalidate = true;
    }

    protected final void setParameters(String...apiParams) {
        if (apiParams != null && apiParams.length > 0) {
            for (String paranm : apiParams) {
                mApiParamMap.put(paranm, "");
                mIsApiParametersInvalidate = true;
            }
        }
    }

    public String getParameter(String key) {
        return mApiParamMap.get(key);
    }
    
    public void setParameterValue(String key, String value) {
        mApiParamMap.put(key, value);
        mIsApiParametersInvalidate = true;
    }

    public void setParameterValue(String...keyValuePairs) {
        if (keyValuePairs.length % 2 != 0) {
            throw new IllegalArgumentException("Key-Value pairs must contain even number strings.");
        }
        for (int i = 0; i < keyValuePairs.length; i += 2) {
            mApiParamMap.put(keyValuePairs[i], keyValuePairs[i + 1]);
        }
        mIsApiParametersInvalidate = true;
    }

    /**
     * Recreate api url formated string if api
     * host and interface were changed.
     */
    protected final void updateApiUrl() {
        if (mIsApiUrlInvalidate) {
            mApiUrlFormatedStr = mApiHost + '/' + mApiInterface + '?';
            mIsApiUrlInvalidate = false;
        }
    }

    /**
     * Recreate api parameters formated string
     * if api parameters was changed.
     */
    protected final void updateApiParameters() {
        if (mIsApiParametersInvalidate) {
            mApiParametersFormatedStr = "";
            for (String key : mApiParamMap.keySet()) {
                mApiParametersFormatedStr += "&" + key + "=" + mApiParamMap.get(key);
            }
            if (!mApiParametersFormatedStr.isEmpty()) {
                mApiParametersFormatedStr = mApiParametersFormatedStr.substring(1);
            }
            mIsApiParametersInvalidate = false;
        }
    }

    /**
     * Get formated api url string. This string
     * will be appended '?' at the end of string
     * automatically.
     * @return
     */
    public String getApiUrl() {
        updateApiUrl();
        return mApiUrlFormatedStr;
    }

    /**
     * Get formated api url parameters string. This
     * string will be concat will '&' between each
     * key value pairs of parameters.
     * @return
     */
    public String getApiParameters() {
        updateApiParameters();
        return mApiParametersFormatedStr;
    }

    /**
     * Parse responding data from the responding json string,
     * xml string, protocol string and so on.
     * @param responseContent
     * @return
     */
    public abstract Object parseResponse(String responseContent, Object...inParams);

    /**
     * Get methoded url for requesting. This string
     * will be formated depends on the specified http
     * method.
     * @return GET will return url + parameter list <br>
     *         POST will return url
     */
    public final String getMethodedUrl() {
        switch (mHttpMethod) {
        case GET:
            updateApiUrl();
            updateApiParameters();
            return getApiUrl() + getApiParameters();
        case POST:
            updateApiUrl();
            return getApiUrl();
        default:
            updateApiUrl();
            updateApiParameters();
            return getApiUrl() + getApiParameters();
        }
    }

    /**
     * Get methoded parameters for requesting.
     * This string will be formated depends on
     * the specified http method.
     * @return GET will return ""
     *         POST will return paramter list
     */
    public final String getMethodedParameters() {
        switch (mHttpMethod) {
        case GET:
            return "";
        case POST:
            updateApiParameters();
            return getApiParameters();
        default:
            updateApiUrl();
            updateApiParameters();
            return getApiUrl() + getApiParameters();
        }
    }
}
