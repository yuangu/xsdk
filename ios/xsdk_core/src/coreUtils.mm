#import "coreUtils.h"

#import <Foundation/Foundation.h>

static int const MAX_THUMBNAIL_SIZE = 320;

@implementation CoreUtils

+(NSData *)getNSDataFromURL:(NSString *)url
{
    NSData *data = nil;

   if ([url hasPrefix:@"http://"] || [url hasPrefix:@"https://"])
   {
       data = [NSData dataWithContentsOfURL:[NSURL URLWithString:url]];
   }
   else if ([url hasPrefix:@"data:image"])
   {
       // a base 64 string
       NSURL *base64URL = [NSURL URLWithString:url];
       data = [NSData dataWithContentsOfURL:base64URL];
   }
   else if ([url rangeOfString:@"temp:"].length != 0)
   {
       url =  [NSTemporaryDirectory() stringByAppendingPathComponent:[url componentsSeparatedByString:@"temp:"][1]];
       data = [NSData dataWithContentsOfFile:url];
   }
   else
   {
       // local file
       url = [[NSBundle mainBundle] pathForResource:[url stringByDeletingPathExtension] ofType:[url pathExtension]];
       data = [NSData dataWithContentsOfFile:url];
   }

   return data;
}

+ (UIImage *)getUIImageFromURL:(NSString *)url
{
    NSData *data = [CoreUtils getNSDataFromURL:url];
    UIImage *image = [UIImage imageWithData:data];

    if (image.size.width > MAX_THUMBNAIL_SIZE || image.size.height > MAX_THUMBNAIL_SIZE)
    {
        CGFloat width = 0;
        CGFloat height = 0;

        // calculate size
        if (image.size.width > image.size.height)
        {
            width = MAX_THUMBNAIL_SIZE;
            height = width * image.size.height / image.size.width;
        }
        else
        {
            height = MAX_THUMBNAIL_SIZE;
            width = height * image.size.width / image.size.height;
        }

        // scale it
        UIGraphicsBeginImageContext(CGSizeMake(width, height));
        [image drawInRect:CGRectMake(0, 0, width, height)];
        UIImage *scaled = UIGraphicsGetImageFromCurrentImageContext();
        UIGraphicsEndImageContext();

        return scaled;
    }

    return image;
}


@end
