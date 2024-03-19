package com.seantone.xsdk.gdt;

import com.qq.e.ads.rewardvideo.RewardVideoAD;
import com.qq.e.ads.rewardvideo.RewardVideoADListener;
import com.qq.e.comm.util.AdError;
import com.seantone.xsdk.core.XSDK;
import com.seantone.xsdk.core.define.AdParams;
import com.seantone.xsdk.core.impl.ad.IBannerAd;
import com.seantone.xsdk.core.impl.ad.IBannerAdEventCallBack;
import com.seantone.xsdk.core.impl.ad.IRewardedVideoAd;
import com.seantone.xsdk.core.impl.ad.IRewardedVideoAdEventCallBack;

import org.json.JSONObject;

import java.util.Map;

public class RewardVideo implements IRewardedVideoAd {
    RewardVideoAD rewardVideoAD = null;

    public RewardVideo(AdParams params, IRewardedVideoAdEventCallBack callBack) {
        XSDK.getInstance().doInUIThread(() -> create(params, callBack));
    }

    void create(AdParams params, IRewardedVideoAdEventCallBack callBack) {
        rewardVideoAD = new RewardVideoAD(XSDK.getInstance().getTopActivity().getApplicationContext(), params.adUnitId, new RewardVideoADListener() {
            @Override
            public void onADLoad() {
                if (callBack != null) {
                    callBack.onLoad("");
                }
            }

            @Override
            public void onVideoCached() {
                XSDK.getInstance().getLogger().log("gdt video: onVideoCached");
            }

            @Override
            public void onADShow() {
                XSDK.getInstance().getLogger().log("gdt video: onADShow");
            }

            @Override
            public void onADExpose() {
                XSDK.getInstance().getLogger().log("gdt video: onADExpose");
            }

            @Override
            public void onReward(Map<String, Object> map) {
                if (callBack != null) {
                    callBack.onReward();
                }
                XSDK.getInstance().getLogger().log("gdt video: onReward");
            }

            @Override
            public void onADClick() {
                XSDK.getInstance().getLogger().log("gdt video: onADClick");
            }

            @Override
            public void onVideoComplete() {
                XSDK.getInstance().getLogger().log("gdt video: onVideoComplete");
            }

            @Override
            public void onADClose() {
                if (callBack != null) {
                    callBack.onClose();
                }
                XSDK.getInstance().getLogger().log("gdt video: onADClose");
            }

            @Override
            public void onError(AdError adError) {
                if (callBack != null) {
                    JSONObject ret = new JSONObject();
                    try {
                        ret.put("errMsg", adError.getErrorMsg());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    callBack.onError(ret.toString());
                }
                XSDK.getInstance().getLogger().log("gdt video: onError");
            }
        });

        rewardVideoAD.loadAD();
    }

    @Override
    public void show() {
        if (rewardVideoAD != null) {
            rewardVideoAD.showAD();
        }
    }

    @Override
    public void destory() {
        if (rewardVideoAD != null) {
            rewardVideoAD = null;
        }
    }
}
