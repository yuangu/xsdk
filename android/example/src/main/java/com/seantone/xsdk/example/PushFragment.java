package com.seantone.xsdk.example;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.seantone.xsdk.core.XSDK;
import com.seantone.xsdk.core.define.SDKParams;
import com.seantone.xsdk.core.impl.IPushCallback;
import com.seantone.xsdk.core.impl.IXSDKCallback;

import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PushFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PushFragment extends Fragment {

    private String SDKName = "";

    public PushFragment() {
        // Required empty public constructor
    }

    public static PushFragment  newInstance(String sdkName) {
        PushFragment fragment = new PushFragment();
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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_push, container, false);
        Button btn =  view .findViewById(R.id.push_button);
        btn.setOnClickListener(_view -> {
            this.onPushButtonClick();
        });
        return view;
    }

    void showRet(String content){
        XSDK.getInstance().getLogger().log(content);
        this.getActivity().runOnUiThread(() -> {
            Toast.makeText(this.getActivity(),  content, Toast.LENGTH_SHORT).show();
        });
    }

    private void onPushButtonClick(){
        XSDK.onPush(new IPushCallback() {
            @Override
            public void onRegisterSucceed(String ret) {
                PushFragment.this.showRet("onRegisterSucceed："+ ret);
            }

            @Override
            public void onNotificationMessageClicked(String ret) {
                PushFragment.this.showRet("onNotificationMessageClicked："+ ret);
                  }

            @Override
            public void onNotificationMessageArrived(String ret) {
                PushFragment.this.showRet("onNotificationMessageArrived："+ ret);
                }
        });

        SDKParams params = new SDKParams();
        params.provider = this.SDKName;
        params.service = "push";
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

        XSDK.onPush(new IPushCallback() {
            @Override
            public void onRegisterSucceed(String ret) {
                PushFragment.this.showRet("onRegisterSucceed："+ ret);
            }

            @Override
            public void onNotificationMessageClicked(String ret) {
                PushFragment.this.showRet("onNotificationMessageClicked："+ ret);
            }

            @Override
            public void onNotificationMessageArrived(String ret) {
                PushFragment.this.showRet("onNotificationMessageArrived："+ ret);
            }
        });

        XSDK.initSDK(jsonParams, new IXSDKCallback() {
            @Override
            public void onSuccess(String ret) {
                //Toast.makeText(MainActivity.this,"onSuccess："+ ret,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFaild(String ret) {
                //Toast.makeText(MainActivity.this,"onFaild："+ ret,Toast.LENGTH_SHORT).show();
            }
        });
    }
}