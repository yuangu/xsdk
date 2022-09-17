package com.seantone.xsdk.core.define;

import com.google.gson.annotations.SerializedName;

public class SDKParams {
    @SerializedName("provider")
    public String provider = "";  //必填

    @SerializedName("appid")
    public String appid = "";     // appid

    @SerializedName("appkey")
    public String appkey = ""; //非必填

    @SerializedName("service")
    public String service = "";
}
