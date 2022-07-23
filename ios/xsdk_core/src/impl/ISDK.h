#import <Foundation/Foundation.h>
#import "IXSDKCallback.h"
#import "sdkParams.h"

@protocol ISDK  <NSObject>
-(void) initSDK:(SDKParams*) params :(id<IXSDKCallback>)callBack;
@end
