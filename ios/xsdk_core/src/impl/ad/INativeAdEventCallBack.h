
//
//  INativeAdEventCallBack.h
//  core
//
//  Created by yuangu on 2022/6/1.
//

#import <Foundation/Foundation.h>

@protocol INativeAdEventCallBack <NSObject>
-(void)onLoad:(NSString*) ret;
-(void)onClick;
-(void)onError:(NSString*) ret;
@end
