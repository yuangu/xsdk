#import <Foundation/Foundation.h>

@protocol IPushCallback <NSObject>
-(void)onRegisterSucceed:(NSString*) ret;
-(void)onNotificationMessageClicked: (NSString*) ret;
-(void)onNotificationMessageArrived: (NSString*) ret;
@end
