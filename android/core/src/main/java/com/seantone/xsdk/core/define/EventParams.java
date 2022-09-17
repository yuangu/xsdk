package com.seantone.xsdk.core.define;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class EventParams {
    @SerializedName("provider")
    public String provider = "";  //必填

    @SerializedName("eventName")
    public String eventName = "";

    @SerializedName("uid")
    public String uid = "";

    @SerializedName("channel")
    public String channel = "";

    @SerializedName("eventArgs")
    public Map<String, Object> eventArgs = new HashMap<>();
}
