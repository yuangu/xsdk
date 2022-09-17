package com.seantone.xsdk.facebook_account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.Nullable;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.seantone.xsdk.core.impl.IXSDKCallback;
import com.seantone.xsdk.plugin.facebook_account.SDK;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class FacebookLoginActivity extends Activity {
    private CallbackManager callbackManager;

    private void hideNavigationBar() {
        int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                | View.SYSTEM_UI_FLAG_FULLSCREEN; // hide status bar
        if (android.os.Build.VERSION.SDK_INT >= 19) {
            uiFlags |= 0x00001000;
        } else {
            uiFlags |= View.SYSTEM_UI_FLAG_LOW_PROFILE;
        }
        this.getWindow().getDecorView().setSystemUiVisibility(uiFlags);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideNavigationBar();
        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {//拿到token给后台
                        //TODO do something
                        // mPresenter.thirdLogin(loginResult.getAccessToken().getToken(), 2, inviteCode);
                       // FacebookLoginActivity.this.onSucess(loginResult.getAccessToken().getToken());
                        FacebookLoginActivity.this.onLoginSuccessHandler(loginResult.getAccessToken());

                        finish();
                    }

                    @Override
                    public void onCancel() {
                        FacebookLoginActivity.this.onFail();
                        finish();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        exception.printStackTrace();
                        FacebookLoginActivity.this.onFail();
                        finish();
                    }
                });
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    public void onLoginSuccessHandler(AccessToken accessToken) {
        GraphRequest request = GraphRequest.newMeRequest(accessToken, (object, response) -> {
            final JSONObject jsonObject = new JSONObject();

            if (object != null) {
                String id = object.optString("id");
                String name = object.optString("name");
                String gender = object.optString("gender");
                JSONObject object_pic = object.optJSONObject("picture");
                JSONObject object_data = object_pic.optJSONObject("data");
                String photo = object_data.optString("url");

                try{
                    jsonObject.put("code", accessToken.getToken());
                    jsonObject.put("status", "success");
                    jsonObject.put("id", id);
                    jsonObject.put("name", name);
                    jsonObject.put("gender", gender);
                    jsonObject.put("profilePictureUri", photo);
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
                try{
                    jsonObject.put("status", "failure");
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            this.onSucess(jsonObject);
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,gender,picture.type(large)");
        request.setParameters(parameters);
        request.executeAsync();
    }


    void onSucess(JSONObject ret) {
        new Handler().postDelayed(() -> {
            IXSDKCallback callback = SDK.getInstance().mLoginCallBack;
            if (callback != null) {
                callback.onSuccess(ret.toString());
                SDK.getInstance().mLoginCallBack = null;
            }
        }, 200);
    }

    void onFail() {
        new Handler().postDelayed(() -> {
            IXSDKCallback callback = SDK.getInstance().mLoginCallBack;

            if (callback != null) {
                callback.onFaild("{}");
                SDK.getInstance().mLoginCallBack = null;
            }
        }, 200);
    }
}
