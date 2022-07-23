#import <Foundation/Foundation.h>
#import "IXSDKCallback.h"
#import "payParams.h"

@protocol IPush  <NSObject>
// 用户是否支持该推送sdk   // 用户是否支持该推送sdk
-(bool) isSupport;
// 是否是最好的,有的推送sdk可用，但是，切后台后不能使用了
-(bool) isBest;
@end
