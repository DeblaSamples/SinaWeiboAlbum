package com.cocoonshu.sina.weibo.util;

/**
 * Debug manager
 * @author Cocoonshu
 * @date   2016-01-11
 */
public class Config {

    public static class Debug {
        public static boolean ENABLE             = true;
        public static boolean ENABLE_INFO        = true;
        public static boolean ENABLE_VERBOSE     = true;
        public static boolean ENABLE_WARNING     = true;
        public static boolean ENABLE_ERROR       = true;
        public static boolean ENABLE_DEBUGGING   = true;
        public static boolean ENABLE_ASSERT      = true;
        public static boolean ENABLE_PRINT_TRACE = true;
    }
    
    public static class Intent {
        public static String START_AUTHORIZE_PAGE_ACTION = "com.cocoonshu.sina.weibo.start_authorize_page"; 
    }
    
    public static class Network {
        public static final int HttpRequestThreadCount = Runtime.getRuntime().availableProcessors();
        public static final int HttpConnectTimeout     = 10 * 1000;
        public static final int HttpReadTimeout        = 30 * 1000;
    }
    
    public static class LaunchPage {
        public static long DISPLAY_STILL_DURATION = 3000;
    }
    
    public static class AuthorizePage {
        
    }
}
