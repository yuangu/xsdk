package com.seantone.xsdk.utils;

import com.seantone.xsdk.core.Utils;
import com.seantone.xsdk.core.define.ShareParams;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;
import com.tencent.mm.opensdk.modelmsg.WXMusicObject;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXVideoObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;

import java.io.IOException;
import java.util.UUID;

public class ShareUtils {
    private interface ShareSceneType {
        public String WXSceneSession = "WXSceneSession";
        public String WXSceneTimeline = "WXSceneTimeline";
        public String WXSceneFavorite = "WXSceneFavorite";
    }

    // 分享文本
    public static BaseReq shareText(ShareParams params) {
        //初始化一个 WXTextObject 对象，填写分享的文本内容
        WXTextObject textObj = new WXTextObject();
        textObj.text = params.summary;

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObj;

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = UUID.randomUUID().toString();
        req.message = msg;

        switch (params.scene) {
            case ShareSceneType.WXSceneTimeline:
                req.scene = SendMessageToWX.Req.WXSceneTimeline;
                break;
            case ShareSceneType.WXSceneFavorite:
                req.scene = SendMessageToWX.Req.WXSceneFavorite;
                break;
            case ShareSceneType.WXSceneSession:
            default:
                req.scene = SendMessageToWX.Req.WXSceneSession;
        }
        //调用api接口，发送数据到微信
        return req;
    }

    // 分享图片
    public static BaseReq shareImage(ShareParams params)
    {
        //初始化 WXImageObject 和 WXMediaMessage 对象
        WXImageObject imgObj = new WXImageObject();
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;

        //设置缩略图
        try {
            msg.thumbData = Utils.readBytes(Utils.getFileInputStream(params.imageUrl));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = UUID.randomUUID().toString();  //相当于请求id
        req.message = msg;

        switch (params.scene) {
            case ShareSceneType.WXSceneTimeline:
                req.scene = SendMessageToWX.Req.WXSceneTimeline;
                break;
            case ShareSceneType.WXSceneFavorite:
                req.scene = SendMessageToWX.Req.WXSceneFavorite;
                break;
            case ShareSceneType.WXSceneSession:
            default:
                req.scene = SendMessageToWX.Req.WXSceneSession;
        }

        return req;
    }

    public static BaseReq shareMusic(ShareParams params)
    {
        WXMusicObject music = new WXMusicObject();
        music.musicUrl= params.mediaUrl;

        WXMediaMessage msg = new WXMediaMessage(music);

        msg.title = params.title;
        msg.description = params.summary;

        //设置缩略图
        try {
            msg.thumbData = Utils.readBytes(Utils.getFileInputStream(params.imageUrl));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = UUID.randomUUID().toString();  //相当于请求id
        req.message = msg;

        switch (params.scene) {
            case ShareSceneType.WXSceneTimeline:
                req.scene = SendMessageToWX.Req.WXSceneTimeline;
                break;
            case ShareSceneType.WXSceneFavorite:
                req.scene = SendMessageToWX.Req.WXSceneFavorite;
                break;
            case ShareSceneType.WXSceneSession:
            default:
                req.scene = SendMessageToWX.Req.WXSceneSession;
        }

        return req;
    }

    public static BaseReq shareVideo(ShareParams params) //初始化一个WXVideoObject，填写url
    {
        WXVideoObject video = new WXVideoObject();

        video.videoUrl = params.mediaUrl;

        //用 WXVideoObject 对象初始化一个 WXMediaMessage 对象
        WXMediaMessage msg = new WXMediaMessage(video);

        msg.title = params.title;
        msg.description = params.summary;
        //设置缩略图
        try {
            msg.thumbData = Utils.readBytes(Utils.getFileInputStream(params.imageUrl));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = UUID.randomUUID().toString();  //相当于请求id
        req.message = msg;

        switch (params.scene) {
            case ShareSceneType.WXSceneTimeline:
                req.scene = SendMessageToWX.Req.WXSceneTimeline;
                break;
            case ShareSceneType.WXSceneFavorite:
                req.scene = SendMessageToWX.Req.WXSceneFavorite;
                break;
            case ShareSceneType.WXSceneSession:
            default:
                req.scene = SendMessageToWX.Req.WXSceneSession;
        }

        return req;
    }

    //分享网页
    public static BaseReq shareWebPage(ShareParams params) {
        //初始化一个WXWebpageObject，填写url
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = params.href;

        //用 WXWebpageObject 对象初始化一个 WXMediaMessage 对象
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = params.title;
        msg.description = params.summary;

        try {
            msg.thumbData = Utils.readBytes(Utils.getFileInputStream(params.imageUrl));
        } catch (IOException e) {
            e.printStackTrace();
        }


        //构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = UUID.randomUUID().toString();  //相当于请求id
        req.message = msg;


        switch (params.scene) {
            case ShareSceneType.WXSceneTimeline:
                req.scene = SendMessageToWX.Req.WXSceneTimeline;
                break;
            case ShareSceneType.WXSceneFavorite:
                req.scene = SendMessageToWX.Req.WXSceneFavorite;
                break;
            case ShareSceneType.WXSceneSession:
            default:
                req.scene = SendMessageToWX.Req.WXSceneSession;
        }
        return req;
    }

    public static BaseReq shareMP(ShareParams params) {
        WXMiniProgramObject miniProgramObj = new WXMiniProgramObject();

        if (params.miniProgram != null) {
            miniProgramObj.webpageUrl = params.miniProgram.webUrl; // 兼容低版本的网页链接
            miniProgramObj.miniprogramType = params.miniProgram.type;
            miniProgramObj.path = params.miniProgram.path;
            miniProgramObj.userName = params.miniProgram.appId;
        }

        WXMediaMessage msg = new WXMediaMessage(miniProgramObj);
        msg.title = params.title;  // 小程序消息title

        try {
            msg.thumbData = Utils.readBytes(Utils.getFileInputStream(params.imageUrl));
        } catch (IOException e) {
            e.printStackTrace();
        }

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = UUID.randomUUID().toString();
        req.message = msg;
        // 目前只支持会话
        switch (params.scene) {
            case ShareSceneType.WXSceneTimeline:
                req.scene = SendMessageToWX.Req.WXSceneTimeline;
                break;
            case ShareSceneType.WXSceneFavorite:
                req.scene = SendMessageToWX.Req.WXSceneFavorite;
                break;
            case ShareSceneType.WXSceneSession:
            default:
                req.scene = SendMessageToWX.Req.WXSceneSession;
        }
        return req;
    }
}
