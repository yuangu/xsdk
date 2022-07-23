//
//  XSDKModuleManager.m
//  XSDKModuleManager
//
//  Created by 吕佳珍 on 2020/3/30.
//

#import "XSDKModuleManager.h"

@interface XSDKModuleManager ()

@property (nonatomic,strong) NSMutableArray <id<LVModule>>*modules;

@end

@implementation XSDKModuleManager

+ (instancetype)sharedInstance {
    static XSDKModuleManager *instance = nil;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        instance = [[[self class] alloc] init];
    });
    return instance;
}


- (NSMutableArray <id<LVModule>> *)modules {
    if (!_modules) {
        _modules = [NSMutableArray array];
    }
    return _modules;
}


- (void)addModule:(id<LVModule>)module {
    if ([self.modules containsObject:module]) {
        return;
    }
    [self.modules addObject:module];
}


- (void)loadModulesWithPlistFile:(NSString *)plistFile {
    NSArray <NSString *> *moduleNames = [NSArray arrayWithContentsOfFile:plistFile];
    for (NSString *moduleName in moduleNames) {
        id<LVModule> module = [[NSClassFromString(moduleName) alloc]init];
        [self addModule:module];
    }
}


- (NSArray<id<LVModule>> *)allModules
{
  return self.modules;
}

#pragma mark - State Transitions / Launch time:

- (BOOL)application:(UIApplication *)application willFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
  for (id<LVModule> module in self.modules) {
    if ([module respondsToSelector:_cmd]) {
      [module application:application willFinishLaunchingWithOptions:launchOptions];
    }
  }
  return YES;
}

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
  for (id<LVModule> module in self.modules) {
    if ([module respondsToSelector:_cmd]) {
      [module application:application didFinishLaunchingWithOptions:launchOptions];
    }
  }
  return YES;
}

#pragma mark - State Transitions / Transitioning to the foreground:

- (void)applicationDidBecomeActive:(UIApplication *)application
{
  for (id<LVModule> module in self.modules) {
    if ([module respondsToSelector:_cmd]) {
      [module applicationDidBecomeActive:application];
    }
  }
}

#pragma mark - State Transitions / Transitioning to the foreground:

- (void)applicationDidEnterBackground:(UIApplication *)application
{
  for (id<LVModule> module in self.modules) {
    if ([module respondsToSelector:_cmd]) {
      [module applicationDidEnterBackground:application];
    }
  }
}

#pragma mark - State Transitions / Transitioning to the inactive state:

// Called when leaving the foreground state.
- (void)applicationWillResignActive:(UIApplication *)application
{
  for (id<LVModule> module in self.modules) {
    if ([module respondsToSelector:_cmd]) {
      [module applicationWillResignActive:application];
    }
  }
}

// Called when transitioning out of the background state.
- (void)applicationWillEnterForeground:(UIApplication *)application
{
  for (id<LVModule> module in self.modules) {
    if ([module respondsToSelector:_cmd]) {
      [module applicationWillEnterForeground:application];
    }
  }
}

#pragma mark - State Transitions / Termination:

- (void)applicationWillTerminate:(UIApplication *)application
{
  for (id<LVModule> module in self.modules) {
    if ([module respondsToSelector:_cmd]) {
      [module applicationWillTerminate:application];
    }
  }
}

#pragma mark - Handling Remote Notification

- (void)application:(UIApplication *)application didRegisterForRemoteNotificationsWithDeviceToken:(NSData *)deviceToken
{
  for (id<LVModule> module in self.modules) {
    if ([module respondsToSelector:_cmd]) {
      [module application:application didRegisterForRemoteNotificationsWithDeviceToken:deviceToken];
    }
  }
}

- (void)application:(UIApplication *)application didFailToRegisterForRemoteNotificationsWithError:(NSError *)error
{
  for (id<LVModule> module in self.modules) {
    if ([module respondsToSelector:_cmd]) {
      [module application:application didFailToRegisterForRemoteNotificationsWithError:error];
    }
  }
}

- (void)application:(UIApplication *)application
  didReceiveRemoteNotification:(NSDictionary *)userInfo
  fetchCompletionHandler:(void (^)(UIBackgroundFetchResult result))completionHandler;
{
  for (id<LVModule> module in self.modules) {
    if ([module respondsToSelector:_cmd]) {
      [module application:application didReceiveRemoteNotification:userInfo fetchCompletionHandler:completionHandler];
    }
  }
}

// Deprecated from iOS 10.0
- (void)application:(UIApplication *)application didReceiveRemoteNotification:(NSDictionary *)userInfo
{
  for (id<LVModule> module in self.modules) {
    if ([module respondsToSelector:_cmd]) {
      [module application:application didReceiveRemoteNotification:userInfo];
    }
  }
}

- (void)application:(UIApplication *)application
  handleActionWithIdentifier:(NSString *)identifier
  forRemoteNotification:(NSDictionary *)userInfo
  completionHandler:(void (^)(void))completionHandler
{
  for (id<LVModule> module in self.modules) {
    if ([module respondsToSelector:_cmd]) {
      [module application:application
handleActionWithIdentifier:identifier
    forRemoteNotification:userInfo
        completionHandler:completionHandler];
    }
  }
}

- (void)application:(UIApplication *)application
  handleActionWithIdentifier:(NSString *)identifier
  forRemoteNotification:(NSDictionary *)userInfo
   withResponseInfo:(NSDictionary *)responseInfo
  completionHandler:(void (^)(void))completionHandler
{
  for (id<LVModule> module in self.modules) {
    if ([module respondsToSelector:_cmd]) {
        if (@available(iOS 9.0, *)) {
            [module application:application
     handleActionWithIdentifier:identifier
          forRemoteNotification:userInfo
               withResponseInfo:responseInfo
              completionHandler:completionHandler];
        } else {
            // Fallback on earlier versions
        }
    }
  }
}

#pragma mark - Handling Local Notification

- (void)userNotificationCenter:(UNUserNotificationCenter *)center
       willPresentNotification:(UNNotification *)notification
         withCompletionHandler:(void (^)(UNNotificationPresentationOptions options))completionHandler
API_AVAILABLE(ios(10.0)){
  for (id<LVModule> module in self.modules) {
    if ([module respondsToSelector:_cmd]) {
      [module userNotificationCenter:center
             willPresentNotification:notification
               withCompletionHandler:completionHandler];
    }
  }
}

- (void)userNotificationCenter:(UNUserNotificationCenter *)center
didReceiveNotificationResponse:(UNNotificationResponse *)response
         withCompletionHandler:(void (^)(void))completionHandler
API_AVAILABLE(ios(10.0)){
  for (id<LVModule> module in self.modules) {
    if ([module respondsToSelector:_cmd]) {
      [module userNotificationCenter:center
      didReceiveNotificationResponse:response
               withCompletionHandler:completionHandler];
    }
  }
}

// Deprecated from iOS 10.0
- (void)application:(UIApplication *)application didReceiveLocalNotification:(UILocalNotification *)notification
{
  for (id<LVModule> module in self.modules) {
    if ([module respondsToSelector:_cmd]) {
      [module application:application didReceiveLocalNotification:notification];
    }
  }
}

- (void)application:(UIApplication *)application
  handleActionWithIdentifier:(NSString *)identifier
  forLocalNotification:(UILocalNotification *)notification
  completionHandler:(void (^)(void))completionHandler
{
  for (id<LVModule> module in self.modules) {
    if ([module respondsToSelector:_cmd]) {
      [module application:application
handleActionWithIdentifier:identifier
     forLocalNotification:notification
        completionHandler:completionHandler];
    }
  }
}

- (void)application:(UIApplication *)application
  handleActionWithIdentifier:(NSString *)identifier
  forLocalNotification:(UILocalNotification *)notification
   withResponseInfo:(NSDictionary *)responseInfo
  completionHandler:(void (^)(void))completionHandler
{
  for (id<LVModule> module in self.modules) {
    if ([module respondsToSelector:_cmd]) {
        if (@available(iOS 9.0, *)) {
            [module application:application
     handleActionWithIdentifier:identifier
           forLocalNotification:notification
               withResponseInfo:responseInfo
              completionHandler:completionHandler];
        } else {
            // Fallback on earlier versions
        }
    }
  }
}

- (void)application:(UIApplication *)application
  didRegisterUserNotificationSettings:(UIUserNotificationSettings *)notificationSettings
{
  for (id<LVModule> module in self.modules) {
    if ([module respondsToSelector:_cmd]) {
      [module application:application didRegisterUserNotificationSettings:notificationSettings];
    }
  }
}

#pragma mark - Handling Continuing User Activity and Handling Quick Actions

- (BOOL)application:(UIApplication *)application
  willContinueUserActivityWithType:(NSString *)userActivityType
{
  BOOL result = NO;
  for (id<LVModule> module in self.modules) {
    if ([module respondsToSelector:_cmd]) {
      result = result || [module application:application willContinueUserActivityWithType:userActivityType];
    }
  }
  return result;
}

- (BOOL)application:(UIApplication *)application
  continueUserActivity:(NSUserActivity *)userActivity
  restorationHandler:(void(^)(NSArray<id<UIUserActivityRestoring>> * __nullable restorableObjects))restorationHandler
{
  BOOL result = NO;
  for (id<LVModule> module in self.modules) {
    if ([module respondsToSelector:_cmd]) {
      result = result || [module application:application continueUserActivity:userActivity restorationHandler:restorationHandler];
    }
  }
  return result;
}

- (void)application:(UIApplication *)application
  didUpdateUserActivity:(NSUserActivity *)userActivity
{
  for (id<LVModule> module in self.modules) {
    if ([module respondsToSelector:_cmd]) {
      [module application:application didUpdateUserActivity:userActivity];
    }
  }
}

- (void)application:(UIApplication *)application
  didFailToContinueUserActivityWithType:(NSString *)userActivityType
              error:(NSError *)error
{
  for (id<LVModule> module in self.modules) {
    if ([module respondsToSelector:_cmd]) {
      [module application:application didFailToContinueUserActivityWithType:userActivityType error:error];
    }
  }
}

- (void)application:(UIApplication *)application
  performActionForShortcutItem:(UIApplicationShortcutItem *)shortcutItem
  completionHandler:(void (^)(BOOL succeeded))completionHandler
API_AVAILABLE(ios(9.0)){
  for (id<LVModule> module in self.modules) {
    if ([module respondsToSelector:_cmd]) {
      [module application:application performActionForShortcutItem:shortcutItem completionHandler:completionHandler];
    }
  }
}

- (BOOL)application:(UIApplication *)application handleOpenURL:(NSURL *)url
{
    BOOL result = NO;
    for (id<LVModule> module in self.modules) {
      if ([module respondsToSelector:_cmd]) {
          result = result || [module application:application handleOpenURL:url];
      }
    }
    return YES;
}

- (BOOL)application:(UIApplication *)application openURL:(nonnull NSURL *)url options:(nonnull NSDictionary<UIApplicationOpenURLOptionsKey,id> *)options {
    BOOL result = NO;
    for (id<LVModule> module in self.modules) {
        if ([module respondsToSelector:_cmd]) {
            result = result || [module application:application openURL:url options:options];
        }
    }
    return  result;
}

@end
