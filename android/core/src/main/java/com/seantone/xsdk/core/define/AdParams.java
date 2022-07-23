package com.seantone.xsdk.core.define;



public class AdParams {
    public String provider = "";  //必填
    public String adpid = "";
    public String adUnitId = "";

    public class Style{
        public Integer left = null;
        public Integer  right = null;
        public Integer  top = null;
        public Integer  bottom= null;

        public Integer size = null;
    }
    public Style style = null;
}
