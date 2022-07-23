//
//  NSObject+ApplePushSDK.m
//  apple_push
//
//  Created by yuangu on 2022/5/25.
//

#import "ApplePushSDK.h"
#import <UIKit/UIKit.h>
#import "xsdk.h"

#ifdef NSFoundationVersionNumber_iOS_9_x_Max
#import <UserNotifications/UserNotifications.h>
#endif

#define  Provider @"apple"

@implementation ApplePushSDK

#pragma mark - 初使化SDK
-(void) initSDK:(SDKParams*) params :(id<IXSDKCallback>)callBack
{
    [[XSDKModuleManager sharedInstance] addModule:self];
    if (@available(iOS 10.0,*)){
        //10.0以后的注册通知方法
        UNUserNotificationCenter *center = [UNUserNotificationCenter currentNotificationCenter];
        [center setDelegate:self];
        //请求通知的权限
        [center requestAuthorizationWithOptions:(UNAuthorizationOptionBadge|UNAuthorizationOptionSound|UNAuthorizationOptionAlert)
                              completionHandler:^(BOOL granted, NSError * _Nullable error) {
            if (granted) {
                dispatch_async(dispatch_get_main_queue(), ^{
                    [[UIApplication sharedApplication] registerForRemoteNotifications];
                    [callBack onSucess:@"{}"];
                });
            }else{
                NSLog(@"失败");
                [callBack onFaild:@"{}"];
            }
        }];
    }
}

#pragma mark - 获取注册的token
- (void)application:(UIApplication *)application didRegisterForRemoteNotificationsWithDeviceToken:(NSData *)deviceToken
{
    id<IPushCallback> callBack =  [[XSDK  getInstance] getPushCallBack];
    if(callBack == nil) return;
    
    NSString *devToken = [[[[deviceToken description]
                                stringByReplacingOccurrencesOfString:@"<"withString:@""]
                               stringByReplacingOccurrencesOfString:@">" withString:@""]
                              stringByReplacingOccurrencesOfString: @" " withString: @""];
    
    NSDictionary *data = [[NSDictionary alloc] initWithObjectsAndKeys:devToken, @"regId", Provider, @"provider", nil];
    
    NSData *jsonData = [NSJSONSerialization dataWithJSONObject:data options:NSJSONWritingPrettyPrinted error:nil];
    
    NSString* ret = [[NSString alloc] initWithData:jsonData encoding:NSUTF8StringEncoding];
    
    [callBack onRegisterSucceed:ret];
}

#pragma mark - 获取token失败
- (void)application:(UIApplication *)application didFailToRegisterForRemoteNotificationsWithError:(NSError *)error
{
    id<IPushCallback> callBack =  [[XSDK  getInstance] getPushCallBack];
    if(callBack == nil) return;
}

#pragma mark - 是否支持
-(bool) isSupport
{
    return true;
}

-(bool) isBest
{
    return true;
}

// iOS 10收到通知
- (void)userNotificationCenter:(UNUserNotificationCenter *)center willPresentNotification:(UNNotification *)notification withCompletionHandler:(void (^)(UNNotificationPresentationOptions options))completionHandler{
/** 只有在客户端启动的时候才会调用*/
    completionHandler(UNNotificationPresentationOptionAlert); // 需要执行这个方法，选择是否提醒用户，有Badge、Sound、Alert三种类型可以设置
}

//  iOS10特性。点击通知进入App
/** 如果通知界面不是自定义的，那么action的响应是这里处理，如果用到了自定义的通知界面，那么是在对应的自定义界面的controller中的didReceiveNotificationResponse处理*/
//https://developer.apple.com/documentation/usernotifications/handling_notifications_and_notification-related_actions?language=objc
- (void)userNotificationCenter:(UNUserNotificationCenter *)center didReceiveNotificationResponse:(UNNotificationResponse *)response withCompletionHandler:(void(^)())completionHandler {
    
    //  UNNotificationResponse 是普通按钮的Response
    NSString* actionIdentifierStr = response.actionIdentifier;
    if (actionIdentifierStr) {

        if ([actionIdentifierStr isEqualToString:@"IdentifierJoinAppA"]) {
            //  do anything
        } else if ([actionIdentifierStr isEqualToString:@"IdentifierJoinAppB"]) {
            [[UIApplication sharedApplication] setApplicationIconBadgeNumber:0];
        }
    }
    
    //  UNTextInputNotificationResponse 是带文本输入框按钮的Response
    if ([response isKindOfClass:[UNTextInputNotificationResponse class]]) {
        NSString* userSayStr = [(UNTextInputNotificationResponse *)response userText];
        if (userSayStr) {
            dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(2 * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
                
            });
        }
    }
    
    completionHandler();
}

@end
