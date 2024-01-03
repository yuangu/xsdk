//
//  WebChatSDK.h
//  xsdk_webchat
//
//  Created by yuangu on 2024/1/1.
//

#import <Foundation/Foundation.h>
#import <GoogleMobileAds/GoogleMobileAds.h>

#import "IBannerAd.h"
#import "adParams.h"
#import "IBannerAdEventCallBack.h"

NS_ASSUME_NONNULL_BEGIN

@interface AdmobBannder : NSObject<IBannerAd>
@property(nonatomic, strong) GADBannerView *bannerView;
- (void) create:(ADParams*)params :(id<IBannerAdEventCallBack>)  callBack;
-(void) show:(NSString*) params;
-(void) hide;
-(void) destory;
@end

NS_ASSUME_NONNULL_END
