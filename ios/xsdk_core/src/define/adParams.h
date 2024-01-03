#import <Foundation/Foundation.h>

// https://blog.csdn.net/u011043997/article/details/25236983

@interface ADStyle : NSObject 
@property (nonatomic, copy) NSNumber *left;
@property (nonatomic, copy) NSNumber *right;
@property (nonatomic, copy) NSNumber *top;
@property (nonatomic, copy) NSNumber *bottom;

@property (nonatomic, copy) NSNumber *size;
@end


@interface ADParams : NSObject 
@property (nonatomic, copy) NSString  *provider;
@property (nonatomic, copy) NSString  *adpid;
@property (nonatomic, copy) NSString  *adUnitId;
@property (nonatomic, strong)  ADStyle  *style;
@end
