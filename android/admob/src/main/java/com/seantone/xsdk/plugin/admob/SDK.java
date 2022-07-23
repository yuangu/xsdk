package com.seantone.xsdk.plugin.admob;

import com.google.android.gms.ads.MobileAds;
import com.seantone.xsdk.admob.BannerAd;
import com.seantone.xsdk.admob.InterstitialAd;
import com.seantone.xsdk.admob.NativeAd;
import com.seantone.xsdk.admob.RewardedVideoAd;
import com.seantone.xsdk.core.XSDK;
import com.seantone.xsdk.core.annotation.ForAD;
import com.seantone.xsdk.core.define.AdParams;
import com.seantone.xsdk.core.define.SDKParams;
import com.seantone.xsdk.core.impl.IAD;
import com.seantone.xsdk.core.impl.ISDK;
import com.seantone.xsdk.core.impl.IXSDKCallback;
import com.seantone.xsdk.core.impl.ad.IBannerAd;
import com.seantone.xsdk.core.impl.ad.IBannerAdEventCallBack;
import com.seantone.xsdk.core.impl.ad.IInterstitialAd;
import com.seantone.xsdk.core.impl.ad.IInterstitialAdEventCallBack;
import com.seantone.xsdk.core.impl.ad.INativeAd;
import com.seantone.xsdk.core.impl.ad.INativeAdEventCallBack;
import com.seantone.xsdk.core.impl.ad.IRewardedVideoAd;
import com.seantone.xsdk.core.impl.ad.IRewardedVideoAdEventCallBack;

@ForAD(provider = "admob")
public class SDK implements ISDK, IAD {
    static public String provider = "admob";

    @Override
    public IRewardedVideoAd createRewardedVideoAd(AdParams params, IRewardedVideoAdEventCallBack callBack) {
        return new RewardedVideoAd(params, callBack);
    }

    @Override
    public IBannerAd createBannerAd(AdParams params, IBannerAdEventCallBack callBack) {
        return new BannerAd(params, callBack);
    }

    @Override
    public INativeAd createNativeAd(AdParams params, INativeAdEventCallBack callBack) {
        return new NativeAd(params.adUnitId, callBack);
    }

    @Override
    public IInterstitialAd createInterstitialAd(AdParams params, IInterstitialAdEventCallBack callBack) {
        return new InterstitialAd(params, callBack);
    }

    @Override
    public void initSDK(SDKParams params, IXSDKCallback callback) {
        MobileAds.initialize(XSDK.getInstance().getTopActivity(), initializationStatus -> {
            callback.onSuccess("{}");
        });
    }
}
