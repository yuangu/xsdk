package com.seantone.xsdk.core.define;

public class ShareParams {
     public class MiniProgram{
          public String appId = "";
          public String path = "";
          public int type = 0;
          public String webUrl = "";
     }

     public String provider = "";  //必填
     public int type = -1;
     public String title = "";
     public String scene = "";
     public String summary = ""; // 分享内容的摘要
     public String href = "";
     public String imageUrl = "";
     public String mediaUrl = "";
     public MiniProgram miniProgram= null;
     public boolean openCustomerServiceChat = false;
     public String corpid = "";
     public String customerUrl = "";
}
