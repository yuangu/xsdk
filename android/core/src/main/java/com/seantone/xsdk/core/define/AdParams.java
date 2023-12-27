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
        public Float left = null;
        public Float  right = null;
        public Float  top = null;
        public Float  bottom= null;

        public Float size = null;
    }

    @SerializedName("style")
    public Style style = null;
}
