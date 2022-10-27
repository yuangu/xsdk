package com.seantone.xsdk.plugin.google_account;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.seantone.xsdk.core.XSDK;
import com.seantone.xsdk.core.annotation.ForLogin;
import com.seantone.xsdk.core.define.LoginParams;
import com.seantone.xsdk.core.define.SDKParams;
import com.seantone.xsdk.core.impl.ILogin;
import com.seantone.xsdk.core.impl.ISDK;
import com.seantone.xsdk.core.impl.IXSDKCallback;
import com.seantone.xsdk.google_account.GoogleLoginActivity;

@ForLogin(provider = "google")
public class SDK  implements ISDK, ILogin {
    static String TAG = "XSDK";
    Boolean isOldAndroidLogin = false;

    String appId = "";

    public IXSDKCallback mLoginCallBack = null;
    private static SDK mInstace = null;
    public static SDK getInstance() {
        return mInstace;
    }

    public Boolean isUseOldAPi(){
        return isOldAndroidLogin;
    }

    public String getAppId(){
        return this.appId;
    }

    @Override
    public void login(LoginParams params, IXSDKCallback callback) {
        Context content = XSDK.getInstance().getTopActivity();
        mLoginCallBack = callback;

        XSDK.getInstance().doInUIThread(() -> {
            Intent intent = new Intent(content, GoogleLoginActivity.class);
            ((Activity)content) . startActivity(intent);
        });
    }

    @Override
    public void logout(LoginParams params, IXSDKCallback callback) {
        Context content = XSDK.getInstance().getTopActivity();
        XSDK.getInstance().doInUIThread(() -> {
            if(isOldAndroidLogin) {
                GoogleSignIn//退出Google登录
                        .getClient(content, new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build())
                        .signOut();
            }
        });
    }

    @Override
    public void initSDK(SDKParams params, IXSDKCallback callback) {
        mInstace = this;
        this.appId = params.appid;
        // 该SDK会自动初使化
        callback.onSuccess("{}");
    }
}
