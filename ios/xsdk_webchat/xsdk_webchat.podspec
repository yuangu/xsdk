Pod::Spec.new do |s|
    s.name             = 'xsdk_webchat'
    s.version          = '0.0.1'
    s.summary          = 'A short description of core.'
    s.description      = <<-DESC
    TODO: Add long description of the pod here.
    DESC
  
    s.homepage         = 'https://github.com/yuangu/xsdk_core'
    s.license          = { :type => 'BSD'  }
    s.author           = { 'yuangu' => 'seantone@126.com' }
    s.source           = { :git => 'https://github.com/yuangu/xsdk_core.git', :tag => s.version.to_s }
   
    s.ios.deployment_target = '9.0'
    s.static_framework = true
    
    s.source_files  = "src/**/*.{h,m,mm,swift}"
    # s.private_header_files = "src/Private/*.h"
    s.public_header_files = "src/**/*.h"
    
    s.dependency 'WechatOpenSDK_UnPay'
    s.dependency 'xsdk_core'
  end
  
