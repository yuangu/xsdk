package com.seantone.xsdk.core.impl.ad;

public interface INativeAd {
    void reportAdShow();
    void reportAdClick(String adName);
    void destory();
}
