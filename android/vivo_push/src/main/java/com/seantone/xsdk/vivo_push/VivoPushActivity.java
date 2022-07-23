package com.seantone.xsdk.vivo_push;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.seantone.xsdk.core.Utils;
import com.seantone.xsdk.core.XSDK;

import org.json.JSONException;
import org.json.JSONObject;

public class VivoPushActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(XSDK.getInstance().getPushCallBack() == null) return;
        Intent intent = getIntent();

        XSDK.getInstance().getLogger().log("show VivoPushActivity");
        if(isTaskRoot())
        {
            XSDK.getInstance().getLogger().log("需要启动新的Activity");
            Utils.runMainActivity(this);
        }

        long messageId = intent.getLongExtra("vivo_push_messageId",0l);

        //获取单个自定义透传参数值
        JSONObject extra = new JSONObject();
        try {
            extra.put("messageId", messageId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String log = "";
        if (null != intent) {
            String key1 = intent.getStringExtra("key1");
            int key2 = intent.getIntExtra("key2", -1);
            try {
                extra.put("key1", key1);
                extra.put("key2", Integer.valueOf(key2));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //遍历所有自定义参数
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            for (String key : bundle.keySet()) {
                if (!TextUtils.isEmpty(key)) {
                    try {
                        extra.put(key, bundle.get(key));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }finally {
                        continue;
                    }
                }
            }
        }
        XSDK.getInstance().getPushCallBack().onNotificationMessageClicked(extra.toString());
    }
}
