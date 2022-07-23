package com.seantone.xsdk.core.impl.ad;

public interface INativeAdEventCallBack {
    void onLoad(String data);
    void onClick();
    void onError(String params);
}
