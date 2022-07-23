
#import <Foundation/Foundation.h>

@protocol IBannerAdEventCallBack <NSObject>
-(void)onLoad:(NSString*) ret;
-(void)onHide;
-(void)onError:(NSString*) ret;
@end
