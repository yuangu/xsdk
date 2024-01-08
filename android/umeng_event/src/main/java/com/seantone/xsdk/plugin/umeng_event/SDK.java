package com.seantone.xsdk.plugin.umeng_event;

import android.content.Context;

import com.seantone.xsdk.core.XSDK;
import com.seantone.xsdk.core.annotation.ForEvent;
import com.seantone.xsdk.core.define.EventParams;
import com.seantone.xsdk.core.define.SDKParams;
import com.seantone.xsdk.core.impl.IEvent;
import com.seantone.xsdk.core.impl.ISDK;
import com.seantone.xsdk.core.impl.IXSDKCallback;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

@ForEvent(provider = "umeng")
public class SDK implements ISDK, IEvent {

    @Override
    public void initSDK(SDKParams params, IXSDKCallback callback) {
        // 该sdk会自动初使化
        Context content = XSDK.getInstance().getTopActivity();

        String channelId = params.channel;
        UMConfigure.preInit(content.getApplicationContext(), params.appid, channelId);

        //使用线程初使化
        new Thread(() -> {
            try{
                UMConfigure.submitPolicyGrantResult(content.getApplicationContext(), true);
                UMConfigure.init(content.getApplicationContext(), params.appid, channelId, UMConfigure.DEVICE_TYPE_PHONE, "");
            }catch (Exception e){
                XSDK.getInstance().getLogger().log("umeng", e);
                e.printStackTrace();
                return;
            }
            XSDK.getInstance().doInUIThread(() -> {
                callback.onSuccess("{}");
            });
        }).start();
    }

    @Override
    public void postEvent(EventParams params) {
        Context content = XSDK.getInstance().getTopActivity();
        XSDK.getInstance().doInUIThread(() -> {
            MobclickAgent.onEventObject(content, params.eventName, params.eventArgs);
        });
    }
}
