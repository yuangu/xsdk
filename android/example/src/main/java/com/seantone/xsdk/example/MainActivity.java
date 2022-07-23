package com.seantone.xsdk.example;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainer;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.AssetManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.gson.Gson;
import com.seantone.xsdk.core.ClassUtils;
import com.seantone.xsdk.core.XSDK;
import com.seantone.xsdk.core.define.LoginParams;
import com.seantone.xsdk.core.define.SDKParams;
import com.seantone.xsdk.core.impl.IPushCallback;
import com.seantone.xsdk.core.impl.ISDK;
import com.seantone.xsdk.core.impl.IXSDKCallback;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private String[] sdkArray = {};
    private String SDKName  = "";

    private String[] serviceList = {};
    private String serviceName = "";

    class MySelectedListener implements AdapterView.OnItemSelectedListener{
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
             MainActivity.this.SDKName = sdkArray[i];
            initServiceSelector();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }

    void initConfig(){
        AssetManager assetManager = this.getApplicationContext().getAssets();
        try {
            InputStream inputStream = assetManager.open("config.yaml");
            ObjectMapper om = new ObjectMapper(new YAMLFactory());
            Map config =  om.readValue(inputStream, Map.class);
            Config.mConfig = config;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        XSDK.getInstance().init(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.initConfig();

        Iterator iterator =  Config.mConfig.keySet().iterator();
        List<String> list = new ArrayList();

        while (iterator.hasNext()) {
            Object key = iterator.next();
            list.add((String) key);
        }

        sdkArray = list.toArray(new String[list.size()]);
        this.initView();
    }

    void initServiceSelector(){
        List<String> list = new ArrayList();

        if(Config.mConfig.containsKey(SDKName))
        {
            Map<String, Object>  tmp = ( Map<String, Object> )Config.mConfig.get(SDKName);
            Iterator iterator =  tmp.keySet().iterator();

            while (iterator.hasNext()) {
                Object key = iterator.next();
                list.add((String) key);
            }
        }

        serviceList = list.toArray(new String[list.size()]);

        ArrayAdapter<String> starAdapter = new ArrayAdapter<String>(this,R.layout.item_select, serviceList);
        Spinner sp = this.findViewById(R.id.spinner2);
        sp.setPrompt("请选择Services");
        sp.setAdapter(starAdapter);
        //设置下拉框默认的显示第一项
        sp.setSelection(0);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                serviceName = serviceList[i];

                if(serviceName.equals("oauth")){
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    LoginFragment loginFragment = LoginFragment.newInstance(MainActivity.this.SDKName );
                    ft.replace(R.id.fragment, loginFragment);
                    ft.commit();
                }else if(serviceName.equals("push")){
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    PushFragment pushFragment =PushFragment.newInstance(MainActivity.this.SDKName );
                    ft.replace(R.id.fragment, pushFragment);
                    ft.commit();
                }else if(serviceName.equals("ad")){
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    AdFragment adFragment = AdFragment.newInstance(MainActivity.this.SDKName );
                    ft.replace(R.id.fragment, adFragment);
                    ft.commit();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void  initView(){
        ArrayAdapter<String> starAdapter = new ArrayAdapter<String>(this,R.layout.item_select, sdkArray);
        Spinner sp = this.findViewById(R.id.spinner);
        //设置下拉框的标题，不设置就没有难看的标题了
        sp.setPrompt("请选择SDK");
        //设置下拉框的数组适配器
        sp.setAdapter(starAdapter);
        //设置下拉框默认的显示第一项
        sp.setSelection(0);
        //给下拉框设置选择监听器，一旦用户选中某一项，就触发监听器的onItemSelected方法
        sp.setOnItemSelectedListener(new MySelectedListener());

    }
}