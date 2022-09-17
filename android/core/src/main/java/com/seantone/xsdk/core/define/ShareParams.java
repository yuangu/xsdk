package com.seantone.xsdk.core.define;

import com.google.gson.annotations.SerializedName;

public class ShareParams {
     public class MiniProgram{
          public String appId = "";
          public String path = "";
          public int type = 0;
          public String webUrl = "";
     }

     @SerializedName("provider")
     public String provider = "";  //必填

     @SerializedName("type")
     public int type = -1;

     @SerializedName("title")
     public String title = "";

     @SerializedName("scene")
     public String scene = "";

     @SerializedName("summary")
     public String summary = ""; // 分享内容的摘要

     @SerializedName("href")
     public String href = "";

     @SerializedName("imageUrl")
     public String imageUrl = "";

     @SerializedName("mediaUrl")
     public String mediaUrl = "";

     @SerializedName("miniProgram")
     public MiniProgram miniProgram= null;

     @SerializedName("openCustomerServiceChat")
     public boolean openCustomerServiceChat = false;

     @SerializedName("corpid")
     public String corpid = "";

     @SerializedName("customerUrl")
     public String customerUrl = "";
}
