package com.seantone.xsdk.core;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;
import android.util.Log;

import com.google.gson.Gson;
import com.seantone.xsdk.core.annotation.ForAD;
import com.seantone.xsdk.core.annotation.ForLogin;
import com.seantone.xsdk.core.annotation.ForPay;
import com.seantone.xsdk.core.annotation.ForPush;
import com.seantone.xsdk.core.annotation.ForShare;
import com.seantone.xsdk.core.define.AdParams;
import com.seantone.xsdk.core.define.LoginParams;
import com.seantone.xsdk.core.define.PayParams;
import com.seantone.xsdk.core.define.SDKParams;
import com.seantone.xsdk.core.define.ShareParams;
import com.seantone.xsdk.core.impl.IAD;
import com.seantone.xsdk.core.impl.ILogger;
import com.seantone.xsdk.core.impl.ILogin;
import com.seantone.xsdk.core.impl.IPay;
import com.seantone.xsdk.core.impl.IPush;
import com.seantone.xsdk.core.impl.IPushCallback;
import com.seantone.xsdk.core.impl.ISDK;
import com.seantone.xsdk.core.impl.IShare;
import com.seantone.xsdk.core.impl.IXSDKCallback;
import com.seantone.xsdk.core.impl.ad.IBannerAd;
import com.seantone.xsdk.core.impl.ad.IBannerAdEventCallBack;
import com.seantone.xsdk.core.impl.ad.IInterstitialAd;
import com.seantone.xsdk.core.impl.ad.IInterstitialAdEventCallBack;
import com.seantone.xsdk.core.impl.ad.INativeAd;
import com.seantone.xsdk.core.impl.ad.INativeAdEventCallBack;
import com.seantone.xsdk.core.impl.ad.IRewardedVideoAd;
import com.seantone.xsdk.core.impl.ad.IRewardedVideoAdEventCallBack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XSDK implements ISDK, ILogin, IPay, IShare, IAD {
    static String TAG = "xsdk";


    private ActivityManager mActivityManager = null;

    private static XSDK mInstace = null;

    public static XSDK getInstance() {
        if (null == mInstace) {
            mInstace = new XSDK();
        }
        return mInstace;
    }

    // 分享类的sdk集合
    private Map<String, IShare> mShareSDKMap = new HashMap<String, IShare>();
    // 支付的sdk集合
    private Map<String, IPay> mPaySDKMap = new HashMap<String, IPay>();
    // 登录的sdk
    private Map<String, ILogin> mLoginSDKMap = new HashMap<String, ILogin>();
    // 推送类的sdk
    private Map<String, IPush> mPushSDKMap = new HashMap<>();
    // 广告类的sdk
    private Map<String, IAD> mADSDKMap = new HashMap<>();


    //========================================================================
    ILogger logger = new ILogger() {
        String mTag = "xsdk";

        @Override
        public void setTag(String tag) {

        }

        @Override
        public void log(String content) {
            Log.d(mTag, content);
        }

        @Override
        public void log(String content, Throwable throwable) {
            Log.d(mTag, content, throwable);
        }
    };

    public void setLogger(ILogger logger) {
        this.logger = logger;
    }

    public ILogger getLogger() {
        return this.logger;
    }

    //========================================================================
    @Override
    public void initSDK(SDKParams params, IXSDKCallback callback) {
        doInUIThread(() -> {
            if (params.service.equals("oauth")) {
                if (mLoginSDKMap.containsKey(params.provider)) {
                    ISDK sdk = (ISDK) (mLoginSDKMap.get(params.provider));
                    sdk.initSDK(params, callback);
                }
            } else if (params.service.equals("share")) {
                if (mShareSDKMap.containsKey(params.provider)) {
                    ISDK sdk = (ISDK) (mShareSDKMap.get(params.provider));
                    sdk.initSDK(params, callback);
                }
            } else if (params.service.equals("payment")) {
                if (mPaySDKMap.containsKey(params.provider)) {
                    ISDK sdk = (ISDK) (mPaySDKMap.get(params.provider));
                    sdk.initSDK(params, callback);
                }
            } else if (params.service.equals("push")) {
                if (mPushSDKMap.containsKey(params.provider)) {
                    ISDK sdk = (ISDK) (mPushSDKMap.get(params.provider));
                    sdk.initSDK(params, callback);
                }
            } else if (params.service.equals("ad")) {
                if (mADSDKMap.containsKey(params.provider)) {
                    ISDK sdk = (ISDK) (mADSDKMap.get(params.provider));
                    sdk.initSDK(params, callback);
                }
            } else {
                if (mLoginSDKMap.containsKey(params.provider)) {
                    ISDK sdk = (ISDK) (mLoginSDKMap.get(params.provider));
                    sdk.initSDK(params, callback);
                    return;
                }

                if (mShareSDKMap.containsKey(params.provider)) {
                    ISDK sdk = (ISDK) (mShareSDKMap.get(params.provider));
                    sdk.initSDK(params, callback);
                    return;
                }

                if (mPaySDKMap.containsKey(params.provider)) {
                    ISDK sdk = (ISDK) (mPaySDKMap.get(params.provider));
                    sdk.initSDK(params, callback);
                    return;
                }

                if (mADSDKMap.containsKey(params.provider)) {
                    ISDK sdk = (ISDK) (mADSDKMap.get(params.provider));
                    sdk.initSDK(params, callback);
                    return;
                }
            }
        });
    }

    public static void initSDK(String jsonParams, IXSDKCallback callback) {
        SDKParams params = new Gson().fromJson(jsonParams, SDKParams.class);
        XSDK.getInstance().initSDK(params, callback);
    }

    //========================================================================
    @Override
    public void share(ShareParams params, IXSDKCallback callback) {
        doInUIThread(() -> {
            if (mShareSDKMap.containsKey(params.provider)) {
                mShareSDKMap.get(params.provider).share(params, callback);
            } else {
                getLogger().log("not found share sdk:" + params.provider);
            }
        });
    }

    public static void share(String jsonParams, IXSDKCallback callback) {
        ShareParams params = new Gson().fromJson(jsonParams, ShareParams.class);
        XSDK.getInstance().share(params, callback);
    }

    //========================================================================
    @Override
    public void pay(PayParams params, IXSDKCallback callback) {
        doInUIThread(() -> {
            if (mPaySDKMap.containsKey(params.provider)) {
                mPaySDKMap.get(params.provider).pay(params, callback);
            } else {
                getLogger().log("not found pay sdk:" + params.provider);
            }
        });
    }

    public static void pay(String jsonParams, IXSDKCallback callback) {
        PayParams params = new Gson().fromJson(jsonParams, PayParams.class);
        XSDK.getInstance().pay(params, callback);
    }

    //========================================================================
    @Override
    public void login(LoginParams params, IXSDKCallback callback) {
        this.doInUIThread(() -> {
            if (mLoginSDKMap.containsKey(params.provider)) {
                mLoginSDKMap.get(params.provider).login(params, callback);
            } else {
                getLogger().log("not found login sdk:" + params.provider);
            }
        });
    }

    public static void login(String jsonParams, IXSDKCallback callback) {
        LoginParams params = new Gson().fromJson(jsonParams, LoginParams.class);
        XSDK.getInstance().login(params, callback);
    }

    //========================================================================
    IPushCallback mPushCallback = null;

    public IPushCallback getPushCallBack() {
        return mPushCallback;
    }

    public static void onPush(IPushCallback callback) {
        XSDK.getInstance().mPushCallback = callback;
    }

    //========================================================================
    @Override
    public IRewardedVideoAd createRewardedVideoAd(AdParams params, IRewardedVideoAdEventCallBack callBack) {
        if (mADSDKMap.containsKey(params.provider)) {
            return mADSDKMap.get(params.provider).createRewardedVideoAd(params, callBack);
        } else {
            getLogger().log("not found login sdk:" + params.provider);
        }
        return null;
    }

    public static IRewardedVideoAd createRewardedVideoAd(String jsonParams, IRewardedVideoAdEventCallBack callBack) {
        AdParams params = new Gson().fromJson(jsonParams, AdParams.class);
        return XSDK.getInstance().createRewardedVideoAd(params, callBack);
    }

    @Override
    public IBannerAd createBannerAd(AdParams params, IBannerAdEventCallBack callBack) {
        if (mADSDKMap.containsKey(params.provider)) {
            return mADSDKMap.get(params.provider).createBannerAd(params, callBack);
        } else {
            getLogger().log("not found login sdk:" + params.provider);
        }
        return null;
    }

    public static IBannerAd createBannerAd(String jsonParams, IBannerAdEventCallBack callBack) {
        AdParams params = new Gson().fromJson(jsonParams, AdParams.class);
        return XSDK.getInstance().createBannerAd(params, callBack);
    }

    @Override
    public INativeAd createNativeAd(AdParams params, INativeAdEventCallBack callBack) {
        if (mADSDKMap.containsKey(params.provider)) {
            return mADSDKMap.get(params.provider).createNativeAd(params, callBack);
        } else {
            getLogger().log("not found login sdk:" + params.provider);
        }
        return null;
    }

    public static  INativeAd createNativeAd(String jsonParams, INativeAdEventCallBack callBack) {
        AdParams params = new Gson().fromJson(jsonParams, AdParams.class);
        return  XSDK.getInstance().createNativeAd(params, callBack);
    }

    @Override
    public IInterstitialAd createInterstitialAd(AdParams params, IInterstitialAdEventCallBack callBack) {
        if (mADSDKMap.containsKey(params.provider)) {
            return mADSDKMap.get(params.provider).createInterstitialAd(params, callBack);
        } else {
            getLogger().log("not found login sdk:" + params.provider);
        }
        return null;
    }

    public static  IInterstitialAd createInterstitialAd(String jsonParams, IInterstitialAdEventCallBack callBack) {
        AdParams params = new Gson().fromJson(jsonParams, AdParams.class);
        return  XSDK.getInstance(). createInterstitialAd(params, callBack);
    }

    //========================================================================
    private void _init(Context context) {
        mActivityManager = new ActivityManager(context);

        String packageName = "com.seantone.xsdk.plugin";
        List<Class> allClass = null;
        try {
            allClass = ClassUtils.getClasses(context, packageName);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        for (int i = 0; i < allClass.size(); i++) {
            Class clazz = allClass.get(i);
            if (!ISDK.class.isAssignableFrom(clazz)) {
                XSDK.getInstance().getLogger().log(clazz.getName() + " not implements ISDK");
                continue;
            }

            Object sdk = null;
            try {
                sdk = clazz.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            if (IPay.class.isAssignableFrom(clazz) && clazz.isAnnotationPresent(ForPay.class)) {
                ForPay forPay = (ForPay) clazz.getAnnotation(ForPay.class);
                XSDK.this.mPaySDKMap.put(forPay.provider(), (IPay) sdk);
            }

            if (IShare.class.isAssignableFrom(clazz) && clazz.isAnnotationPresent(ForShare.class)) {
                ForShare forShare = (ForShare) clazz.getAnnotation(ForShare.class);
                XSDK.this.mShareSDKMap.put(forShare.provider(), (IShare) sdk);
            }

            if (ILogin.class.isAssignableFrom(clazz) && clazz.isAnnotationPresent(ForLogin.class)) {
                ForLogin forLogin = (ForLogin) clazz.getAnnotation(ForLogin.class);
                XSDK.this.mLoginSDKMap.put(forLogin.provider(), (ILogin) sdk);
            }

            if (IPush.class.isAssignableFrom(clazz) && clazz.isAnnotationPresent(ForPush.class)) {
                ForPush forLogin = (ForPush) clazz.getAnnotation(ForPush.class);
                XSDK.this.mPushSDKMap.put(forLogin.provider(), (IPush) sdk);
            }

            if (IAD.class.isAssignableFrom(clazz) && clazz.isAnnotationPresent(ForAD.class)) {
                ForAD forLogin = (ForAD) clazz.getAnnotation(ForAD.class);
                XSDK.this.mADSDKMap.put(forLogin.provider(), (IAD) sdk);
            }
        }
    }

    public void init(Context context) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            this._init(context);
        } else {
            ((Activity) context).runOnUiThread(() -> {
                this._init(context);
            });
        }
    }

    public Activity getTopActivity() {
        if (mActivityManager != null) {
            return mActivityManager.getTopActivity();
        }
        return null;
    }

    public void doInUIThread(Runnable runnable) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            runnable.run();
        } else {
            getTopActivity().runOnUiThread(runnable);
        }
    }
}
