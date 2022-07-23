package com.seantone.xsdk.core.impl;

import com.seantone.xsdk.core.define.SDKParams;

public interface ISDK {
    void initSDK(SDKParams params, IXSDKCallback callback);
}
