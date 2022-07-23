#import <Foundation/Foundation.h>

// https://blog.csdn.net/u011043997/article/details/25236983

@interface PayParams: NSObject 
@property (nonatomic, copy) NSString *provider;
@property (nonatomic, copy) NSString *orderInfo; //支付的参数,json
@end
