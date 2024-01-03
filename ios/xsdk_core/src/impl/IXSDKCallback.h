#import <Foundation/Foundation.h>

@protocol IXSDKCallback <NSObject>
@required
-(void)onSuccess: (NSString*)ret;
@required
-(void)onFaild: (NSString*) ret;
@end
