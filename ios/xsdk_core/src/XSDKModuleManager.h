//
//  XSDKModuleManager.h
//  XSDKModuleManager
//
//  Created by 吕佳珍 on 2020/3/30.
//

// copy f;rom https://github.com/krisouljz/LvModuleManager

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>

#ifdef NSFoundationVersionNumber_iOS_9_x_Max
#import <UserNotifications/UserNotifications.h>
#endif



@protocol LVModule <UIApplicationDelegate, UNUserNotificationCenterDelegate>

@end

NS_ASSUME_NONNULL_BEGIN

@interface XSDKModuleManager : NSObject<UIApplicationDelegate,UNUserNotificationCenterDelegate>

+ (instancetype)sharedInstance;

- (void)addModule:(id<LVModule>)module;

- (void)loadModulesWithPlistFile:(NSString *)plistFile;

- (NSArray <id<LVModule>> *)allModules;

@end

NS_ASSUME_NONNULL_END
