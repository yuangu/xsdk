package com.seantone.xsdk.google_push;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.seantone.xsdk.core.XSDK;
import com.seantone.xsdk.plugin.google_push.SDK;

import org.json.JSONObject;

public class XSDKFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        if(XSDK.getInstance().getPushCallBack() == null)
        {
            return;
        }
        JSONObject ret = new JSONObject();
        try {
            ret.put("provider",  SDK.provider);
            if (message.getNotification() != null) {
                ret.put("title", message.getNotification().getTitle());
                ret.put("description", message.getNotification().getBody());
            }
            ret.put("payload", message.getData());
            ret.put("isPassThrough", message.getNotification() == null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        XSDK.getInstance().getPushCallBack().onNotificationMessageArrived(ret.toString());
    }

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        if (XSDK.getInstance().getPushCallBack() == null) return;
        try{
            JSONObject ret = new JSONObject();

            ret.put("regId", token);
            ret.put("provider", SDK.provider);

            XSDK.getInstance().getPushCallBack().onRegisterSucceed(ret.toString());
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
