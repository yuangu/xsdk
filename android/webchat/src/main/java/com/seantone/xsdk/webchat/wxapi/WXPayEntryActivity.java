package com.seantone.xsdk.webchat.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.seantone.xsdk.plugin.webchat.SDK;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import org.json.JSONObject;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SDK sdk = SDK.getInstance();
        if(sdk != null && sdk.getIWXApi() != null){
            sdk.getIWXApi().handleIntent(getIntent(), this);
        }else{
            Log.e("xsdk", "not get this webchat sdk.");
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);

        SDK sdk = SDK.getInstance();
        if(sdk != null && sdk.getIWXApi() != null) {
            sdk.getIWXApi().handleIntent(intent, this);
        }
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(final BaseResp resp) {
        finish();
        SDK.getInstance().onPayResp(resp);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

