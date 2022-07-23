package com.seantone.xsdk.core.impl;

import com.seantone.xsdk.core.define.PayParams;

public interface IPay {
    void pay(PayParams params, IXSDKCallback callback);
}
