package com.seantone.xsdk.plugin.xiaomi_push;

import android.os.Build;

import com.seantone.xsdk.core.XSDK;
import com.seantone.xsdk.core.annotation.ForPush;
import com.seantone.xsdk.core.define.SDKParams;
import com.seantone.xsdk.core.impl.IPush;
import com.seantone.xsdk.core.impl.ISDK;
import com.seantone.xsdk.core.impl.IXSDKCallback;
import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.mipush.sdk.Logger;
import com.xiaomi.mipush.sdk.MiPushClient;

@ForPush(provider = "xiaomi")
public class SDK implements ISDK, IPush {
    static public String provider = "xiaomi";

    @Override
    public void initSDK(SDKParams params, IXSDKCallback callback) {
        if(!isSupport())
        {
            return;
        }
        Logger.setLogger(XSDK.getInstance().getTopActivity(), new LoggerInterface() {
            @Override
            public void setTag(String s) {

            }

            @Override
            public void log(String s) {
                XSDK.getInstance().getLogger().log(s);
            }

            @Override
            public void log(String s, Throwable throwable) {
                XSDK.getInstance().getLogger().log(s, throwable);
            }
        });
        MiPushClient.registerPush(XSDK.getInstance().getTopActivity().getApplicationContext(), params.appid, params.appkey);
    }

    @Override
    public boolean isSupport() {
        return true;
    }

    @Override
    public boolean isBest() {
        // 如果手机品牌是小米平台
        String manufacturer = Build.MANUFACTURER.toLowerCase();
        if (!manufacturer.equals("xiaomi")) {
            return true;
        }
        return false;
    }
}
