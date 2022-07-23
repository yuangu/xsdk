package com.seantone.xsdk.huawei_push;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.seantone.xsdk.core.Utils;
import com.seantone.xsdk.core.XSDK;
import com.seantone.xsdk.plugin.huawei_push.SDK;

import org.json.JSONObject;

import java.util.List;

public class HmsMessageReceiveActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        XSDK.getInstance().getLogger().log("show HmsMessageReceiveActivity");
        if(isTaskRoot())
        {
            XSDK.getInstance().getLogger().log("需要启动新的Activity");
            Utils.runMainActivity(this);
        }

        this.finish();

        Uri data = getIntent().getData();
        XSDK.getInstance().getLogger().log("huawei Intent:" + data.toString());
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
    }
}