Pod::Spec.new do |s|
    s.name             = 'xsdk_core'
    s.version          = '1.0.0'
    s.summary          = 'A short description of core.'
    s.description      = <<-DESC
  TODO: Add long description of the pod here.
                         DESC
  
    s.homepage         = 'https://github.com/yuangu/xsdk_core'
    s.license          = { :type => 'BSD'  }
    s.author           = { 'yuangu' => 'seantone@126.com' }
    s.source           = { :svn => 'svn://gitee.com/yuangu/xsdk/ios/xsdk_core', :tag => s.version.to_s }
    
    s.ios.deployment_target = '9.0'
    s.static_framework = true
    s.libraries    = 'stdc++'
    
    s.source_files  = "src/**/*"
    s.public_header_files = "src/**/*.h"
    
  end
  
