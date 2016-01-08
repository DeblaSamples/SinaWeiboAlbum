package com.cocoonshu.network;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;

/**
 * Http API package
 * @author Cocoonshu
 * @date   2016-01-06
 */
public abstract class HttpAPI {

    private HttpMethod   mHttpMethod                = HttpMethod.GET;
    private String       mApiHost                   = null;
    private String       mApiInterface              = null;
    private List<String> mApiParamNames             = new LinkedList<String>();
    private boolean      mIsApiUrlInvalidate        = false;
    private boolean      mIsApiParametersInvalidate = false;
    private String       mApiUrlFormatedStr         = "";
    private String       mApiParametersFormatedStr  = "";

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
        mApiParamNames.add(apiParam);
        mIsApiParametersInvalidate = true;
    }

    protected final void setParameters(String...apiParams) {
        if (apiParams != null && apiParams.length > 0) {
            for (String paranm : apiParams) {
                mApiParamNames.add(paranm);
                mIsApiParametersInvalidate = true;
            }
        }
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
    protected final void updateApiParameterUrlFormated() {
        if (mIsApiParametersInvalidate) {
            mApiParametersFormatedStr = "";
            for (String param : mApiParamNames) {
                mApiParametersFormatedStr += "&" + param + "=%s";
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
     * Get formated api parameters string. This string
     * will be appended '=%s' for every each single parameter
     * and concat all parameters with '&' automatically.
     * @return
     */
    public String getFormatApiParameterUrl() {
        updateApiParameterUrlFormated();
        return mApiParametersFormatedStr;
    }

    /**
     * Get formated api parameters string. This string
     * will be appended value for every each single parameter
     * and concat all parameters with '&' automatically.
     * @param values
     * @return
     */
    public String getApiParameterUrl(String...values) {
        String resultUrl = String.format(getFormatApiParameterUrl(), values);
        try {
            return URLEncoder.encode(resultUrl, "utf8");
        } catch (UnsupportedEncodingException utf8Exp) {
            try {
                return URLEncoder.encode(resultUrl, Charset.defaultCharset().name());
            } catch (UnsupportedEncodingException defaultExp) {
                defaultExp.printStackTrace();
                return resultUrl;
            }
        }
    }

    /**
     * Parse responding data from the responding json string,
     * xml string, protocol string and so on.
     * @param responseContent
     * @return
     */
    public abstract Object parseResponse(String responseContent, Object...inParams);
}
