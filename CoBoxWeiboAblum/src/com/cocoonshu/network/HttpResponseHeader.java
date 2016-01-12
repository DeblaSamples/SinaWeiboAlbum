package com.cocoonshu.network;

import android.annotation.SuppressLint;
import java.util.List;
import java.util.Map;

/**
 * Generic response header of HTTP
 * @author Cocoonshu
 * @date   2016-01-12
 */
public class HttpResponseHeader {
    public static String HTTPVersion     = "HTTP-Version";
    public static String ReasonPhrase    = "Reason-Phrase";
    public static String StatusCode      = "Status-Code";
    public static String Date            = "Date";
    public static String Server          = "Server";
    public static String LastModified    = "Last-Modified";
    public static String Etag            = "Etag";
    public static String Allow           = "Allow";
    public static String ContentType     = "Content-Type";
    public static String ContentBase     = "Content-Base";
    public static String ContentLength   = "Content-Length";
    public static String ContentRange    = "Content-Range";
    public static String ContentEncoding = "Content-Encoding";
    public static String ContentLanguage = "Content-Language";
    public static String ContentLocation = "Content-Location";
    public static String ContentMD5      = "Content-MD5";
    
    private Map<String, List<String>> mHeaderMap = null;
    
    public HttpResponseHeader(Map<String, List<String>> headerMap) {
        mHeaderMap = headerMap;
    }
    
    public final long getContentLength() {
        String value  = getString(ContentLength);
        long   length = 0;
        
        try {
            length = Long.valueOf(value);
        } catch (NumberFormatException exp) {
            return 0;
        }
        
        return length;
    }
    
    public String getString(String key) {
        if (mHeaderMap != null) {
            List<String> values = mHeaderMap.get(key);
            if (values != null && !values.isEmpty()) {
                for (String value : values) {
                    if (!value.trim().isEmpty()) {
                        return value;
                    }
                }
                return values.get(0);
            } 
        }
        return null;
    }
    
    public String getString(String key, String defaultValue) {
        String value = getString(key);
        return value == null ? defaultValue : value;
    }
    
    public List<String> getStringList(String key) {
        return mHeaderMap != null ? mHeaderMap.get(key) : null;
    }
    
    public String[] getStringArray(String key) {
        List<String> values = mHeaderMap.get(key);
        return (String[]) (values != null ? values.toArray() : null);
    }
    
    /**
     * Content type of Http responding header
     * @author Cocoonshu
     * @date   2016-01-12
     */
    public static class ContentType {
        
        public static boolean isTextType(String contentType) {
            return isSpecifiedMainType(contentType, "text");
        }
        
        public static boolean isImageType(String contentType) {
            return isSpecifiedMainType(contentType, "image");
        }
        
        @SuppressLint("DefaultLocale")
        private static boolean isSpecifiedMainType(String contentType, String type) {
            return contentType != null && !contentType.isEmpty() ?
                    contentType.toLowerCase().startsWith(type) : false;
        }
        
        @SuppressLint("DefaultLocale")
        private static boolean isSpecifiedSubType(String contentType, String type) {
            return contentType != null && !contentType.isEmpty() ?
                    contentType.toLowerCase().endsWith(type) : false;
        }
    }
}
