#import <Foundation/Foundation.h>
#import "IXSDKCallback.h"
#import "payParams.h"

@protocol IPay  <NSObject>
-(void) pay:(PayParams*) params :(id<IXSDKCallback>)callBack;
@end
