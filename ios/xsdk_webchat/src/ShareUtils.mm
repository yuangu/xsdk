//
//  ShareUtils.mm
//  webchat
//
//  Created by yuangu on 2022/5/24.
//
#import <ShareUtils.h>
#import "WXApi.h"
#include "coreUtils.h"

#define _WXSceneSession   @"WXSceneSession"
#define _WXSceneTimeline  @"WXSceneTimeline"
#define _WXSceneFavorite  @"WXSceneFavorite"

@implementation ShareUtils 

+(SendMessageToWXReq*) shareText:(ShareParams*) params {
    WXTextObject* textObj = [[WXTextObject alloc] init];
    [textObj setContentText:params.summary];

    
    SendMessageToWXReq *req = [[SendMessageToWXReq alloc] init];
    req.bText = YES;
    req.text = params.summary;
    
    if([_WXSceneSession isEqualToString:params.scene] )
    {
        req.scene = WXSceneSession;
    }else  if([_WXSceneTimeline isEqualToString:params.scene] )
    {
        req.scene = WXSceneTimeline;
    }else  if([_WXSceneFavorite isEqualToString:params.scene] )
    {
        req.scene = WXSceneFavorite;
    }
    return req;
}

+(SendMessageToWXReq*) shareImage:(ShareParams*) params
{
    WXImageObject* imgObj = [[WXImageObject alloc] init];
  
    WXMediaMessage* msg = [[WXMediaMessage alloc] init];
    msg.mediaObject = imgObj;
    UIImage* image = [CoreUtils getUIImageFromURL:params.imageUrl];
    msg.thumbData = UIImageJPEGRepresentation( image , 0.8);
    
    SendMessageToWXReq *req = [[SendMessageToWXReq alloc] init];
    req.bText = NO;
    req.message = msg;
    
    if([_WXSceneSession isEqualToString:params.scene] )
    {
        req.scene = WXSceneSession;
    }else  if([_WXSceneTimeline isEqualToString:params.scene] )
    {
        req.scene = WXSceneTimeline;
    }else  if([_WXSceneFavorite isEqualToString:params.scene] )
    {
        req.scene = WXSceneFavorite;
    }
    return req;
    
}

+(SendMessageToWXReq*) shareMusic:(ShareParams*) params
{
    WXMusicObject* music = [[WXMusicObject alloc] init];
    music.musicUrl= params.mediaUrl;
    
    WXMediaMessage* msg = [[WXMediaMessage alloc] init];
    msg.mediaObject = music;
    msg.title = params.title;
    msg.description = params.summary;
    
    //设置缩略图
    UIImage* image = [CoreUtils getUIImageFromURL:params.imageUrl];
    msg.thumbData = UIImageJPEGRepresentation( image , 0.8);
    
    SendMessageToWXReq *req = [[SendMessageToWXReq alloc] init];
    req.bText = NO;
    req.message = msg;
    
    if([_WXSceneSession isEqualToString:params.scene] )
    {
        req.scene = WXSceneSession;
    }else  if([_WXSceneTimeline isEqualToString:params.scene] )
    {
        req.scene = WXSceneTimeline;
    }else  if([_WXSceneFavorite isEqualToString:params.scene] )
    {
        req.scene = WXSceneFavorite;
    }
    return req;
    
}

+(SendMessageToWXReq*) shareVideo:(ShareParams*) params
{
    WXVideoObject* video  = [[WXVideoObject alloc] init];
    video.videoUrl= params.mediaUrl;
    
    WXMediaMessage* msg = [[WXMediaMessage alloc] init];
    msg.mediaObject = video;
    msg.title = params.title;
    msg.description = params.summary;
    
    //设置缩略图
    UIImage* image = [CoreUtils getUIImageFromURL:params.imageUrl];
    msg.thumbData = UIImageJPEGRepresentation( image , 0.8);
    
    SendMessageToWXReq *req = [[SendMessageToWXReq alloc] init];
    req.bText = NO;
    req.message = msg;
    
    if([_WXSceneSession isEqualToString:params.scene] )
    {
        req.scene = WXSceneSession;
    }else  if([_WXSceneTimeline isEqualToString:params.scene] )
    {
        req.scene = WXSceneTimeline;
    }else  if([_WXSceneFavorite isEqualToString:params.scene] )
    {
        req.scene = WXSceneFavorite;
    }
    return req;
    
}

+(SendMessageToWXReq*) shareWebPage:(ShareParams*) params
{
    WXWebpageObject* webpage  = [[WXWebpageObject alloc] init];
    webpage.webpageUrl = params.href;
    
    WXMediaMessage* msg = [[WXMediaMessage alloc] init];
    msg.mediaObject = webpage;
    msg.title = params.title;
    msg.description = params.summary;
    
    //设置缩略图
    UIImage* image = [CoreUtils getUIImageFromURL:params.imageUrl];
    msg.thumbData = UIImageJPEGRepresentation( image , 0.8);
    
    SendMessageToWXReq *req = [[SendMessageToWXReq alloc] init];
    req.bText = NO;
    req.message = msg;
    
    if([_WXSceneSession isEqualToString:params.scene] )
    {
        req.scene = WXSceneSession;
    }else  if([_WXSceneTimeline isEqualToString:params.scene] )
    {
        req.scene = WXSceneTimeline;
    }else  if([_WXSceneFavorite isEqualToString:params.scene] )
    {
        req.scene = WXSceneFavorite;
    }
    return req;
}

+(SendMessageToWXReq*) shareMP:(ShareParams*) params
{
    WXMiniProgramObject* miniProgramObj  = [[ WXMiniProgramObject alloc] init];
  
   if (params.miniProgram != nil) {
       miniProgramObj.webpageUrl = params.miniProgram.webUrl; // 兼容低版本的网页链接
       miniProgramObj.miniProgramType =( WXMiniProgramType) params.miniProgram.type.intValue;
       miniProgramObj.path = params.miniProgram.path;
       miniProgramObj.userName = params.miniProgram.appId;
   }
    
    WXMediaMessage* msg = [[WXMediaMessage alloc] init];
    msg.mediaObject = miniProgramObj;
    msg.title = params.title;
    
    //设置缩略图
    UIImage* image = [CoreUtils getUIImageFromURL:params.imageUrl];
    msg.thumbData = UIImageJPEGRepresentation( image , 0.8);
    
    SendMessageToWXReq *req = [[SendMessageToWXReq alloc] init];
    req.bText = NO;
    req.message = msg;
    
    if([_WXSceneSession isEqualToString:params.scene] )
    {
        req.scene = WXSceneSession;
    }else  if([_WXSceneTimeline isEqualToString:params.scene] )
    {
        req.scene = WXSceneTimeline;
    }else  if([_WXSceneFavorite isEqualToString:params.scene] )
    {
        req.scene = WXSceneFavorite;
    }
    return req;
}

@end

