package com.cocoonshu.network;

/**
 * Http listener used for listening a submitted
 * request to An http requestor
 * @author Cocoonshu
 * @date   2016-01-13
 */
public interface HttpListener {
    void onResponed(HttpResponse response);
    void onError(HttpResponse response, HttpCode code);
}
