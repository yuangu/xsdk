package com.seantone.xsdk.core.define;


import com.google.gson.annotations.SerializedName;

public class AdParams {
    @SerializedName("provider")
    public String provider = "";  //必填

    @SerializedName("adpid")
    public String adpid = "";

    @SerializedName("adUnitId")
    public String adUnitId = "";

    public class Style{
        public Integer left = null;
        public Integer  right = null;
        public Integer  top = null;
        public Integer  bottom= null;

        public Integer size = null;
    }

    @SerializedName("style")
    public Style style = null;
}
