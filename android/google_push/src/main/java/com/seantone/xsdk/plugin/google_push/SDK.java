package com.seantone.xsdk.plugin.google_push;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.seantone.xsdk.core.XSDK;
import com.seantone.xsdk.core.annotation.ForPush;
import com.seantone.xsdk.core.define.SDKParams;
import com.seantone.xsdk.core.impl.IPush;
import com.seantone.xsdk.core.impl.ISDK;
import com.seantone.xsdk.core.impl.IXSDKCallback;

import org.json.JSONObject;

//https://firebase.google.com/docs/cloud-messaging/android/client?hl=zh-cn
@ForPush(provider = "google")
public class SDK implements ISDK, IPush {
    static public String provider = "google";
    static public String  TAG= "google_push";
    @Override
    public boolean isSupport() {
        return false;
    }

    @Override
    public boolean isBest() {
        return false;
    }

    @Override
    public void initSDK(SDKParams params, IXSDKCallback callback) {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();
                        try{
                            callback.onSuccess("{}");
                            if (XSDK.getInstance().getPushCallBack() == null) {
                                return;
                            }
                            JSONObject ret = new JSONObject();

                            ret.put("regId", token);
                            ret.put("provider", SDK.provider);

                            XSDK.getInstance().getPushCallBack().onRegisterSucceed(ret.toString());
                        } catch (Exception e) {
                            e.printStackTrace();

                        }

                    }
                });
    }
}
