package com.seantone.xsdk.core.impl;

public interface ILogger {
    void setTag(String tag);

    void log(String content);

    void log(String content, Throwable throwable);
}
