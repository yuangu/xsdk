package com.seantone.xsdk.plugin.huawei_push;

import android.content.Context;
import android.os.Build;

import com.huawei.hms.aaid.HmsInstanceId;
import com.huawei.hms.adapter.internal.AvailableCode;
import com.huawei.hms.api.HuaweiMobileServicesUtil;
import com.huawei.hms.common.ApiException;
import com.seantone.xsdk.core.XSDK;
import com.seantone.xsdk.core.annotation.ForPush;
import com.seantone.xsdk.core.define.SDKParams;
import com.seantone.xsdk.core.impl.IPush;
import com.seantone.xsdk.core.impl.ISDK;
import com.seantone.xsdk.core.impl.IXSDKCallback;

import org.json.JSONObject;

//https://developer.huawei.com/consumer/cn/doc/development/HMSCore-Guides/android-app-quickstart-0000001071490422
@ForPush(provider = "huawei")
public class SDK implements ISDK, IPush {
    static public String provider = "huawei";

    //https://developer.huawei.com/consumer/cn/doc/development/HMSCore-Guides/android-app-quickstart-0000001071490422
    @Override
    public boolean isSupport() {
        return true;
    }

    @Override
    public boolean isBest() {
        String manufacturer = Build.MANUFACTURER.toLowerCase();

        if (!manufacturer.equals("huawei")) {
            return false;
        }
        int available = HuaweiMobileServicesUtil.isHuaweiMobileServicesAvailable(XSDK.getInstance().getTopActivity());
        if (available != AvailableCode.SUCCESS) {
            return false;
        }
        return true;
    }

    @Override
    public void initSDK(SDKParams params, IXSDKCallback callback) {
        Context content = XSDK.getInstance().getTopActivity();
        new Thread() {
            @Override
            public void run() {
                try {
                    XSDK.getInstance().getLogger().log("start init huawei sdk");
                    String token = HmsInstanceId.getInstance(content).getToken(params.appid, "HCM");
                    XSDK.getInstance().getLogger().log("huawei token:" + token);
                    callback.onSuccess("{}");
                    if (XSDK.getInstance().getPushCallBack() == null) {
                        return;
                    }
                    JSONObject ret = new JSONObject();

                    ret.put("regId", token);
                    ret.put("provider", SDK.provider);

                    XSDK.getInstance().getPushCallBack().onRegisterSucceed(ret.toString());
                } catch (Exception e) {
                    XSDK.getInstance().getLogger().log("huawei", e);
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
