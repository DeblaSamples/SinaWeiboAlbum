package com.cocoonshu.network;

/**
 * Http methods
 * @author Cocoonshu
 * @date   2016-01-06
 */
public enum HttpMethod {
    GET    ("GET"),
    POST   ("POST"),
    HEAD   ("HEAD"),
    PUT    ("PUT"),
    DELETE ("DELETE"),
    OPTIONS("OPTIONS"), 
    TRACE  ("TRACE");

    private String mMethod = null;

    private HttpMethod(String method) {
        mMethod = method;
    }

    public String getMethodString() {
        return mMethod;
    }
    
}