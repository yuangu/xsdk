//
//  INativeAd.h
//  core
//
//  Created by yuangu on 2022/6/1.
//

#import <Foundation/Foundation.h>

@protocol INativeAd <NSObject>
-(void)reportAdShow:(NSString*) ret;
-(void)reportAdClick:(NSString*) ret;
-(void)destory;
@end
