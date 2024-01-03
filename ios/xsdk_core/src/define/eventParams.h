#import <Foundation/Foundation.h>


@interface EventParams : NSObject 
@property (nonatomic, copy) NSString  *provider;
@property (nonatomic, copy) NSString  *eventName;
@property (nonatomic, copy) NSString  *uid;
@property (nonatomic, copy) NSString  *channel;
@property (nonatomic, copy) NSMutableDictionary *eventArgs;
@end
