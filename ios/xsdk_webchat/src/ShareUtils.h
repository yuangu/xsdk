//
//  ShareUtils.h
//  webchat
//
//  Created by yuangu on 2022/5/24.
//

#import <Foundation/Foundation.h>
#import "IShare.h"
#import "WXApi.h"

NS_ASSUME_NONNULL_BEGIN

@interface ShareUtils : NSObject {
    
}

+(SendMessageToWXReq*) shareText:(ShareParams*) params;
+(SendMessageToWXReq*) shareImage:(ShareParams*) params;
+(SendMessageToWXReq*) shareMusic:(ShareParams*) params;
+(SendMessageToWXReq*) shareVideo:(ShareParams*) params;
+(SendMessageToWXReq*) shareWebPage:(ShareParams*) params;
+(SendMessageToWXReq*) shareMP:(ShareParams*) params;
@end

NS_ASSUME_NONNULL_END
