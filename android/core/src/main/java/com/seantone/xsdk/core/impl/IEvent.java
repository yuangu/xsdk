package com.seantone.xsdk.core.impl;

import com.seantone.xsdk.core.define.EventParams;


public interface IEvent {
    void postEvent(EventParams params);
}
