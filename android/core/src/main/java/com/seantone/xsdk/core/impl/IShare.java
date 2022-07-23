package com.seantone.xsdk.core.impl;

import com.seantone.xsdk.core.define.ShareParams;

public interface IShare {
    void share(ShareParams params, IXSDKCallback callback);
}
