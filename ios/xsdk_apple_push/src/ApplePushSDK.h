//
//  NSObject+ApplePushSDK.h
//  apple_push
//
//  Created by yuangu on 2022/5/25.
//

#import <Foundation/Foundation.h>
#import "ISdk.h"
#import "IPush.h"
#import "XSDKModuleManager.h"

NS_ASSUME_NONNULL_BEGIN

@interface ApplePushSDK:NSObject<LVModule,ISDK,IPush, UNUserNotificationCenterDelegate>

@end

NS_ASSUME_NONNULL_END
