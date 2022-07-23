#import <Foundation/Foundation.h>
#import "ad/IBannerAd.h"
#import "ad/IBannerAdEventCallBack.h"
#import "ad/INativeAd.h"
#import "ad/INativeAdEventCallBack.h"
#import "ad/IInterstitialAd.h"
#import "ad/IInterstitialAdEventCallBack.h"
#import "ad/IRewardedVideoAd.h"
#import "ad/IRewardedVideoAdEventCallBack.h"


@protocol IAD  <NSObject>
// 创建激励广告
-(IRewardedVideoAd) createRewardedVideoAd:(AdParams*)params :id<IRewardedVideoAdEventCallBack>)callBack;
// 创建banner广告
-(IBannerAd) createBannerAd:(AdParams*)params :id<IBannerAdEventCallBack>)  callBack;
// 创建原生广告
-(INativeAd) createNativeAd:(AdParams*)params :id<INativeAdEventCallBack>) callBack;
// 创建插页式广告
-(InterstitialAd) createInterstitialAd:(AdParams*)params :id<IInterstitialAdEventCallBack>) callBack;

@end

