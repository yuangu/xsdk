package com.seantone.xsdk.core;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Environment;
import android.text.Layout;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.webkit.URLUtil;
import android.widget.FrameLayout;

import com.seantone.xsdk.core.define.AdParams;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.IntentCompat;


public class Utils {
    static String TAG = "Utils";
    static int REQUEST_CODE = 1;
    public static final String EXTERNAL_STORAGE_IMAGE_PREFIX = "external://";

    public static List<String> checkPermission(Context context, String[] checkList) {
        List<String> list = new ArrayList<>();
        for (String s : checkList) {
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat
                    .checkSelfPermission(context, s)) {
                list.add(s);
            }
        }
        return list;
    }

    public static void requestPermission(String[] needRequestList) {
        Activity activity = XSDK.getInstance().getTopActivity();
        ActivityCompat
                .requestPermissions(activity, needRequestList,
                        REQUEST_CODE);
    }

    public static File getCacheFolder(Context context) {
        File cacheDir = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheDir = new File(Environment.getExternalStorageDirectory(), "cache");
            if (!cacheDir.isDirectory()) {
                cacheDir.mkdirs();
            }
        }

        if (!cacheDir.isDirectory()) {
            cacheDir = context.getCacheDir(); //get system cache folder
        }

        return cacheDir;
    }

    public static File downloadAndCacheFile(Context context, String url) {
        URL fileURL = null;
        try {
            fileURL = new URL(url);

            Log.d(TAG, String.format("Start downloading file at %s.", url));

            HttpURLConnection connection = (HttpURLConnection) fileURL.openConnection();
            connection.connect();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                Log.e(TAG, String.format("Failed to download file from %s, response code: %d.", url, connection.getResponseCode()));
                return null;
            }

            InputStream inputStream = connection.getInputStream();

            File cacheDir = getCacheFolder(context);
            File cacheFile = new File(cacheDir, url.substring(url.lastIndexOf("/") + 1));
            FileOutputStream outputStream = new FileOutputStream(cacheFile);

            byte buffer[] = new byte[4096];
            int dataSize;
            while ((dataSize = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, dataSize);
            }
            outputStream.close();
            Log.d(TAG, String.format("File was downloaded and saved at %s.", cacheFile.getAbsolutePath()));

            return cacheFile;
        } catch (Exception e) {
            Log.e(TAG, "downloadAndCacheFile failed: " + e.getMessage());
        }

        return null;
    }

    public static byte[] readBytes(InputStream inputStream) throws IOException {
        // this dynamically extends to take the bytes you read
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();

        // this is storage overwritten on each iteration with bytes
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        // we need to know how may bytes were read to write them to the byteBuffer
        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }

        // and then we can return your byte array.
        return byteBuffer.toByteArray();
    }

    /**
     * Get input stream from a url
     */
    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    public static InputStream getFileInputStream(String url) {
        InputStream inputStream = null;
        try {
            if (URLUtil.isHttpUrl(url) || URLUtil.isHttpsUrl(url)) {
                Activity activity = XSDK.getInstance().getTopActivity();

                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                    String[] PERMISSION_LIST = new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    };
                    List<String> needRequestList = checkPermission(activity, PERMISSION_LIST);
                    if (needRequestList.isEmpty()) {
                        requestPermission(PERMISSION_LIST);
                    }
                }

                File file = downloadAndCacheFile(XSDK.getInstance().getTopActivity(), url);

                if (file == null) {
                    Log.d(TAG, String.format("File could not be downloaded from %s.", url));
                    return null;
                }

                // url = file.getAbsolutePath();
                inputStream = new FileInputStream(file);

                Log.d(TAG, String.format("File was downloaded and cached to %s.", file.getAbsolutePath()));

            } else if (url.startsWith("data:image")) {  // base64 image
                String imageDataBytes = url.substring(url.indexOf(",") + 1);
                byte imageBytes[] = Base64.decode(imageDataBytes.getBytes(), Base64.DEFAULT);
                inputStream = new ByteArrayInputStream(imageBytes);
                Log.d(TAG, "Image is in base64 format.");
            } else if (url.startsWith(EXTERNAL_STORAGE_IMAGE_PREFIX)) { // external path

                url = Environment.getExternalStorageDirectory().getAbsolutePath() + url.substring(EXTERNAL_STORAGE_IMAGE_PREFIX.length());
                inputStream = new FileInputStream(url);

                Log.d(TAG, String.format("File is located on external storage at %s.", url));

            } else if (!url.startsWith("/")) { // relative path
                inputStream = XSDK.getInstance().getTopActivity().getApplicationContext().getAssets().open(url);
                Log.d(TAG, String.format("File is located in assets folder at %s.", url));
            } else {
                inputStream = new FileInputStream(url);
                Log.d(TAG, String.format("File is located at %s.", url));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return inputStream;
    }

    /**
     * 启动主Activity，目前用于推送中
     * @param context
     * @return
     */
    public static boolean runMainActivity(Context context){
        PackageManager packageManager = context.getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(context.getPackageName());
        ComponentName componentName = intent.getComponent();
        Intent mainIntent = Intent.makeRestartActivityTask(componentName);
        context.startActivity(mainIntent);
        return true;
    }

    public static void setBannerStyle(AdParams.Style style,  FrameLayout.LayoutParams layoutParams){
        if(style != null)
        {
            if(style.left != null)
            {
                if(layoutParams.gravity !=  layoutParams.UNSPECIFIED_GRAVITY)
                {
                    layoutParams.gravity =   layoutParams.gravity| Gravity.START;
                }else{
                    layoutParams.gravity = Gravity.START;
                }

                layoutParams.leftMargin = style.left.intValue();
            }

            if(style.right != null)
            {
                if(layoutParams.gravity !=  layoutParams.UNSPECIFIED_GRAVITY)
                {
                    layoutParams.gravity =   layoutParams.gravity|Gravity.END;
                }else{
                    layoutParams.gravity = Gravity.END;
                }

                layoutParams.rightMargin = style.right.intValue();
            }

            if(style.top != null)
            {
                if(layoutParams.gravity !=  layoutParams.UNSPECIFIED_GRAVITY)
                {
                    layoutParams.gravity =   layoutParams.gravity|Gravity.TOP;
                }else{
                    layoutParams.gravity = Gravity.TOP;
                }
                layoutParams.topMargin = style.top.intValue();
            }

            if(style.bottom != null)
            {
                if(layoutParams.gravity !=  layoutParams.UNSPECIFIED_GRAVITY)
                {
                    layoutParams.gravity =   layoutParams.gravity|Gravity.BOTTOM;
                }else{
                    layoutParams.gravity = Gravity.BOTTOM;
                }
                layoutParams.bottomMargin =style.bottom.intValue();
            }
        }else{
            layoutParams.gravity = Gravity.BOTTOM;
        }

    }

}
