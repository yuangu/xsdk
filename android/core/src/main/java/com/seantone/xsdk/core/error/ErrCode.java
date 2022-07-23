package com.seantone.xsdk.core.error;

public interface ErrCode {
    int ERR_OK = 0;   //用户同意
    int ERR_COMM = -1;
    int ERR_USER_CANCEL = -2; //用户取消
    int ERR_SENT_FAILED = -3;
    int ERR_USER_DENIED = -4; //用户拒绝授权
    int ERR_UNSUPPORT = -5;
    int ERR_BAN = -6;
    int ERR_NO_INSTALL_TARGET_APP = -7; //没有安装对应的app
}