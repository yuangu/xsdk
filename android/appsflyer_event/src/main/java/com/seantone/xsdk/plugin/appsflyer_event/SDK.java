package com.seantone.xsdk.plugin.appsflyer_event;

import android.content.Context;
import android.text.TextUtils;

import com.appsflyer.AppsFlyerLib;
import com.seantone.xsdk.core.XSDK;
import com.seantone.xsdk.core.annotation.ForEvent;
import com.seantone.xsdk.core.define.EventParams;
import com.seantone.xsdk.core.define.SDKParams;
import com.seantone.xsdk.core.impl.IEvent;
import com.seantone.xsdk.core.impl.ISDK;
import com.seantone.xsdk.core.impl.IXSDKCallback;

@ForEvent(provider = "appsflyer")
public class SDK implements ISDK, IEvent {

    @Override
    public void initSDK(SDKParams params, IXSDKCallback callback) {
        // 该sdk会自动初使化
        Context content = XSDK.getInstance().getTopActivity();
        XSDK.getInstance().doInUIThread(() -> {
            AppsFlyerLib.getInstance().init(params.appid, null, content);
                    AppsFlyerLib.getInstance().start(content);//延迟初始化AppsFlyer
                    //AppsFlyerLib.getInstance().setOutOfStore(ChannelUtils.getChannel());
                    callback.onSuccess("{}");
                }
        );
    }

    @Override
    public void postEvent(EventParams params) {
        Context content = XSDK.getInstance().getTopActivity();
        XSDK.getInstance().doInUIThread(() -> {
                    if (!TextUtils.isEmpty(params.uid)) {
                        AppsFlyerLib.getInstance().setCustomerUserId(params.uid);
                    }
                    AppsFlyerLib.getInstance().logEvent(content.getApplicationContext(), params.eventName, params.eventArgs);
                }
        );
    }
}
