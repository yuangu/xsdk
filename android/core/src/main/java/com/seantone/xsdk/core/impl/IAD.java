package com.seantone.xsdk.core.impl;

import com.seantone.xsdk.core.define.AdParams;
import com.seantone.xsdk.core.impl.ad.IBannerAd;
import com.seantone.xsdk.core.impl.ad.IBannerAdEventCallBack;
import com.seantone.xsdk.core.impl.ad.IInterstitialAd;
import com.seantone.xsdk.core.impl.ad.IInterstitialAdEventCallBack;
import com.seantone.xsdk.core.impl.ad.INativeAd;
import com.seantone.xsdk.core.impl.ad.INativeAdEventCallBack;
import com.seantone.xsdk.core.impl.ad.IRewardedVideoAd;
import com.seantone.xsdk.core.impl.ad.IRewardedVideoAdEventCallBack;

public interface IAD {
    // 创建激励广告
    IRewardedVideoAd createRewardedVideoAd(AdParams params, IRewardedVideoAdEventCallBack callBack);

    // 创建banner广告
    IBannerAd createBannerAd(AdParams params, IBannerAdEventCallBack  callBack);

    // 创建原生广告
    INativeAd createNativeAd(AdParams params, INativeAdEventCallBack callBack);

    // 创建插页式广告
    IInterstitialAd createInterstitialAd(AdParams params, IInterstitialAdEventCallBack callBack);
}
