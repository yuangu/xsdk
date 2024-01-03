package com.seantone.xsdk.umeng_event;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.seantone.xsdk.core.XSDK;

public class Utils {
    // 获取渠道id
    public static String getChannelId(){
        Context content = XSDK.getInstance().getTopActivity();
        Context appContext = content.getApplicationContext();
        String channelId = "unknown_channel";
        try {
            ApplicationInfo applicationInfo = appContext.getPackageManager().
                    getApplicationInfo(appContext.getPackageName(), PackageManager.GET_META_DATA);
            if (applicationInfo != null) {
                if (applicationInfo.metaData != null) {
                    channelId = applicationInfo.metaData.getString("UMENG_CHANNEL");
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            channelId = "unknown_channel";
        }

        return channelId;
    }
}
