package com.iosfilesappsave;

import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Environment;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.io.File;
import java.io.IOException;

import com.facebook.react.bridge.WritableMap;
import com.facebook.react.module.annotations.ReactModule;

import java.net.HttpURLConnection;
import java.net.URL;

@ReactModule(name = IosFilesAppSaveModule.NAME)
public class IosFilesAppSaveModule extends ReactContextBaseJavaModule {
  public static final String NAME = "IosFilesAppSave";
  Context mContext;
  private long downloadId;
  private DownloadManager downloadManager;

  public IosFilesAppSaveModule(ReactApplicationContext reactContext) {
    super(reactContext);
    mContext = reactContext;
  }

  @Override
  @NonNull
  public String getName() {
    return NAME;
  }

  @ReactMethod
  public void startDownload(String promise, Promise callback) {
    downloadFile(promise, callback);
  }

  private void downloadFile(String fileUrl, String customFileName, Promise callback) {
    WritableMap map = Arguments.createMap();
    String appName = getApplicationName();

    String fileName = "myFile.pdf"

    //* Check if customFileName is not null

    if(customFileName != null) {
      fileName = customFileName
    } else {
      fileName = fileUrl.substring(fileUrl.lastIndexOf('/') + 1);
    }

    // Create a new HttpURLConnection object
    HttpURLConnection urlConnection = null;
    try {
      URL url = new URL(fileUrl);
      urlConnection = (HttpURLConnection) url.openConnection();
      urlConnection.setRequestMethod("HEAD");

      // Check if the response code is 200 (OK)
      if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
        // Create a new download request
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(fileUrl))
          .setTitle(fileName)
          .setDescription("Downloading " + fileName)
          .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
          .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOCUMENTS, fileName)
          .setAllowedOverRoaming(false);
        downloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
        downloadId = downloadManager.enqueue(request);
        map.putBoolean("success",true);
        map.putString("message","Download Done");
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),fileName);
        if (file.exists()) {
          map.putString("path",file.getAbsolutePath());
        }
        callback.resolve(map);
      } else {
        // The file doesn't exist or is unavailable
        map.putBoolean("success",false);
        map.putString("message","File not found: The specified file URL doesn't exist or is unavailable");
        map.putString("path",null);
        callback.resolve(map);
      }
    } catch (IOException e) {
      e.printStackTrace();
      // Handle the exception and reject the promise
      map.putBoolean("success",false);
      map.putString("message",e.toString());
      map.putString("path",null);
      callback.resolve(map);
    } finally {
      if (urlConnection != null) {
        urlConnection.disconnect();
      }
    }
  }


  private String getApplicationName() {
    ApplicationInfo applicationInfo = mContext.getApplicationInfo();
    int stringId = applicationInfo.labelRes;
    return stringId == 0 ? applicationInfo.nonLocalizedLabel.toString() : mContext.getString(stringId);
  }


  // Example method
  // See https://reactnative.dev/docs/native-modules-android
  @ReactMethod
  public void multiply(double a, double b, Promise promise) {
    promise.resolve(a * b);
  }
}
