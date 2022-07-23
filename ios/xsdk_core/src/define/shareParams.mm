#import "shareParams.h"

@implementation MiniProgram


@end

@implementation ShareParams

// https://www.jianshu.com/p/531ac7ff6d2f
- (void)setValue:(id)value forKey:(NSString *)key{
    [super setValue:value forKey:key]; // 必须调用父类方法
    if ([key isEqualToString:@"miniProgram"]) { // 特殊字符处理
        MiniProgram * miniProgram = [[MiniProgram alloc]init]; // 模型嵌套模型
        [miniProgram setValuesForKeysWithDictionary:value];
        self.miniProgram = miniProgram;
    }

}
// 如果发现为标识的字符 必须调用 防止意外崩溃
- (void)setValue:(id)value forUndefinedKey:(NSString *)key{

}



@end



