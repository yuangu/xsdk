package com.seantone.xsdk.core.impl.ad;

public interface IInterstitialAdEventCallBack {
    void onLoad(String ret);
    void onError(String ret);
    void onClose();
}
