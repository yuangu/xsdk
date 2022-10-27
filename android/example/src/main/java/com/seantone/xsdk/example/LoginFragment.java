package com.seantone.xsdk.example;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.seantone.xsdk.core.XSDK;
import com.seantone.xsdk.core.define.LoginParams;
import com.seantone.xsdk.core.define.SDKParams;
import com.seantone.xsdk.core.impl.IXSDKCallback;

import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {
    private String SDKName = "";

    public LoginFragment() {
    }

    public static LoginFragment newInstance(String sdkName) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString("SDKNAME", sdkName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.SDKName = getArguments().getString("SDKNAME");
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        Button loginInitBtn = (Button) getActivity().findViewById(R.id.login_init_button );
        loginInitBtn.setOnClickListener(view -> {
          this.onInitButtonClick();
        });

        Button loginDoBtn = (Button)  getActivity().findViewById(R.id.login_do_button);
        loginDoBtn.setOnClickListener(view -> {
            this.onLoginButtonClick();
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    private void onInitButtonClick(){
        SDKParams params = new SDKParams();
        params.provider = this.SDKName;
        params.service = "oauth";

        Map tmp =  (Map) Config.mConfig.get(this.SDKName);
        Map tmp2 =   (Map) tmp.get(params.service);

        if(tmp2.containsKey("appid"))
        {
            params.appid = String.valueOf( tmp2.get("appid"));
        }

        if(tmp2.containsKey("appkey"))
        {
            params.appkey =  String.valueOf( tmp2.get("appkey"));
        }

        Gson gson = new Gson();
        String jsonParams = gson.toJson(params);

        XSDK.initSDK(jsonParams, new IXSDKCallback() {
            @Override
            public void onSuccess(String ret) {
                XSDK.getInstance().getLogger().log("onRegisterSucceed："+ ret);
                LoginFragment.this.getActivity().runOnUiThread(() -> {
                    Toast.makeText(LoginFragment.this.getActivity(), "onSuccess：" + ret, Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onFaild(String ret) {
                XSDK.getInstance().getLogger().log("onFaild："+ ret);
                LoginFragment.this.getActivity().runOnUiThread(() -> {
                    Toast.makeText(LoginFragment.this.getActivity(), "onFaild："+ ret, Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    private void onLoginButtonClick(){
        LoginParams params = new LoginParams();
        params.provider = this.SDKName;
        Gson gson = new Gson();
        String jsonParams = gson.toJson(params);

        XSDK.login(jsonParams, new IXSDKCallback() {
            @Override
            public void onSuccess(String ret) {
                XSDK.getInstance().getLogger().log("onSuccess："+ ret);
                LoginFragment.this.getActivity().runOnUiThread(() -> {
                    Toast.makeText(LoginFragment.this.getActivity(), "onSuccess：" + ret, Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onFaild(String ret) {
                XSDK.getInstance().getLogger().log("onFaild："+ ret);
                LoginFragment.this.getActivity().runOnUiThread(() -> {
                    Toast.makeText(LoginFragment.this.getActivity(), "onFaild：" + ret, Toast.LENGTH_SHORT).show();
                });
            }
        });
    }
}