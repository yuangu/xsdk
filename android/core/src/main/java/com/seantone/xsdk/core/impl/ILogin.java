package com.seantone.xsdk.core.impl;

import com.seantone.xsdk.core.define.LoginParams;
import com.seantone.xsdk.core.impl.IXSDKCallback;

public interface ILogin {
    void login(LoginParams params, IXSDKCallback callback);
    void isAuthorized(LoginParams params, IXSDKCallback callback);
    void logout(LoginParams params, IXSDKCallback callback);
}
