package com.seantone.xsdk.core.define;

import com.google.gson.annotations.SerializedName;

public class PayParams {
    @SerializedName("provider")
    public String provider = "";  //必填

    @SerializedName("orderInfo")
    public String orderInfo = ""; //支付的参数,json
}
