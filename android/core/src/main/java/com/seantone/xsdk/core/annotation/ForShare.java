package com.seantone.xsdk.core.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public  @interface ForShare {
    public abstract String provider();
}
