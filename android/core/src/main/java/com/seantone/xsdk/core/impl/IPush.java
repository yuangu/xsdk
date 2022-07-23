package com.seantone.xsdk.core.impl;

public interface IPush {
    // 用户是否支持该推送sdk
    boolean isSupport();
    // 是否是最好的,有的推送sdk可用，但是，切后台后不能使用了
    boolean isBest();
}
