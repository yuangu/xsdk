package com.seantone.xsdk.core.plugin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Base64;

public class CordovaArgs {
    private JSONArray baseArgs;

    public CordovaArgs(JSONArray args) {
        this.baseArgs = args;
    }


    // Pass through the basics to the base args.
    public Object get(int index) throws JSONException {
        return baseArgs.get(index);
    }

    public boolean getBoolean(int index) throws JSONException {
        return baseArgs.getBoolean(index);
    }

    public double getDouble(int index) throws JSONException {
        return baseArgs.getDouble(index);
    }

    public int getInt(int index) throws JSONException {
        return baseArgs.getInt(index);
    }

    public JSONArray getJSONArray(int index) throws JSONException {
        return baseArgs.getJSONArray(index);
    }

    public JSONObject getJSONObject(int index) throws JSONException {
        return baseArgs.getJSONObject(index);
    }

    public long getLong(int index) throws JSONException {
        return baseArgs.getLong(index);
    }

    public String getString(int index) throws JSONException {
        return baseArgs.getString(index);
    }


    public Object opt(int index) {
        return baseArgs.opt(index);
    }

    public boolean optBoolean(int index) {
        return baseArgs.optBoolean(index);
    }

    public double optDouble(int index) {
        return baseArgs.optDouble(index);
    }

    public int optInt(int index) {
        return baseArgs.optInt(index);
    }

    public JSONArray optJSONArray(int index) {
        return baseArgs.optJSONArray(index);
    }

    public JSONObject optJSONObject(int index) {
        return baseArgs.optJSONObject(index);
    }

    public long optLong(int index) {
        return baseArgs.optLong(index);
    }

    public String optString(int index) {
        return baseArgs.optString(index);
    }

    public boolean isNull(int index) {
        return baseArgs.isNull(index);
    }


    // The interesting custom helpers.
    public byte[] getArrayBuffer(int index) throws JSONException {
        String encoded = baseArgs.getString(index);
        return Base64.decode(encoded, Base64.DEFAULT);
    }
}


