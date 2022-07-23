#import <Foundation/Foundation.h>
#import "IXSDKCallback.h"
#import "shareParams.h"

@protocol IShare  <NSObject>
-(void) share:(ShareParams*) params :(id<IXSDKCallback>)callBack;
@end
