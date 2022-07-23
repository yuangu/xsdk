//
//  AppDelegate.m
//  Example
//
//  Created by yuangu on 2022/4/24.
//

#import "AppDelegate.h"


@interface AppDelegate ()


@end

@implementation AppDelegate


- (BOOL)application:(UIApplication *)application
    didFinishLaunchingWithOptions:(NSDictionary *)launchOptions {
    
//    XSDKModuleManager *manager = [XSDKModuleManager sharedInstance];
//    [manager application:application willFinishLaunchingWithOptions:launchOptions];
    
    // Override point for customization after application launch.
    return YES;
}

# pragma mark - 微信SDK需要
- (BOOL)application:(UIApplication *)application handleOpenURL:(NSURL *)url{
    XSDKModuleManager *manager = [XSDKModuleManager sharedInstance];
    return  [manager application:application handleOpenURL:url];
}

- (BOOL)application:(UIApplication *)application openURL:(nonnull NSURL *)url options:(nonnull NSDictionary<UIApplicationOpenURLOptionsKey,id> *)options {
    XSDKModuleManager *manager = [XSDKModuleManager sharedInstance];
    return  [manager application:application openURL:url options:options];
}

- (BOOL)application:(UIApplication *)application continueUserActivity:(NSUserActivity *)userActivity restorationHandler:(void(^)(NSArray<id<UIUserActivityRestoring>> * __nullable restorableObjects))restorationHandler API_AVAILABLE(ios(8.0)){
    
    XSDKModuleManager *manager = [XSDKModuleManager sharedInstance];
    return  [manager application:application continueUserActivity:userActivity restorationHandler:restorationHandler];
}

# pragma mark - 苹果推送
- (void)application:(UIApplication *)application didRegisterForRemoteNotificationsWithDeviceToken:(NSData *)deviceToken
{
    XSDKModuleManager *manager = [XSDKModuleManager sharedInstance];
    [manager application:application didRegisterForRemoteNotificationsWithDeviceToken:deviceToken];
}

- (void)application:(UIApplication *)application didFailToRegisterForRemoteNotificationsWithError:(NSError *)error
{
    XSDKModuleManager *manager = [XSDKModuleManager sharedInstance];
    [manager application:application didFailToRegisterForRemoteNotificationsWithError:error];
}

#pragma mark - UISceneSession lifecycle


- (UISceneConfiguration *)application:(UIApplication *)application configurationForConnectingSceneSession:(UISceneSession *)connectingSceneSession options:(UISceneConnectionOptions *)options {
    // Called when a new scene session is being created.
    // Use this method to select a configuration to create the new scene with.
    return [[UISceneConfiguration alloc] initWithName:@"Default Configuration" sessionRole:connectingSceneSession.role];
}


- (void)application:(UIApplication *)application didDiscardSceneSessions:(NSSet<UISceneSession *> *)sceneSessions {
    // Called when the user discards a scene session.
    // If any sessions were discarded while the application was not running, this will be called shortly after application:didFinishLaunchingWithOptions.
    // Use this method to release any resources that were specific to the discarded scenes, as they will not return.
}


@end
