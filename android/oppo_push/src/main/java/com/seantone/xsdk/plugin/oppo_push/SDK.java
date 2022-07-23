package com.seantone.xsdk.plugin.oppo_push;


import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.heytap.msp.push.HeytapPushManager;
import com.heytap.msp.push.callback.ICallBackResultService;
import com.seantone.xsdk.core.XSDK;
import com.seantone.xsdk.core.annotation.ForPush;
import com.seantone.xsdk.core.define.SDKParams;
import com.seantone.xsdk.core.impl.IPush;
import com.seantone.xsdk.core.impl.ISDK;
import com.seantone.xsdk.core.impl.IXSDKCallback;

import org.json.JSONObject;


//https://open.oppomobile.com/new/developmentDoc/info?id=11219
@ForPush(provider = "oppo")
public class SDK implements ISDK, IPush {
    static public String provider = "oppo";
    public static final String TAG = "oppo";

    @Override
    public boolean isSupport() {
        Context context = XSDK.getInstance().getTopActivity();
        String brand = Build.BRAND.toLowerCase();
        String manufacturer = Build.MANUFACTURER.toLowerCase();
        if (manufacturer.equals("oneplus") || manufacturer.equals("oppo") || brand.equals("oppo") || brand.equals("realme")) {
            HeytapPushManager.init(context, true);
            return HeytapPushManager.isSupportPush(context);
        }
        return false;
    }

    @Override
    public boolean isBest() {
        return  isSupport();
    }

    @Override
    public void initSDK(SDKParams params, IXSDKCallback callback) {
        Context context = XSDK.getInstance().getTopActivity().getApplicationContext();
        HeytapPushManager.init(context, true);
        HeytapPushManager.register(context, params.appid, params.appkey, new ICallBackResultService() {
            @Override
            public void onRegister(int responseCode, String regId) {
                XSDK.getInstance().getLogger().log("onRegister:" + regId);
                if(responseCode != 0){
                    callback.onFaild("{}");
                    return;
                }
                callback.onSuccess("{}");
                if(XSDK.getInstance().getPushCallBack() == null) return;
                try {
                    callback.onSuccess("{}");

                    JSONObject ret = new JSONObject();

                    ret.put("regId", regId );
                    ret.put("provider", SDK.provider);

                    XSDK.getInstance().getPushCallBack().onRegisterSucceed(ret.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onUnRegister(int responseCode) {

            }

            @Override
            public void onSetPushTime(int responseCode, String pushTime) {

            }

            @Override
            public void onGetPushStatus(int responseCode, int status) {
                Log.d(TAG, "onGetPushStatus responseCode = " + responseCode + ", status = " + status);
            }

            @Override
            public void onGetNotificationStatus(int responseCode, int status) {
                Log.d(TAG, "onGetNotificationStatus responseCode = " + responseCode + ", status = " + status);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }
}
