#import <Foundation/Foundation.h>

@protocol IBannerAd  <NSObject>
-(void) show:(NSString*) params;
-(void) hide;
-(void) destory;
@end

