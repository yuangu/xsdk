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
import com.seantone.xsdk.core.define.AdParams;
import com.seantone.xsdk.core.define.SDKParams;
import com.seantone.xsdk.core.impl.IXSDKCallback;
import com.seantone.xsdk.core.impl.ad.IBannerAdEventCallBack;
import com.seantone.xsdk.core.impl.ad.IInterstitialAd;
import com.seantone.xsdk.core.impl.ad.IInterstitialAdEventCallBack;
import com.seantone.xsdk.core.impl.ad.IRewardedVideoAd;
import com.seantone.xsdk.core.impl.ad.IRewardedVideoAdEventCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Map;


public class AdFragment extends Fragment {
    private String SDKName = "";

    public static AdFragment newInstance(String sdkName) {
        AdFragment fragment = new AdFragment();
        Bundle args = new Bundle();
        args.putString("SDKNAME", sdkName);
        fragment.setArguments(args);
        return fragment;
    }

    public AdFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.SDKName = getArguments().getString("SDKNAME");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_ad, container, false);
        Button btn =  view.findViewById(R.id.init_SDK_button);
        btn.setOnClickListener(view1 -> {
            this.initSDK();
        });

        Button bannerBtn =  view.findViewById(R.id.banner_Btn);
        bannerBtn.setOnClickListener(view1 -> {
            this.showBanner();
        });

        Button interstitialAdBtn =  view.findViewById(R.id.interstitialAd_button);
        interstitialAdBtn.setOnClickListener(view1 -> {
            this.showInterstitialAd();
        });

        Button rewardBtton =  view.findViewById(R.id.rewardBtton);
        rewardBtton .setOnClickListener(view1 -> {
            this.showrewardAd();
        });


        return view;
    }

    void initSDK(){
        SDKParams params = new SDKParams();
        params.provider = this.SDKName;
        params.service = "ad";

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
                AdFragment.this.getActivity().runOnUiThread(() -> {
                    Toast.makeText(AdFragment.this.getActivity(), "onSuccess：" + ret, Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onFaild(String ret) {
                XSDK.getInstance().getLogger().log("onFaild："+ ret);
                AdFragment.this.getActivity().runOnUiThread(() -> {
                    Toast.makeText(AdFragment.this.getActivity(), "onFaild："+ ret, Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    IInterstitialAd  mIInterstitialAd = null;
    void showInterstitialAd(){
        Map<String, Object>  tmp = ( Map<String, Object> )Config.mConfig.get(SDKName);
        Map<String, Object>  tmp2 =( Map<String, Object> ) tmp.get("ad");

        JSONObject object = new JSONObject();
        try {
            object.put("provider",this.SDKName);
            object.put("adUnitId",tmp2.get("interstitial"));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        mIInterstitialAd = XSDK.createInterstitialAd(object.toString(), new IInterstitialAdEventCallBack() {
            @Override
            public void onLoad(String ret) {
                XSDK.getInstance().getLogger().log(ret);
                mIInterstitialAd.show("");
            }

            @Override
            public void onError(String ret) {
                XSDK.getInstance().getLogger().log(ret);
            }

            @Override
            public void onClose() {
                XSDK.getInstance().getLogger().log("IInterstitialAd close");
            }
        });
    }

    IRewardedVideoAd rewardedVideoAd;
    void showrewardAd()
    {
        Map<String, Object>  tmp = ( Map<String, Object> )Config.mConfig.get(SDKName);
        Map<String, Object>  tmp2 =( Map<String, Object> ) tmp.get("ad");

        JSONObject object = new JSONObject();
        try {
            object.put("provider",this.SDKName);
            object.put("adUnitId",tmp2.get("reward"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        rewardedVideoAd =  XSDK.createRewardedVideoAd(object.toString(), new IRewardedVideoAdEventCallBack() {
            @Override
            public void onError(String ret) {
                XSDK.getInstance().getLogger().log(ret);
            }

            @Override
            public void onLoad(String ret) {
                rewardedVideoAd.show();
            }

            @Override
            public void onReward() {
                XSDK.getInstance().getLogger().log("onReward, 给奖励");
            }

            @Override
            public void onClose() {
                XSDK.getInstance().getLogger().log("onClose, 视频关闭了");
            }
        });
    }

    void showBanner(){
//        AdParams params = new AdParams();
//        params.provider =  this.SDKName;
//
//
//
        Map<String, Object>  tmp = ( Map<String, Object> )Config.mConfig.get(SDKName);
        Map<String, Object>  tmp2 =( Map<String, Object> ) tmp.get("ad");

//        params.adUnitId = (String) tmp2.get("banner");

        JSONObject object = new JSONObject();
        try {
            object.put("provider",this.SDKName );
            object.put("adUnitId",tmp2.get("banner"));
            JSONObject style  = new JSONObject();
            style.put("bottom", 0);
            //object.put("style",style);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        XSDK.createBannerAd(object.toString(), new IBannerAdEventCallBack() {
            @Override
            public void onLoad(String ret) {
                XSDK.getInstance().getLogger().log(ret);
            }

            @Override
            public void onHide() {

            }

            @Override
            public void onError(String ret) {
                XSDK.getInstance().getLogger().log(ret);
            }
        });
    }
}