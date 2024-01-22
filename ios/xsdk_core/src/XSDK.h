#import <Foundation/Foundation.h>
#import "ILogin.h"
#import "IPay.h"
#import "ISDK.h"
#import "IShare.h"
#import "IPush.h"
#import "IXSDKCallback.h"
#import "IPushCallback.h"
#import "IAD.h"
#import "IEvent.h"

@interface XSDK : NSObject< ISDK, ILogin, IPay, IShare, IAD,IEvent>

+ (XSDK*)getInstance;

-(void) doInUIThread:(dispatch_block_t) block;
-(void) doInCallBackThread:(dispatch_block_t) block;
-(NSString*) warpNSString:(NSString*)str;

//===============================
-(void) initSDK:(SDKParams*) params :(id<IXSDKCallback>)callBack;
-(void) login:(LoginParams*) params :(id<IXSDKCallback>)callBack;
-(void) pay:(PayParams*) params :(id<IXSDKCallback>)callBack;
-(void) share:(ShareParams*) params :(id<IXSDKCallback>)callBack;
-(void) postEvent:(EventParams*) params;

// for ad
// 创建激励广告
-(id<IRewardedVideoAd>) createRewardedVideoAd:(ADParams*)params :(id<IRewardedVideoAdEventCallBack>)callBack;
// 创建banner广告
-(id<IBannerAd>) createBannerAd:(ADParams*)params :(id<IBannerAdEventCallBack>)  callBack;
// 创建原生广告
-(id<INativeAd>) createNativeAd:(ADParams*)params :(id<INativeAdEventCallBack>) callBack;
// 创建插页式广告
-(id<IInterstitialAd>) createInterstitialAd:(ADParams*)params :(id<IInterstitialAdEventCallBack>) callBack;


- (void) forLogin:(NSString *)provider :(id<ILogin>) obj;
- (void) forPay:(NSString *)provider :(id<IPay>) obj;
- (void) forShare:(NSString *)provider :(id<IShare>)  obj;
- (void) forPush:(NSString *)provider :(id<IPush>)  obj;
- (void) forAD:(NSString *)provider :(id<IShare>)  obj;
- (void) forEvent:(NSString *)provider :(id<IShare>)  obj;
-(bool) hasSDK:(NSString*) sdkName :(NSString*) sdkType;
-(void) logout:(LogoutParams*) params :(id<IXSDKCallback>)callBack;

+(void) initSDK:(NSString*) json :(id<IXSDKCallback>)callBack;
+(void) login:(NSString*) json :(id<IXSDKCallback>)callBack;
+(void) pay:(NSString*) json :(id<IXSDKCallback>)callBack;
+(void) share:(NSString*) json :(id<IXSDKCallback>)callBack;
+(void) onPush:(id<IPushCallback>)callback;
+(void) postEvent:(NSString*) json;
+(bool) hasSDK:(NSString*) json;
+(void) logout:(NSString*) json  :(id<IXSDKCallback>)callBack;

// 创建激励广告
+(id<IRewardedVideoAd>) createRewardedVideoAd:(NSString*) json :(id<IRewardedVideoAdEventCallBack>)callBack;
// 创建banner广告
+(id<IBannerAd>) createBannerAd:(NSString*) json  :(id<IBannerAdEventCallBack>)  callBack;
// 创建原生广告
+(id<INativeAd>) createNativeAd:(NSString*) json  :(id<INativeAdEventCallBack>) callBack;
// 创建插页式广告
+(id<IInterstitialAd>) createInterstitialAd:(NSString*) json  :(id<IInterstitialAdEventCallBack>) callBack;

- (id<IPushCallback>) getPushCallBack;
@end


