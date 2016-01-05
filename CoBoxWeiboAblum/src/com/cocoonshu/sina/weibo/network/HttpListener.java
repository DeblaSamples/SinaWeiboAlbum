package com.cocoonshu.sina.weibo.network;

public interface HttpListener {
    void onResponed(HttpResponse response);
    void onError(int errorCode);
}
