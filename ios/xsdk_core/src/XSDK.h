#import <Foundation/Foundation.h>
#import "ILogin.h"
#import "IPay.h"
#import "ISDK.h"
#import "IShare.h"
#import "IPush.h"
#import "IXSDKCallback.h"
#import "IPushCallback.h"

@interface XSDK : NSObject< ISDK, ILogin, IPay, IShare>

+ (XSDK*)getInstance;

//===============================
-(void) initSDK:(SDKParams*) params :(id<IXSDKCallback>)callBack;
-(void) login:(LoginParams*) params :(id<IXSDKCallback>)callBack;
-(void) pay:(PayParams*) params :(id<IXSDKCallback>)callBack;
-(void) share:(ShareParams*) params :(id<IXSDKCallback>)callBack;

- (void) forLogin:(NSString *)provider :(id<ILogin>) obj;
- (void) forPay:(NSString *)provider :(id<IPay>) obj;
- (void) forShare:(NSString *)provider :(id<IShare>)  obj;
- (void) forPush:(NSString *)provider :(id<IPush>)  obj;

+(void) initSDK:(NSString*) json :(id<IXSDKCallback>)callBack;
+(void) login:(NSString*) json :(id<IXSDKCallback>)callBack;
+(void) pay:(NSString*) json :(id<IXSDKCallback>)callBack;
+(void) share:(NSString*) json :(id<IXSDKCallback>)callBack;
+(void) onPush:(id<IPushCallback>)callback;

- (id<IPushCallback>) getPushCallBack;
@end


