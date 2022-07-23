package com.seantone.xsdk.vivo_push;

import android.content.Context;


import com.seantone.xsdk.core.XSDK;
import com.seantone.xsdk.plugin.vivo_push.SDK;
import com.vivo.push.model.UPSNotificationMessage;
import com.vivo.push.sdk.OpenClientPushMessageReceiver;

import org.json.JSONObject;

public class PushMessageReceiverImpl extends OpenClientPushMessageReceiver {

    @Override
    public void onNotificationMessageClicked(Context context, UPSNotificationMessage message) {
        if(XSDK.getInstance().getPushCallBack() == null) return;

        JSONObject ret = new JSONObject();
        try {
            ret.put("provider", SDK.provider);
            ret.put("title", message.getTitle());
            ret.put("payload", message.getSkipContent());
            ret.put("description", message.getContent());
            ret.put("isPassThrough", false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        XSDK.getInstance().getPushCallBack().onNotificationMessageClicked(ret.toString());
    }


    @Override
    public void onReceiveRegId(Context context, String regId) {
        if(XSDK.getInstance().getPushCallBack() == null) return;
        try {
            JSONObject ret = new JSONObject();

            ret.put("regId", regId );
            ret.put("provider", SDK.provider);

            XSDK.getInstance().getPushCallBack().onRegisterSucceed(ret.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
