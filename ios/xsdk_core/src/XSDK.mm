//
//  XSDK.mm
//  core
//  SDK管理类
//  Created by yuangu on 2021/6/23.
//

#import <Foundation/Foundation.h>
#include "XSDK.h"

@implementation XSDK{
    NSMutableDictionary* mShareSDKMap;
    NSMutableDictionary* mPaySDKMap;
    NSMutableDictionary* mLoginSDKMap;
    NSMutableDictionary* mPushSDKMap;
    NSMutableDictionary* mADSDKMap;
    NSMutableDictionary* mEventSDKMap;
    
    id<IPushCallback> mPushCallback;
}

static XSDK*  mInstace = nil;

+ (XSDK*)getInstance
{
    @synchronized(self.class)
    {
        if (mInstace == nil) {
            mInstace = [[self.class alloc] init];
        }
        
        return mInstace;
    }
}

-(id)init
{
    if(self=[super init])
    {
        self->mShareSDKMap = [NSMutableDictionary  dictionaryWithCapacity:0];
        self->mPaySDKMap = [NSMutableDictionary  dictionaryWithCapacity:0];
        self->mLoginSDKMap = [NSMutableDictionary  dictionaryWithCapacity:0];
        self->mPushSDKMap = [NSMutableDictionary  dictionaryWithCapacity:0];
        self-> mADSDKMap = [NSMutableDictionary  dictionaryWithCapacity:0];;
        self-> mEventSDKMap = [NSMutableDictionary  dictionaryWithCapacity:0];;
    }
    return self;
}

-(void) doInUIThread:(dispatch_block_t) block
{
    if (strcmp(dispatch_queue_get_label(DISPATCH_CURRENT_QUEUE_LABEL), dispatch_queue_get_label(dispatch_get_main_queue())) == 0) {
                block();
    } else {
                dispatch_async(dispatch_get_main_queue(),  block);
    }
}


-(void) doInCallBackThread:(dispatch_block_t) block
{
    dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), block);
}

-(NSString*) warpNSString:(NSString*)str{
    return  [NSString stringWithFormat:@"%@", str];
}

-(void) initSDK:(SDKParams*) params :(id<IXSDKCallback>)callBack
{
    [self doInUIThread: ^{
       
        if (params.service == @"oauth") {

            id<ISDK> object = [self->mLoginSDKMap objectForKey:params.provider];
            if(object != nil){
                [object initSDK:params:callBack];
                return;
            }
            
        } else if (params.service == @"share") {
          
            id<ISDK> object = [self->mShareSDKMap objectForKey:params.provider];
            if(object != nil){
                [object initSDK:params:callBack];
                return;
            }
        } else if (params.service == @"payment") {
            id<ISDK> object = [self->mPaySDKMap objectForKey:params.provider];
            if(object != nil){
                [object initSDK:params:callBack];
                return;
            }
        } else if (params.service == @"push") {
         
            id<ISDK> object = [self->mPushSDKMap objectForKey:params.provider];
            if(object != nil){
                [object initSDK:params:callBack];
                return;
            }
        }else if (params.service == @"ad") {
            id<ISDK> object = [self->mADSDKMap objectForKey:params.provider];
            if(object != nil){
                [object initSDK:params:callBack];
                return;
            }
        }else if (params.service == @"event") {
            id<ISDK> object = [self->mEventSDKMap objectForKey:params.provider];
            if(object != nil){
                [object initSDK:params:callBack];
                return;
            }
        }else{
        
            id<ISDK> object = [self->mLoginSDKMap objectForKey:params.provider];
            if(object != nil){
                [object initSDK:params:callBack];
                return;
            }
            
            object = [self->mShareSDKMap objectForKey:params.provider];
            if(object != nil){
                [object initSDK:params:callBack];
                return;
            }
            
            object = [self->mPaySDKMap objectForKey:params.provider];
            if(object != nil){
                [object initSDK:params:callBack];
                return;
            }
            
            object  = [self->mADSDKMap objectForKey:params.provider];
            if(object != nil){
                [object initSDK:params:callBack];
                return;
            }
            
            object  = [self->mEventSDKMap objectForKey:params.provider];
            if(object != nil){
                [object initSDK:params:callBack];
                return;
            }
        }
    }];
}

-(void) login:(LoginParams*) params :(id<IXSDKCallback>)callBack
{
    [self doInUIThread: ^{
        id<ILogin> object = [self->mLoginSDKMap objectForKey:params.provider];
        if(object != nil){
            [object login:params:callBack];
            return;
        }
        NSLog(@"xsdk not found %@", params.provider);
    }];
}

-(void) pay:(PayParams*) params :(id<IXSDKCallback>)callBack
{
    [self doInUIThread: ^{
        id<IPay> object = [self->mPaySDKMap objectForKey:params.provider];
        if(object != nil){
            [object pay: params:callBack];
            return;
        }
        NSLog(@"xsdk not found %@", params.provider);
    }];
}

-(void) share:(ShareParams*) params :(id<IXSDKCallback>)callBack
{
    [self doInUIThread: ^{
        id<IShare> object = [self->mShareSDKMap objectForKey:params.provider];
        if(object != nil){
            [object share: params:callBack];
            return;
        }
        NSLog(@"xsdk not found %@", params.provider);
    }];
}

-(void) postEvent:(EventParams*) params{
    [self doInUIThread: ^{
        id<IEvent> object = [self->mEventSDKMap objectForKey:params.provider];
        if(object != nil){
            [object postEvent:params];
            return;
        }
        NSLog(@"xsdk not found %@", params.provider);
    }];
}

// 创建激励广告
-(id<IRewardedVideoAd>) createRewardedVideoAd:(ADParams*)params :(id<IRewardedVideoAdEventCallBack>)callBack
{
    id<IAD> object = [self->mADSDKMap objectForKey:params.provider];
    if(object != nil){
        return  [object createRewardedVideoAd: params:callBack];
    }
    return nil;
}
// 创建banner广告
-(id<IBannerAd>) createBannerAd:(ADParams*)params :(id<IBannerAdEventCallBack>)  callBack
{
    id<IAD> object = [self->mADSDKMap objectForKey:params.provider];
    if(object != nil){
        return  [object createBannerAd: params:callBack];
    }
    return nil;
}
// 创建原生广告
-(id<INativeAd>) createNativeAd:(ADParams*)params :(id<INativeAdEventCallBack>) callBack
{
    id<IAD> object = [self->mADSDKMap objectForKey:params.provider];
    if(object != nil){
        return  [object createNativeAd: params:callBack];
    }
    return nil;
}
// 创建插页式广告
-(id<IInterstitialAd>) createInterstitialAd:(ADParams*)params :(id<IInterstitialAdEventCallBack>) callBack
{
    id<IAD> object = [self->mADSDKMap objectForKey:params.provider];
    if(object != nil){
        return  [object createInterstitialAd: params:callBack];
    }
    return nil;
}


+(void) initSDK:(NSString*)json :(id<IXSDKCallback>)callBack
{

    NSData *jsonData =  [json dataUsingEncoding:NSUTF8StringEncoding];
    NSDictionary *dict = [NSJSONSerialization JSONObjectWithData:jsonData
                                                         options:NSJSONReadingMutableContainers
                                                           error:nil];
    
    
    SDKParams *params = [[SDKParams alloc] init];
    [params setValuesForKeysWithDictionary:dict];
    
    [[XSDK getInstance] initSDK:params :callBack];
}

+(void) login:(NSString*) json :(id<IXSDKCallback>)callBack{
    NSData *jsonData =  [json dataUsingEncoding:NSUTF8StringEncoding];
    NSDictionary *dict = [NSJSONSerialization JSONObjectWithData:jsonData
                                                         options:NSJSONReadingMutableContainers
                                                           error:nil];
    
    
    LoginParams *params = [[LoginParams alloc] init];
    [params setValuesForKeysWithDictionary:dict];
    
    [[XSDK getInstance] login:params :callBack];
}

+(void) pay:(NSString*) json :(id<IXSDKCallback>)callBack
{
    NSData *jsonData =  [json dataUsingEncoding:NSUTF8StringEncoding];
    NSDictionary *dict = [NSJSONSerialization JSONObjectWithData:jsonData
                                                         options:NSJSONReadingMutableContainers
                                                           error:nil];

    PayParams *params = [[PayParams alloc] init];
    [params setValuesForKeysWithDictionary:dict];
    [[XSDK getInstance] pay:params :callBack];
}

+(void) share:(NSString*) json :(id<IXSDKCallback>)callBack
{
//    NSDictionary *data = [[NSDictionary alloc] initWithObjectsAndKeys: nil];
//    NSData *jsonData = [NSJSONSerialization dataWithJSONObject:data options:NSJSONWritingPrettyPrinted error:nil];
//    NSString* ret = [[NSString alloc] initWithData:jsonData encoding:NSUTF8StringEncoding];
//    
// 
//    [callBack onSucess:ret];
    
    NSData *jsonData =  [json dataUsingEncoding:NSUTF8StringEncoding];
    NSDictionary *dict = [NSJSONSerialization JSONObjectWithData:jsonData
                                                         options:NSJSONReadingMutableContainers
                                                           error:nil];


    ShareParams *params = [[ShareParams alloc] init];
    [params setValuesForKeysWithDictionary:dict];
    [[XSDK getInstance] share:params :callBack];
}

+(void) postEvent:(NSString*) json{
    NSData *jsonData =  [json dataUsingEncoding:NSUTF8StringEncoding];
    NSDictionary *dict = [NSJSONSerialization JSONObjectWithData:jsonData
                                                         options:NSJSONReadingMutableContainers
                                                           error:nil];


    EventParams *params = [[EventParams alloc] init];
    [params setValuesForKeysWithDictionary:dict];
    [[XSDK getInstance] postEvent:params];
}

// 创建banner广告
+(id<IBannerAd>) createBannerAd:(NSString*)json  :(id<IBannerAdEventCallBack>)  callBack{
    NSData *jsonData =  [json dataUsingEncoding:NSUTF8StringEncoding];
    NSDictionary *dict = [NSJSONSerialization JSONObjectWithData:jsonData
                                                         options:NSJSONReadingMutableContainers
                                                           error:nil];

    ADParams *params = [[ADParams alloc] init];
    [params setValuesForKeysWithDictionary:dict];
    return [[XSDK getInstance] createBannerAd:params :callBack];
}
// 创建原生广告
+(id<INativeAd>) createNativeAd:(NSString*) json  :(id<INativeAdEventCallBack>) callBack{
    NSData *jsonData =  [json dataUsingEncoding:NSUTF8StringEncoding];
    NSDictionary *dict = [NSJSONSerialization JSONObjectWithData:jsonData
                                                         options:NSJSONReadingMutableContainers
                                                           error:nil];

    ADParams *params = [[ADParams alloc] init];
    [params setValuesForKeysWithDictionary:dict];
    return [[XSDK getInstance] createNativeAd:params :callBack];
}
// 创建插页式广告
+(id<IInterstitialAd>) createInterstitialAd:(NSString*) json  :(id<IInterstitialAdEventCallBack>) callBack{
    NSData *jsonData =  [json dataUsingEncoding:NSUTF8StringEncoding];
    NSDictionary *dict = [NSJSONSerialization JSONObjectWithData:jsonData
                                                         options:NSJSONReadingMutableContainers
                                                           error:nil];

    ADParams *params = [[ADParams alloc] init];
    [params setValuesForKeysWithDictionary:dict];
    return [[XSDK getInstance] createInterstitialAd:params :callBack];
}

+(id<IRewardedVideoAd>) createRewardedVideoAd:(NSString*) json :(id<IRewardedVideoAdEventCallBack>)callBack
{
    NSData *jsonData =  [json dataUsingEncoding:NSUTF8StringEncoding];
    NSDictionary *dict = [NSJSONSerialization JSONObjectWithData:jsonData
                                                         options:NSJSONReadingMutableContainers
                                                           error:nil];

    ADParams *params = [[ADParams alloc] init];
    [params setValuesForKeysWithDictionary:dict];
    return [[XSDK getInstance] createRewardedVideoAd:params :callBack];
}

//========================================================
- (void) setPushCallBack:(id<IPushCallback>)callback{
      mPushCallback = callback;
}

- (id<IPushCallback>) getPushCallBack{
        return mPushCallback;
}

+(void)onPush:(id<IPushCallback>)callback {
    [[XSDK getInstance] setPushCallBack:callback];
}
//========================================================

- (void) forLogin:(NSString *)provider :(id<ILogin>) obj
{
    [ self->mLoginSDKMap setObject:obj forKey:provider];
}

- (void) forPay:(NSString *)provider :(id<IPay>) obj
{
    [ self->mPaySDKMap setObject:obj forKey:provider];
}

- (void) forShare:(NSString *)provider :(id<IShare>)  obj
{
    [ self->mShareSDKMap setObject:obj forKey:provider];
}

- (void) forPush:(NSString *)provider :(id<IPush>)  obj
{
    [ self->mPushSDKMap setObject:obj forKey:provider];
}

- (void) forAD:(NSString *)provider :(id<IShare>)  obj
{
    [ self->mADSDKMap setObject:obj forKey:provider];
}

- (void) forEvent:(NSString *)provider :(id<IShare>)  obj
{
    [ self->mEventSDKMap setObject:obj forKey:provider];
}

@end


