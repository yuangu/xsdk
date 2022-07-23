package com.seantone.xsdk.admob;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.seantone.xsdk.core.Utils;
import com.seantone.xsdk.core.XSDK;
import com.seantone.xsdk.core.define.AdParams;
import com.seantone.xsdk.core.impl.ad.IBannerAd;
import com.seantone.xsdk.core.impl.ad.IBannerAdEventCallBack;
import com.seantone.xsdk.core.plugin.XSDKPlugin;

import org.json.JSONException;
import org.json.JSONObject;

public class BannerAd implements IBannerAd {
    AdView mAdView = null;
    LinearLayout bannerLayout = null;

    public BannerAd(AdParams params, IBannerAdEventCallBack callBack) {
        XSDK.getInstance().doInUIThread(() -> create(params, callBack));
    }

    void create(AdParams params, IBannerAdEventCallBack callBack) {
        Activity activity = XSDK.getInstance().getTopActivity();

        // Create a banner ad. The ad size and ad unit ID must be set before calling loadAd.
        mAdView = new AdView(activity);
        if(params.style != null && params.style.size != null)
        {
            switch (params.style.size.intValue())
            {
                case 1:
                    mAdView.setAdSize(AdSize.BANNER);
                    break;
                case 3:
                    mAdView.setAdSize(AdSize.LARGE_BANNER);
                    break;
                case 2:
                default:
                    mAdView.setAdSize(AdSize.FULL_BANNER);
            }
        }else
        {
            mAdView.setAdSize(AdSize.FULL_BANNER);
        }

        mAdView.setAdUnitId(params.adUnitId);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.

                if (callBack != null) {
                        new Handler().postDelayed(() -> {
                            try {
                                JSONObject ret = new JSONObject();
                                ret.put("width", BannerAd.this.bannerLayout.getWidth());
                                ret.put("height", BannerAd.this.bannerLayout.getHeight());

                                callBack.onLoad(ret.toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }, 20);
                }
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                // Code to be executed when an ad request fails.
                if (callBack != null) {
                    JSONObject ret = new JSONObject();
                    try {
                        ret.put("errMsg", adError.getMessage());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    callBack.onError(ret.toString());
                }
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
                if (callBack != null) {
                    //mIBannerAdEventCallBack.onShow();
                }
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
                if (callBack != null) {
                    //mIBannerAdEventCallBack.onClick();
                }
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
                if (callBack != null) {
                    callBack.onHide();
                }
            }
        });

        LinearLayout bannerLayout = new LinearLayout(activity);
        bannerLayout.setOrientation(LinearLayout.VERTICAL);
        bannerLayout.addView(mAdView);

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        if(params.style!=null)
        {
            Utils.setBannerStyle(params.style, layoutParams);
        }

        XSDK.getInstance().getTopActivity().addContentView(bannerLayout, layoutParams);
        this.bannerLayout = bannerLayout;
        bannerLayout.setVisibility(View.INVISIBLE);
        AdRequest.Builder adRequestBuilder = new AdRequest.Builder();
        mAdView.loadAd(adRequestBuilder.build());
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
