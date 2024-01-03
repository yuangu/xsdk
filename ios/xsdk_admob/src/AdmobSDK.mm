#import <AdmobSDK.h>
#import <GoogleMobileAds/GoogleMobileAds.h>
#import "xsdk.h"
#import "AdmobBanner.h"

struct __AutoAutoAdmobSDKResg{
    __AutoAutoAdmobSDKResg(){
        id sdkObj = [[AdmobSDK alloc]init];
        
        [[XSDK getInstance] forAD:@"admob" :sdkObj];

    }
} __AutoAutoAdmobSDKResgResg;


@implementation AdmobSDK{
    BOOL isInitAdmobSDK;
}

-(id) init{
    if(self=[super init])
    {
        [[XSDKModuleManager sharedInstance] addModule:self];
        self->isInitAdmobSDK = false;
    }
    return self;
}

#pragma mark - 初使化SDK
-(void) initSDK:(SDKParams*) params :(id<IXSDKCallback>)callBack
{
   [[XSDK getInstance] doInUIThread:^{
       if(!self->isInitAdmobSDK){
           [GADMobileAds.sharedInstance startWithCompletionHandler:nil];
            self->isInitAdmobSDK = true;
       }
     
       [[XSDK getInstance] doInCallBackThread:^{
           [callBack onSuccess:[NSString stringWithFormat:@"%@", @"{}"]];
       }];
      
   }];
}

// 创建激励广告
-(id<IRewardedVideoAd>) createRewardedVideoAd:(ADParams*)params :(id<IRewardedVideoAdEventCallBack>)callBack{
    return nil;
}

// 创建banner广告
-(id<IBannerAd>) createBannerAd:(ADParams*)params :(id<IBannerAdEventCallBack>)  callBack{
    AdmobBannder* banner =  [[AdmobBannder alloc] init];
    [banner create:params :callBack];
    return banner ;
}

// 创建原生广告
-(id<INativeAd>)  createNativeAd:(ADParams*)params :(id<INativeAdEventCallBack>) callBack{
    return nil;
}

// 创建插页式广告
-(id<IInterstitialAd>) createInterstitialAd:(ADParams*)params :(id<IInterstitialAdEventCallBack>) callBack{
    return nil;
}

@end
