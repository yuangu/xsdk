package com.seantone.xsdk.plugin.vivo_push;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;


import com.seantone.xsdk.core.XSDK;
import com.seantone.xsdk.core.annotation.ForPush;
import com.seantone.xsdk.core.define.SDKParams;
import com.seantone.xsdk.core.impl.IPush;
import com.seantone.xsdk.core.impl.ISDK;
import com.seantone.xsdk.core.impl.IXSDKCallback;
import com.vivo.push.PushClient;
import com.vivo.push.IPushActionListener;

import org.json.JSONObject;


@ForPush(provider = "vivo")
public class SDK implements ISDK, IPush {
    static public String provider = "vivo";
    public static String TAG = "vivo_push";

    // vivo推送只支持vivo平台 https://dev.vivo.com.cn/documentCenter/doc/156
    @Override
    public boolean isSupport() {
        String brand = Build.BRAND.toLowerCase();
        String manufacturer = Build.MANUFACTURER.toLowerCase();
        if (manufacturer.equals("vivo") || brand.contains("vivo") || brand.contains("iqoo")) {
            return PushClient.getInstance(XSDK.getInstance().getTopActivity()).isSupport();
        }
        return false;
    }

    @Override
    public boolean isBest() {
        return isSupport();
    }

    @Override
    public void initSDK(SDKParams params, IXSDKCallback callback) {
        Context context= XSDK.getInstance().getTopActivity().getApplicationContext();
        try {
            ApplicationInfo appinfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            appinfo.metaData.putString("com.vivo.push.app_id", params.appid);
            appinfo.metaData.putString("com.vivo.push.api_key", params.appkey);

            PushClient.getInstance(context).initialize();
        } catch (Exception e) {
            e.printStackTrace();
            callback.onFaild("{}");
            return;
        }

        // 打开推送开关
        PushClient.getInstance(context).turnOnPush(state -> {
            // 开关状态处理， 0代表成功
            if (state == 0) {
                XSDK.getInstance().getLogger().log("开启成功");
                callback.onSuccess("{}");

                if(XSDK.getInstance().getPushCallBack() == null) return;
                String regId = PushClient.getInstance(context).getRegId();
                try {
                    JSONObject ret = new JSONObject();

                    ret.put("regId", regId );
                    ret.put("provider", SDK.provider);

                    XSDK.getInstance().getPushCallBack().onRegisterSucceed(ret.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                XSDK.getInstance().getLogger().log("开启失败");
                callback.onFaild("{}");
            }
        });
    }
}
