package com.seantone.xsdk.plugin.gdt;

import android.app.Activity;

import com.qq.e.comm.managers.GDTAdSdk;
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
import com.seantone.xsdk.gdt.BannerAd;
import com.seantone.xsdk.gdt.RewardVideo;

/**
 * 腾讯优量汇
 * https://developers.adnet.qq.com/doc/android/access_doc
 */

@ForAD(provider = "gdt")
public class SDK implements ISDK, IAD {
    static public String provider = "gdt";

    @Override
    public IRewardedVideoAd createRewardedVideoAd(AdParams params, IRewardedVideoAdEventCallBack callBack) {
        return new RewardVideo(params, callBack);
    }

    @Override
    public IBannerAd createBannerAd(AdParams params, IBannerAdEventCallBack callBack) {
        return new BannerAd(params, callBack);
    }

    @Override
    public INativeAd createNativeAd(AdParams params, INativeAdEventCallBack callBack) {
        return null;
    }

    @Override
    public IInterstitialAd createInterstitialAd(AdParams params, IInterstitialAdEventCallBack callBack) {
        return null;
    }

    @Override
    public void initSDK(SDKParams params, IXSDKCallback callback) {
        try {
            Activity activity = XSDK.getInstance().getTopActivity();
            GDTAdSdk.init(activity.getApplicationContext(), params.appid);
        } catch (Exception e) {
            callback.onFaild("{}");
            return;
        }
        callback.onSuccess("{}");

    }
}
