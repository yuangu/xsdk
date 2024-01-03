#import <Foundation/Foundation.h>
#import "adParams.h"
#import "IBannerAd.h"
#import "IBannerAdEventCallBack.h"
#import "INativeAd.h"
#import "INativeAdEventCallBack.h"
#import "IInterstitialAd.h"
#import "IInterstitialAdEventCallBack.h"
#import "IRewardedVideoAd.h"
#import "IRewardedVideoAdEventCallBack.h"


@protocol IAD  <NSObject>
// 创建激励广告
-(id<IRewardedVideoAd>) createRewardedVideoAd:(ADParams*)params :(id<IRewardedVideoAdEventCallBack>)callBack;
// 创建banner广告
-(id<IBannerAd>) createBannerAd:(ADParams*)params :(id<IBannerAdEventCallBack>)  callBack;
// 创建原生广告
-(id<INativeAd>) createNativeAd:(ADParams*)params :(id<INativeAdEventCallBack>) callBack;
// 创建插页式广告
-(id<IInterstitialAd>) createInterstitialAd:(ADParams*)params :(id<IInterstitialAdEventCallBack>) callBack;

@end

