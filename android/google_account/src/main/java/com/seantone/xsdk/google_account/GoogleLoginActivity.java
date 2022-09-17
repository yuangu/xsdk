package com.seantone.xsdk.google_account;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.Task;
import com.seantone.xsdk.core.impl.IXSDKCallback;
import com.seantone.xsdk.plugin.google_account.SDK;

import org.json.JSONException;
import org.json.JSONObject;

public class GoogleLoginActivity extends AppCompatActivity {
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

        ActivityResultLauncher<Intent> signInClient = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            //FirebaseAuth.getInstance().signInWithCredential(GoogleAuthProvider.getCredential(GoogleSignIn.getSignedInAccountFromIntent(result.getData()).getResult(ApiException.class).getIdToken(), null));null

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                this.onLoginSuccessHandler(account);
            } catch (ApiException e) {
                this.onFail();
                Log.w("xsdk", "signInResult:failed code=" + e.getStatusCode() + " msg:" + e.getMessage());
                e.printStackTrace();
            }finally {
                finish();
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()//请求邮箱
                .requestProfile()//请求用户的个人资料信息
                .requestId()//请求用户ID
                //.requestIdToken("976430636726-o2ktfkoa2bagkoalvc9urpf7vpblo2a6.apps.googleusercontent.com")//Json文件里面的值
                .requestScopes(new Scope(Scopes.DRIVE_APPFOLDER))//获取AuthCode
                //.requestServerAuthCode("976430636726-o2ktfkoa2bagkoalvc9urpf7vpblo2a6.apps.googleusercontent.com")
                .build();

        signInClient.launch(GoogleSignIn.getClient(this, gso).getSignInIntent());
        // this.startActivityForResult(GoogleSignIn.getClient(this, gso).getSignInIntent(), 0x466);
    }

    public void onLoginSuccessHandler(GoogleSignInAccount  account) {
        final JSONObject jsonObject = new JSONObject();

            try{
                jsonObject.put("code", account.getServerAuthCode());
                jsonObject.put("status", "success");
                jsonObject.put("id", account.getId());
                jsonObject.put("name", account.getDisplayName());
               // jsonObject.put("gender", account.);
                jsonObject.put("profilePictureUri", account.getPhotoUrl());
            }catch (JSONException e) {
                e.printStackTrace();
            }

        this.onSucess(jsonObject);

    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//
//        if (requestCode == 0x466) {
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            try {
//                GoogleSignInAccount account = task.getResult(ApiException.class);
//                if (account != null && !TextUtils.isEmpty(account.getServerAuthCode())) {//拿到code给后台
//                    //TODO do something
//                    //  mPresenter.thirdLogin(account.getServerAuthCode(), 1, inviteCode);
//                    this.onSucess(account.getServerAuthCode());
//                } else {
//                    this.onFail();
//                }
//
//            } catch (ApiException e) {
//                Log.w("xsdk", "signInResult:failed code=" + e.getStatusCode());
//                this.onFail();
//                e.printStackTrace();
//            }finally {
//                finish();
//            }
//        }
//    }

    void onSucess( JSONObject ret) {
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
