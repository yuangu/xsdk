#import <Foundation/Foundation.h>

// https://blog.csdn.net/u011043997/article/details/25236983

@interface MiniProgram : NSObject 
@property (nonatomic, copy) NSString *appId;
@property (nonatomic, copy) NSString *path;
@property (nonatomic, copy) NSNumber* type;
@property (nonatomic, copy) NSString *webUrl;
@end


@interface ShareParams : NSObject 
@property (nonatomic, copy) NSString  *provider;
@property (nonatomic, copy) NSNumber* type;
@property (nonatomic, copy) NSString *title;
@property (nonatomic, copy) NSString *scene;
@property (nonatomic, copy) NSString *summary; // 分享内容的摘要
@property (nonatomic, copy) NSString *href;
@property (nonatomic, copy) NSString *imageUrl;
@property (nonatomic, copy) NSString *mediaUrl;
@property (nonatomic, strong) MiniProgram  *miniProgram;
@property (nonatomic) BOOL openCustomerServiceChat;
@property (nonatomic, copy) NSString *corpid;
@property (nonatomic, copy) NSString *customerUrl;
@end
