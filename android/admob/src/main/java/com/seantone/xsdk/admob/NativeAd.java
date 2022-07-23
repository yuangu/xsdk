package com.seantone.xsdk.admob;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;
import android.util.Log;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.nativead.NativeCustomFormatAd;
import com.seantone.xsdk.core.XSDK;
import com.seantone.xsdk.core.impl.ad.INativeAd;
import com.seantone.xsdk.core.impl.ad.INativeAdEventCallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class NativeAd implements INativeAd {

    NativeCustomFormatAd nativeCustomFormatAd = null;

    public NativeAd(String adUnitId, INativeAdEventCallBack callBack){
        XSDK.getInstance().doInUIThread(() -> {
            this.load(adUnitId,  callBack);
        });
    }

    public void load(String adUnitId, INativeAdEventCallBack callBack) {
        Context context = XSDK.getInstance().getTopActivity();
        // 第二个参数为加载， 第三个参数为点击
        AdLoader adLoader = new AdLoader.Builder(context, adUnitId) //"/6499/example/native")
                .forCustomFormatAd("10063170", nativeCustomFormatAd -> {
                    this.nativeCustomFormatAd = nativeCustomFormatAd;
                    List<String> keys =  nativeCustomFormatAd.
                            getAvailableAssetNames();

                    // desc
                    String headline = nativeCustomFormatAd.getText("Headline").toString();
                    // title
                    String caption  = nativeCustomFormatAd.getText("Caption").toString();
                    String  url =   nativeCustomFormatAd.getImage("MainImage").getUri().toString();


                        JSONArray ret = new JSONArray();
                        JSONObject object = new JSONObject();
                        try {
                            object.put("desc", headline );
                            object.put("title", caption );
                            object.put("icon", url);
                            object.put("adId", "MainImage");
                            ret.put(object);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        callBack.onLoad(ret.toString());

                }, (nativeCustomFormatAd, s) -> {
                    Log.d("xsdk", s);

                }) .withNativeAdOptions(new NativeAdOptions.Builder()
                        .build()).withAdListener(new AdListener() {
                    // AdListener callbacks like OnAdFailedToLoad, OnAdOpened, OnAdClicked and
                    // so on, can be overridden here.

                    @Override
                    public void onAdFailedToLoad( LoadAdError loadAdError) {
                        super.onAdFailedToLoad(loadAdError);
                        JSONObject ret = new JSONObject();
                        try {
                            ret.put("errMsg", loadAdError.getMessage());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        callBack.onError(ret.toString());
                    }
                }).build();
        adLoader.loadAd(new AdRequest.Builder().build());
    }

    @Override
    public void reportAdShow() {
        if(this.nativeCustomFormatAd == null) {
            return;
        }

        if (Looper.myLooper() == Looper.getMainLooper()) {
            this.nativeCustomFormatAd.recordImpression();
        }else{
            Activity activity = XSDK.getInstance().getTopActivity();
            activity.runOnUiThread(() -> {
                this.nativeCustomFormatAd.recordImpression();
            });
        }
    }

    @Override
    public void reportAdClick(String adName) {
        if(this.nativeCustomFormatAd == null) {
            return;
        }

        if (Looper.myLooper() == Looper.getMainLooper()) {
            this.nativeCustomFormatAd.performClick(adName);
        }else
        {
            Activity activity = XSDK.getInstance().getTopActivity();
            activity.runOnUiThread(() -> {
                this.nativeCustomFormatAd.performClick(adName);
            });
        }
    }

    @Override
    public void destory() {
        if(this.nativeCustomFormatAd == null){
            return;
        }
        if (Looper.myLooper() == Looper.getMainLooper()) {
            this.nativeCustomFormatAd.destroy();
            this.nativeCustomFormatAd = null;
        }else{
            Activity activity = XSDK.getInstance().getTopActivity();
            activity.runOnUiThread(() -> {
                this.nativeCustomFormatAd.destroy();
                this.nativeCustomFormatAd = null;
            });
        }
    }
}
