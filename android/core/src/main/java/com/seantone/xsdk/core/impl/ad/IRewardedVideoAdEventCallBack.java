package com.seantone.xsdk.core.impl.ad;

public interface IRewardedVideoAdEventCallBack {
    void onError(String ret);
    void onLoad(String ret);
    // 给奖励
    void onReward();
    // 没有奖励
    void onClose();
}