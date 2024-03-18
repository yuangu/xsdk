//
//  WebChatSDK.m
//  xsdk_webchat
//
//  Created by yuangu on 2021/6/24.
//

#import <WebChatSDK.h>
#import "WXApi.h"
#import "coreUtils.h"
#import "ShareUtils.h"

struct __AutoWebchatResg{
    __AutoWebchatResg(){
        id sdkObj = [[WebChatSDK alloc]init];
        
        [[XSDK getInstance] forShare:@"webchat" :sdkObj];
#if HAS_WEBCHAT_PAY == 1
        [[XSDK getInstance] forPay:@"webchat" :sdkObj];
#endif
        [[XSDK getInstance] forLogin:@"webchat" :sdkObj];
    }
} __AutoWebchatResg;


@implementation WebChatSDK{
    BOOL isInitWebChatSDK;
}


-(id) init{
    if(self=[super init])
    {
//        [[XSDK getInstance] addAppDelegate:self];
        [[XSDKModuleManager sharedInstance] addModule:self];
        
        self->mCallBackMap = [NSMutableDictionary  dictionaryWithCapacity:0];
        self->isInitWebChatSDK = false;
    }
    return self;
}


-(void) initSDK:(SDKParams*) params :(id<IXSDKCallback>)callBack
{
    dispatch_async(dispatch_get_main_queue(), ^{
        if(self->isInitWebChatSDK)
        {
            if(callBack != NULL){
                [callBack onSuccess:@""];
            }
         
            return;
        }
           
        
        // 打印日志，方便查看bug
        [WXApi startLogByLevel:WXLogLevelDetail logBlock:^(NSString *log) {
            NSLog(@"WeChatSDK: %@", log);
        }];
        
        NSDictionary *dict = [[NSBundle mainBundle] infoDictionary];
        if([[dict allKeys] containsObject:@"UNIVERSAL_LINK"]){
            NSString* UNIVERSAL_LINK = dict[@"UNIVERSAL_LINK"];
            [WXApi registerApp:params.appid universalLink:UNIVERSAL_LINK];
            if(callBack != NULL){
                [callBack onSuccess:@""];
            }
        }
        else{
            NSLog(@"请在info.plist增加UNIVERSAL_LINK参数");
            if(callBack != NULL){
                [callBack onFaild:@""];
            }
        }
    });
}


-(void) login:(LoginParams*) params :(id<IXSDKCallback>)callBack{
    SendAuthReq* req =[[SendAuthReq alloc]init];
    req.scope = @"snsapi_userinfo";
    req.state = [[NSUUID UUID] UUIDString];
    
    if ([WXApi isWXAppInstalled]) {
        [WXApi sendReq:req completion:^(BOOL isScuess){
            if(isScuess){
                [self->mCallBackMap setObject:callBack forKey:req.state];
            }
        } ];
    }else{
        dispatch_async(dispatch_get_main_queue(), ^{
            UIViewController *rootViewController = [UIApplication sharedApplication].keyWindow.rootViewController;
            [WXApi sendAuthReq:req viewController:rootViewController delegate:self completion:^(BOOL isScuess){
                NSLog(@"wechat login success = %@", isScuess ? @"yes" : @"no");
            } ];
        });
    }
}

-(void) share:(ShareParams*) params :(id<IXSDKCallback>)callBack
{
    SendMessageToWXReq* req = nil;
           // 分享文本
    if ([params.type  isEqual: @1]) {
       req = [ShareUtils shareText:params];
    } else if ([params.type  isEqual: @2]) {
       req = [ShareUtils shareImage:params];
    }else if ([params.type  isEqual: @3]) {
       req = [ShareUtils shareMusic:params];
    }else if ([params.type  isEqual: @4]){
       req = [ShareUtils shareVideo:params];
   }
    else if ([params.type  isEqual: @5]) {
       req = [ShareUtils shareMP:params];
    } else if ([params.type  isEqual: @6]) {
       req = [ShareUtils shareWebPage:params];
   }
    
    if (req != nil) {
        [self->mCallBackMap setObject:callBack forKey:@"share"];
        [WXApi sendReq:req completion:^(BOOL isScuess){
            if(!isScuess){
                [self->mCallBackMap removeObjectForKey:@"share"];
                [[XSDK getInstance] doInCallBackThread:^{
                    [callBack onFaild:@""];
                }];
                NSLog(@"分享失败");
            }
        } ];
    }
    
}

#if HAS_WEBCHAT_PAY == 1
-(void) pay:(PayParams*) params :(id<IXSDKCallback>)callBack{
    //      PayReq* req             = [[PayReq alloc] init];
    //
    //      req.partnerId           = [dict objectForKey:@"partnerid"];
    //      req.prepayId            = [dict objectForKey:@"prepayid"];
    //      req.nonceStr            = [dict objectForKey:@"noncestr"];
    //      req.timeStamp           = [[dict objectForKey:@"timestamp"] intValue];
    //      req.package             = [dict objectForKey:@"package"];
    //      req.sign                = [dict objectForKey:@"sign"];
    //      [WXApi sendReq:req completion:^(BOOL isScuess){
    //
    //      } ];
}

// 有些SDK需要主动通知支付结束，防止漏单
-(void)finishPay:(NSDictionary*)args
{
    
}
#endif

- (void)onReq:(BaseReq*)req{
    
}

- (void)onResp:(BaseResp*)resp{
    if([resp isKindOfClass:[SendAuthResp class]])
    {
        SendAuthResp* _resp = ( SendAuthResp*)resp;
        id<IXSDKCallback> callBack = [self->mCallBackMap objectForKey:_resp.state];
        if(callBack == nil){
            return;
        }else{
            [self->mCallBackMap removeObjectForKey:_resp.state];
        }
        
        if(_resp.errCode == WXSuccess )
        {
            NSDictionary *data = [[NSDictionary alloc] initWithObjectsAndKeys:_resp.code, @"code", nil];
            
            NSData *jsonData = [NSJSONSerialization dataWithJSONObject:data options:NSJSONWritingPrettyPrinted error:nil];
            
            NSString* ret = [[NSString alloc] initWithData:jsonData encoding:NSUTF8StringEncoding];
            
            [[XSDK getInstance] doInCallBackThread:^{
                [callBack onSuccess:[NSString stringWithFormat:@"%@", ret]];
            }];
        }
        
        else if(_resp.errCode  == WXErrCodeUserCancel ){
            NSDictionary *data = [[NSDictionary alloc] initWithObjectsAndKeys:@-2, @"errCode",@"用户取消", @"errMsg", nil];
            
            NSData *jsonData = [NSJSONSerialization dataWithJSONObject:data options:NSJSONWritingPrettyPrinted error:nil];
            
            NSString* ret = [[NSString alloc] initWithData:jsonData encoding:NSUTF8StringEncoding];
    
            [[XSDK getInstance] doInCallBackThread:^{
                [callBack onFaild:[NSString stringWithFormat:@"%@", ret]];
            }];
        }
        
        else  if(_resp.errCode  == WXErrCodeAuthDeny){
            NSDictionary *data = [[NSDictionary alloc] initWithObjectsAndKeys:@-4, @"errCode",@"用户拒绝", @"errMsg", nil];
            
            NSData *jsonData = [NSJSONSerialization dataWithJSONObject:data options:NSJSONWritingPrettyPrinted error:nil];
            
            NSString* ret = [[NSString alloc] initWithData:jsonData encoding:NSUTF8StringEncoding];
            [[XSDK getInstance] doInCallBackThread:^{
                [callBack onFaild:[NSString stringWithFormat:@"%@", ret]];
            }];
        }
    }
    
#if HAS_WEBCHAT_PAY == 1
    else if([resp isKindOfClass:[PayResp class]]){
        //支付返回结果，实际支付结果需要去微信服务器端查询
        //           NSString *strMsg,*strTitle = [NSString stringWithFormat:@"支付结果"];
        //
        //            PayResp* _resp = ( PayResp*)resp;
        //            if(self->payCallBack == nil)
        //            {
        //                return;
        //            }
        //
        //
        //            if(_resp.errCode == WXSuccess )
        //            {
        //                NSDictionary *data = [[NSDictionary alloc] initWithObjectsAndKeys: nil];
        //                NSData *jsonData = [NSJSONSerialization dataWithJSONObject:data options:NSJSONWritingPrettyPrinted error:nil];
        //                NSString* ret = [[NSString alloc] initWithData:jsonData encoding:NSUTF8StringEncoding];
        //                [self->payCallBack onSucess:ret];
        //            }
        //
        //            else if(_resp.errCode  == WXErrCodeUserCancel ){
        //                [self->payCallBack onError:-2 :@"取消支付"];
        //            }
        //            else{
        //                [self->payCallBack onError:-2 :@"其它原因支付失败"];
        //            }
        //
        //            if(_resp.errCode != WXSuccess){
        //                strMsg = [NSString stringWithFormat:@"支付结果：失败！retcode = %d, retstr = %@", resp.errCode,resp.errStr];
        //                NSLog(@"错误，retcode = %d, retstr = %@", resp.errCode,resp.errStr);
        //            }
    }
#endif
    else if([resp isKindOfClass:[SendMessageToWXResp class]]){
        SendMessageToWXResp* _resp = (SendMessageToWXResp*)resp;
        
        id<IXSDKCallback> callBack = [self->mCallBackMap objectForKey:@"share"];
        if(callBack == nil){
            return;
        }else{
            [self->mCallBackMap removeObjectForKey:@"share"];
        }
        
        [[XSDK getInstance] doInCallBackThread:^{
            [callBack onSuccess:[NSString stringWithFormat:@"%@", @"{}"]];
        }];
    }
}

- (BOOL)application:(UIApplication *)application handleOpenURL:(NSURL *)url{
    return  [WXApi handleOpenURL:url delegate:self];
}

- (BOOL)application:(UIApplication *)application openURL:(nonnull NSURL *)url options:(nonnull NSDictionary<UIApplicationOpenURLOptionsKey,id> *)options {
    return [WXApi handleOpenURL:url delegate:self];
}

- (BOOL)application:(UIApplication *)application continueUserActivity:(NSUserActivity *)userActivity restorationHandler:(void(^)(NSArray<id<UIUserActivityRestoring>> * __nullable restorableObjects))restorationHandler API_AVAILABLE(ios(8.0)){
    return [WXApi handleOpenUniversalLink:userActivity delegate:self];
}
@end
