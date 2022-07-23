package com.seantone.xsdk.oppo_push;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

import com.seantone.xsdk.core.Utils;
import com.seantone.xsdk.core.XSDK;
import com.seantone.xsdk.plugin.oppo_push.SDK;

import org.json.JSONObject;


public class OppoMessageReceiveActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Uri data = getIntent().getData();

        XSDK.getInstance().getLogger().log("show OppoMessageReceiveActivity");
        if(isTaskRoot())
        {
            XSDK.getInstance().getLogger().log("需要启动新的Activity");
            Utils.runMainActivity(this);
        }

        this.finish();
        if(data != null && XSDK.getInstance().getPushCallBack() != null){
            JSONObject ret = new JSONObject();
            try {
                ret.put("provider", SDK.provider);
                ret.put("title", data.getQueryParameter("title"));
                ret.put("payload", data.getQueryParameter("payload"));
                ret.put("description",data.getQueryParameter("description"));
                ret.put("isPassThrough", false);
            } catch (Exception e) {
                e.printStackTrace();
            }

            XSDK.getInstance().getPushCallBack().onNotificationMessageClicked(ret.toString());
        }
        // mixpush://com.mixpush.oppo/message?title=title&description=description&payload=%7b%22url%22%3a%22http%3a%2f%2fsoso.com%22%7d
    }
}