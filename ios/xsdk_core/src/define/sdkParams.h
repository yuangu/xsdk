#import <Foundation/Foundation.h>

// https://blog.csdn.net/u011043997/article/details/25236983

@interface SDKParams : NSObject 
@property (nonatomic, copy) NSString *provider;
@property (nonatomic, copy) NSString *appid;
@property (nonatomic, copy) NSString *appkey; //非必填
@property (nonatomic, copy) NSString *channel; //渠道，非必填
@property (nonatomic, copy) NSString *service;

@end

