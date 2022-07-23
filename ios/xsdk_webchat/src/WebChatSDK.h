//
//  WebChatSDK.h
//  xsdk_webchat
//
//  Created by yuangu on 2021/6/24.
//

#import <Foundation/Foundation.h>

#define HAS_WEBCHAT_PAY 0

#import "ILogin.h"
#import "WXApi.h"
#import "XSDK.h"

#if HAS_WEBCHAT_PAY == 0
#import "IPay.h"
#endif

#import "ISdk.h"
#import "IShare.h"
#import "XSDKModuleManager.h"

NS_ASSUME_NONNULL_BEGIN

#if HAS_WEBCHAT_PAY == 1
@interface WebChatSDK : NSObject<ISDK, ILogin, IPay, IShare, WXApiDelegate, LVModule>
#else
@interface WebChatSDK : NSObject<ISDK,  ILogin, IShare, WXApiDelegate, LVModule>
#endif
{
    NSMutableDictionary* mCallBackMap;
}
@property (strong, nonatomic)   NSMutableDictionary* mCallBackMap;

@end

NS_ASSUME_NONNULL_END
