package com.seantone.xsdk.core.plugin;

import org.json.JSONArray;
import org.json.JSONException;

public class XSDKPlugin {

    public boolean execute(String action, String rawArgs, CallbackContext callbackContext) throws JSONException {
        JSONArray args = new JSONArray(rawArgs);
        return execute(action, args, callbackContext);
    }

    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        CordovaArgs cordovaArgs = new CordovaArgs(args);
        return execute(action, cordovaArgs, callbackContext);
    }

    public boolean execute(String action, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
        return false;
    }
}
