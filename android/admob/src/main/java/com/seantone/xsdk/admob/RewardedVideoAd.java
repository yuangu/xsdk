package com.seantone.xsdk.admob;

import android.app.Activity;
import android.util.Log;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.seantone.xsdk.core.XSDK;
import com.seantone.xsdk.core.define.AdParams;
import com.seantone.xsdk.core.impl.ad.INativeAdEventCallBack;
import com.seantone.xsdk.core.impl.ad.IRewardedVideoAd;
import com.seantone.xsdk.core.impl.ad.IRewardedVideoAdEventCallBack;

import org.json.JSONObject;

import androidx.annotation.NonNull;


public class RewardedVideoAd implements IRewardedVideoAd {
    private RewardedAd rewardedAd;
    private  IRewardedVideoAdEventCallBack mCallBack;

    public RewardedVideoAd(AdParams params, IRewardedVideoAdEventCallBack callBack){
        XSDK.getInstance().doInUIThread(() -> {
            this.load(params,  callBack);
        });
    }

    public void load(AdParams params, IRewardedVideoAdEventCallBack callBack) {
        mCallBack = callBack;
            AdRequest adRequest = new AdRequest.Builder().build();
            RewardedVideoAd that = this;
            RewardedAdLoadCallback adLoadCallback = new RewardedAdLoadCallback() {
                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    that.rewardedAd = null;
                    if (callBack != null) {
                        JSONObject ret = new JSONObject();
                        try {
                            ret.put("errMsg", loadAdError.getMessage());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        callBack.onError(ret.toString());
                    }
                }

                @Override
                public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                    that.rewardedAd = rewardedAd;

                    rewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdShowedFullScreenContent() {

                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(AdError adError) {

                        }

                        @Override
                        public void onAdDismissedFullScreenContent() {
                            if(callBack != null) callBack.onClose();
                            that.rewardedAd = null;
                        }
                    });

                    if (callBack != null) {
                        callBack.onLoad("{}");
                    }
                }
            };

            RewardedAd.load(XSDK.getInstance().getTopActivity(), params.adUnitId, adRequest, adLoadCallback);
    }

    @Override
    public void show() {
        if (rewardedAd == null) {
            XSDK.getInstance().getLogger().log( "admob 没有支持");
            return;
        }
        XSDK.getInstance().doInUIThread(() -> {
            rewardedAd.show(XSDK.getInstance().getTopActivity(), rewardItem -> {
                //给奖励
                if(mCallBack != null)
                {
                    mCallBack.onReward();
                }
            });
        });
    }

    @Override
    public void destory() {
        rewardedAd = null;
    }
}
