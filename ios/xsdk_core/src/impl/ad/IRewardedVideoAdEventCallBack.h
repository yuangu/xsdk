//
//  IRewardedVideoAdEventCallBack.h
//  Pods
//
//  Created by yuangu on 2022/6/1.
//

#import <Foundation/Foundation.h>

@protocol IRewardedVideoAdEventCallBack  <NSObject>
-(void)onLoad:(NSString*) ret;
-(void)onClose;
-(void)onReward;
-(void)onError:(NSString*) ret;
@end
