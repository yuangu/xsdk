#import <Foundation/Foundation.h>

@protocol IXSDKCallback <NSObject>
-(void)onSucess: (NSString*)ret;
-(void)onFaild: (NSString*) ret;
@end
