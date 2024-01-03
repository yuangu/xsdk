#import <Foundation/Foundation.h>
#import "IXSDKCallback.h"
#import "loginParams.h"
#import "eventParams.h"


@protocol IEvent  <NSObject>
-(void) postEvent:(EventParams*) params;
@end
