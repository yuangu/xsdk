package com.seantone.xsdk.plugin.google_event;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.seantone.xsdk.core.XSDK;
import com.seantone.xsdk.core.annotation.ForEvent;
import com.seantone.xsdk.core.define.EventParams;
import com.seantone.xsdk.core.define.SDKParams;
import com.seantone.xsdk.core.impl.IEvent;
import com.seantone.xsdk.core.impl.ISDK;
import com.seantone.xsdk.core.impl.IXSDKCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Set;


@ForEvent(provider = "firebase")
public class SDK implements ISDK, IEvent {

    @Override
    public void initSDK(SDKParams params, IXSDKCallback callback) {
        // 该sdk会自动初使化
        Context content = XSDK.getInstance().getTopActivity();
        // 提交这个的话，可以在服务器侧提交firebase事件
        // https://developers.google.cn/analytics/devguides/collection/protocol/ga4/sending-events?client_type=firebase#required_parameters
        FirebaseAnalytics.getInstance(content).getAppInstanceId().addOnCompleteListener(appInstanceId -> {
            JSONObject ret = new JSONObject();
            try {
                ret.put("app_instance_id", appInstanceId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            callback.onSuccess(ret.toString());
        });
    }

    @Override
    public void postEvent(EventParams params) {

        XSDK.getInstance().doInUIThread(() -> {
                    Context content = XSDK.getInstance().getTopActivity();
                    Bundle bundle = new Bundle();

                    Set<String> keySet = params.eventArgs.keySet();
                    Iterator<String> iter = keySet.iterator();
                    while (iter.hasNext()) {
                        String key = iter.next();
                        Object value = params.eventArgs.get(key);
                        if (value instanceof String) {
                            bundle.putString(key, (String) value);
                        } else {
                            bundle.putString(key, String.valueOf(value));
                        }
                    }

                    // 设置uid
                    if (!TextUtils.isEmpty(params.uid)) {
                        FirebaseAnalytics.getInstance(content).setUserId(params.uid);
                    }

                    FirebaseAnalytics.getInstance(content).logEvent(params.eventName, bundle);
                }
        );
    }
}
