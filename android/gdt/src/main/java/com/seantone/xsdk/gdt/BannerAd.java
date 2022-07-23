package com.seantone.xsdk.gdt;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.qq.e.ads.banner2.UnifiedBannerADListener;
import com.qq.e.ads.banner2.UnifiedBannerView;
import com.qq.e.comm.util.AdError;
import com.seantone.xsdk.core.Utils;
import com.seantone.xsdk.core.XSDK;
import com.seantone.xsdk.core.define.AdParams;
import com.seantone.xsdk.core.impl.ad.IBannerAd;
import com.seantone.xsdk.core.impl.ad.IBannerAdEventCallBack;

import org.json.JSONException;
import org.json.JSONObject;

public class BannerAd implements IBannerAd {
    LinearLayout bannerLayout = null;
    UnifiedBannerView mAdView = null;

    public BannerAd(AdParams params, IBannerAdEventCallBack callBack) {
        XSDK.getInstance().doInUIThread(() -> create(params, callBack));
    }

    private FrameLayout.LayoutParams getUnifiedBannerLayoutParams() {
        Activity activity = XSDK.getInstance().getTopActivity();
        Point screenSize = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(screenSize);
        return new FrameLayout.LayoutParams(screenSize.x,  Math.round(screenSize.x / 6.4F));
    }

    void create(AdParams params, IBannerAdEventCallBack callBack) {
        Activity activity = XSDK.getInstance().getTopActivity();
        mAdView = new UnifiedBannerView(activity, params.adUnitId, new UnifiedBannerADListener() {
            @Override
            public void onNoAD(AdError adError) {
                XSDK.getInstance().getLogger().log(  "ErrorId:" +  String.valueOf(adError.getErrorCode()) + " ErroMsg:" +  adError.getErrorMsg() );

                JSONObject ret = new JSONObject();
                try {
                    ret.put("errMsg", adError.getErrorMsg());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                callBack.onError(ret.toString());
            }

            @Override
            public void onADReceive() {
                XSDK.getInstance().getLogger().log("gdt banner: onADReceive" );

                int height =  BannerAd.this.bannerLayout.getHeight();
                int width =  BannerAd.this.bannerLayout.getWidth();

                try {
                    JSONObject ret = new JSONObject();
                    ret.put("width", width );
                    ret.put("height", height);
                    if (callBack != null) {
                        callBack.onLoad(ret.toString());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onADExposure() {
                XSDK.getInstance().getLogger().log("gdt banner: onADExposure" );
            }

            @Override
            public void onADClosed() {
                XSDK.getInstance().getLogger().log("gdt banner: onADClosed" );
                if (callBack != null) {
                    callBack.onHide();
                }
            }

            @Override
            public void onADClicked() {
                XSDK.getInstance().getLogger().log("gdt banner: onADClicked" );
            }

            @Override
            public void onADLeftApplication() {
                XSDK.getInstance().getLogger().log("gdt banner: onADLeftApplication" );
            }
        });

        LinearLayout bannerLayout = new LinearLayout(activity);
        bannerLayout.setOrientation(LinearLayout.VERTICAL);
        bannerLayout.addView(mAdView, getUnifiedBannerLayoutParams());

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        if(params.style!=null)
        {
            Utils.setBannerStyle(params.style, layoutParams);
        }
        XSDK.getInstance().getTopActivity().addContentView(bannerLayout, layoutParams);
        bannerLayout.setVisibility(View.INVISIBLE);
        this.bannerLayout = bannerLayout;
        mAdView.loadAD();
    }

    @Override
    public void show(String params) {
        if (this.bannerLayout == null) return;
        XSDK.getInstance().getLogger().log("set admob banner VISIBLE");
        XSDK.getInstance().doInUIThread(() -> bannerLayout.setVisibility(View.VISIBLE));
    }

    @Override
    public void hide() {
        if (this.bannerLayout == null) return;
        XSDK.getInstance().getLogger().log("set admob banner INVISIBLE");
        XSDK.getInstance().doInUIThread(() -> bannerLayout.setVisibility(View.INVISIBLE));
    }

    @Override
    public void destory() {
        if (this.bannerLayout == null) return;
        XSDK.getInstance().doInUIThread(() ->{
            this.mAdView.destroy();
            bannerLayout.removeAllViews();
            bannerLayout.setVisibility(View.GONE);
            bannerLayout = null;
        });
    }
}
