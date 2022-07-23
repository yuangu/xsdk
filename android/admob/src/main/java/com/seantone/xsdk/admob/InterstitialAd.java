package com.seantone.xsdk.admob;

import android.app.Activity;
import android.os.Looper;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.seantone.xsdk.core.XSDK;
import com.seantone.xsdk.core.define.AdParams;
import com.seantone.xsdk.core.impl.ad.IInterstitialAd;
import com.seantone.xsdk.core.impl.ad.IInterstitialAdEventCallBack;

import org.json.JSONObject;

import androidx.annotation.NonNull;

public class InterstitialAd implements IInterstitialAd {
    private com.google.android.gms.ads.interstitial.InterstitialAd mInterstitialAd = null;

    public InterstitialAd(AdParams params, IInterstitialAdEventCallBack callBack) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            create(params, callBack);
        } else {
            Activity activity = XSDK.getInstance().getTopActivity();
            activity.runOnUiThread(() -> create(params, callBack));
        }
    }

    void create(AdParams params, IInterstitialAdEventCallBack callBack) {
        AdRequest adRequest = new AdRequest.Builder().build();
        Activity activity = XSDK.getInstance().getTopActivity();
        com.google.android.gms.ads.interstitial.InterstitialAd.load(activity, params.adUnitId, adRequest,
                new com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull com.google.android.gms.ads.interstitial.InterstitialAd interstitialAd){
                    // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        interstitialAd.setFullScreenContentCallback(
                                new FullScreenContentCallback() {
                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        // Called when fullscreen content is dismissed.
                                        // Make sure to set your reference to null so you don't
                                        // show it a second time.
                                        InterstitialAd.this.mInterstitialAd = null;
                                        if(callBack != null)
                                        {
                                            callBack.onClose();
                                        }
                                    }

                                    @Override
                                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                                        // Called when fullscreen content failed to show.
                                        // Make sure to set your reference to null so you don't
                                        // show it a second time.
                                        InterstitialAd.this.mInterstitialAd = null;
                                    }

                                    @Override
                                    public void onAdShowedFullScreenContent() {
                                        // Called when fullscreen content is shown.
                                    }
                                });
                        callBack.onLoad("{}");
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        XSDK.getInstance().getLogger().log(loadAdError.getMessage());
                        // Handle the error
                        mInterstitialAd = null;
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
                });
    }

    @Override
    public void show(String params) {
       XSDK.getInstance().doInUIThread(() -> mInterstitialAd.show(XSDK.getInstance().getTopActivity()));
    }
}
