package com.seantone.xsdk.core.impl;

/**
 * 推送的通用回调
 */
public interface IPushCallback {
    // 注册成功
    void onRegisterSucceed(String ret);
    void onNotificationMessageClicked(String ret);
    void onNotificationMessageArrived(String ret);
}
