#import <AdmobBanner.h>
#import "xsdk.h"

@implementation AdmobBannder{
    id<IBannerAdEventCallBack> eventCallBack;
}

-  (void) create:(ADParams*)params :(id<IBannerAdEventCallBack>)callBack
{
    self->eventCallBack = callBack;
    dispatch_async(dispatch_get_main_queue(), ^{
        if(self.bannerView != nil) return;
        
        UIViewController * viewController = [UIApplication sharedApplication].keyWindow.rootViewController;
        UIView *currentView = [[UIApplication sharedApplication].keyWindow.subviews objectAtIndex:0];
        
        self.bannerView = [[GADBannerView alloc]
                           initWithAdSize:GADAdSizeBanner];
        
        self.bannerView.translatesAutoresizingMaskIntoConstraints = NO;
        [currentView addSubview:self.bannerView];
        self.bannerView.delegate = self;
       
        
        NSMutableArray *mutableArray = [NSMutableArray array];
        if (params.style.bottom != nil) {
            [mutableArray addObject:  [NSLayoutConstraint constraintWithItem:self.bannerView
                                                                      attribute:NSLayoutAttributeBottom
                                                                      relatedBy:NSLayoutRelationEqual
                                                                         toItem:currentView.safeAreaLayoutGuide
                                                                      attribute:NSLayoutAttributeBottom
                                                                     multiplier:1
                                                                       constant: -1 * [params.style.bottom  floatValue]]];
        }
        
        if (params.style.top != nil) {
            [mutableArray addObject:  [NSLayoutConstraint constraintWithItem:self.bannerView
                                                                      attribute:  NSLayoutAttributeTop
                                                                      relatedBy:NSLayoutRelationEqual
                                                                         toItem:currentView.safeAreaLayoutGuide
                                                                      attribute:NSLayoutAttributeTop
                                                                     multiplier:1
                                                                       constant: -1 * [params.style.top  floatValue]]];
        }
        
        if(params.style.left == nil && params.style.right == nil)
        {
            // 居中显示
            [mutableArray addObject: [NSLayoutConstraint constraintWithItem:self.bannerView
                                       attribute:NSLayoutAttributeCenterX
                                       relatedBy:NSLayoutRelationEqual
                                          toItem:currentView
                                       attribute:NSLayoutAttributeCenterX
                                      multiplier:1
                                                                   constant:0]];
        }else{
            
            if (params.style.left != nil) {
                [mutableArray addObject:  [NSLayoutConstraint constraintWithItem:self.bannerView
                                                                          attribute: NSLayoutAttributeLeft
                                                                          relatedBy:NSLayoutRelationEqual
                                                                             toItem:currentView.safeAreaLayoutGuide
                                                                          attribute: NSLayoutAttributeLeft
                                                                         multiplier:1
                                                                           constant: -1 * [params.style.left  floatValue]]];
            }
            
            if (params.style.right  != nil) {
                [mutableArray addObject:  [NSLayoutConstraint constraintWithItem:self.bannerView
                                                                          attribute: NSLayoutAttributeRight
                                                                          relatedBy:NSLayoutRelationEqual
                                                                             toItem:currentView.safeAreaLayoutGuide
                                                                          attribute:  NSLayoutAttributeRight
                                                                         multiplier:1
                                                                           constant: -1 * [params.style.right  floatValue]]];
            }
        }
        
        
        
        NSArray<__kindof NSLayoutConstraint *> * constraints = [[NSArray<__kindof NSLayoutConstraint *> alloc] initWithArray:mutableArray copyItems:NO];
        
        
        [currentView addConstraints:constraints];
        
        self.bannerView.adUnitID = params.adUnitId;
        self.bannerView.rootViewController = viewController;
        [self.bannerView loadRequest:[GADRequest request]];
        self.bannerView.autoloadEnabled = YES;
        self.bannerView.hidden =   YES;
        
    });
}


-(void) show:(NSString*) params{
    dispatch_async(dispatch_get_main_queue(), ^{
        if(self.bannerView == nil) return;
        self.bannerView.hidden = NO;
    });
}

-(void) hide{
    dispatch_async(dispatch_get_main_queue(), ^{
        if(self.bannerView == nil) return;
        self.bannerView.hidden = YES;
        
    });
    
    if(self->eventCallBack != nil)
   {
       [[XSDK getInstance] doInCallBackThread:^{ [self->eventCallBack onHide];}];
   }
}

-(void) destory{
    dispatch_async(dispatch_get_main_queue(), ^{
        if(self.bannerView != nil) return;
        [self.bannerView removeFromSuperview];
        self.bannerView = nil;
    });
}


- (void)bannerViewDidReceiveAd:(nonnull GADBannerView *)bannerView
{
    if(self->eventCallBack != nil)
   {
       [[XSDK getInstance] doInCallBackThread:^{ [self->eventCallBack onLoad:@""];}];
   }
}

- (void)bannerView:(nonnull GADBannerView *)bannerView
    didFailToReceiveAdWithError:(nonnull NSError *)error
{
    if(self->eventCallBack != nil)
   {
       [[XSDK getInstance] doInCallBackThread:^{ [self->eventCallBack onError:@""];}];
   }
}

/// Tells the delegate that an impression has been recorded for an ad.
- (void)bannerViewDidRecordImpression:(nonnull GADBannerView *)bannerView
{
    
}

- (void)bannerViewDidRecordClick:(nonnull GADBannerView *)bannerView
{
    
}



@end

