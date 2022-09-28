package com.seantone.xsdk.plugin.facebook_account;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.seantone.xsdk.core.XSDK;
import com.seantone.xsdk.core.annotation.ForLogin;
import com.seantone.xsdk.core.define.LoginParams;
import com.seantone.xsdk.core.define.SDKParams;
import com.seantone.xsdk.core.impl.ILogin;
import com.seantone.xsdk.core.impl.ISDK;
import com.seantone.xsdk.core.impl.IXSDKCallback;
import com.seantone.xsdk.facebook_account.FacebookLoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;


@ForLogin(provider = "facebook")
public class SDK implements ISDK, ILogin {
    static String TAG = "XSDK";

    private boolean isInit = false;

    public IXSDKCallback mLoginCallBack = null;

    private static SDK mInstace = null;

    public static SDK getInstance() {
        return mInstace;
    }

    @Override
    public void login(LoginParams params, IXSDKCallback callback) {
        mLoginCallBack = callback;
        XSDK.getInstance().getLogger().log("call facebook Login");
        Context content = XSDK.getInstance().getTopActivity();

        XSDK.getInstance().doInUIThread(() -> {
            Intent intent = new Intent(content,
                    FacebookLoginActivity.class);
            //  intent.putExtra("_data", data);
            ((Activity)content) . startActivity(intent);
        });
    }

    @Override
    public void logout(LoginParams params, IXSDKCallback callback) {
        XSDK.getInstance().doInUIThread(() -> {
            LoginManager.getInstance().logOut();//退出Facebook登录
        });
    }

    @Override
    public void initSDK(SDKParams params, IXSDKCallback callback) {
        XSDK.getInstance().getLogger().log("call facebook initSDK");
        mInstace = this;
        // 该sdk会自动初使化
        callback.onSuccess("{}");
    }
}
