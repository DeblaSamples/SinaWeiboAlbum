package com.cocoonshu.network;

public interface HttpListener {
    void onResponed(HttpResponse response);
    void onError(HttpCode code);
}
