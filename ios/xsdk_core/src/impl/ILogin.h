#import <Foundation/Foundation.h>
#import "IXSDKCallback.h"
#import "loginParams.h"

@protocol ILogin  <NSObject>
-(void) login:(LoginParams*) params :(id<IXSDKCallback>)callBack;
-(void) logout:(LogoutParams*) params :(id<IXSDKCallback>)callBack;
@end
