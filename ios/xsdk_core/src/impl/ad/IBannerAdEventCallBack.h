
#import <Foundation/Foundation.h>

@protocol IBannerAdEventCallBack <NSObject>
-(void)onLoad;
-(void)onHide;
-(void)onError:(NSString*) ret;
@end
