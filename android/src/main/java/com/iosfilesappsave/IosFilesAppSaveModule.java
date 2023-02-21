package com.iosfilesappsave;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.io.File;
import java.io.FileOutputStream;
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
  Promise mcallback;
  String fileName = "myFile.pdf";
  URL url;

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
  public void startDownload(String promise, String customFileName, Boolean isBase64, Promise callback) {
    downloadFile(promise, customFileName,isBase64, callback);
  }

  private void downloadFile(String fileUrl, String customFileName,Boolean isBase64, Promise callback) {
    //* Check if customFileName is not null
    mcallback = callback;
    if (isBase64) {
      if(customFileName != null) {
        fileName = customFileName;
      }
      saveBase64ToFile(mContext,fileUrl,fileName);
    } else {
      if(customFileName != null) {
        fileName = customFileName;
      } else {
        fileName = fileUrl.substring(fileUrl.lastIndexOf('/') + 1);
      }
      DownloadTask task = new DownloadTask();
      task.execute(fileUrl);
    }
  }

  public boolean saveBase64ToFile(Context context, String base64String, String fileName) {
    byte[] fileBytes = Base64.decode(base64String, Base64.DEFAULT);
    File downloadFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
    if (!downloadFolder.exists()) {
      downloadFolder.mkdirs();
    }
    File file = new File(downloadFolder, fileName);
    try {
      FileOutputStream outputStream = new FileOutputStream(file);
      outputStream.write(fileBytes);
      outputStream.close();
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
    Uri uri = Uri.fromFile(file);
    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
    context.sendBroadcast(mediaScanIntent);
    WritableMap map = Arguments.createMap();
    map.putBoolean("success",false);
    map.putString("message","Download Done");
    if (file.exists()) {
      map.putString("path",file.getAbsolutePath());
    }
    mcallback.resolve(map);
    return true;
  }

  private void startDownloadTask (){
    WritableMap map = Arguments.createMap();
    DownloadManager.Request request = new DownloadManager.Request((Uri.parse(String.valueOf(url))))
      .setTitle(fileName)
      .setDescription("Downloading " + fileName)
      .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
      .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOCUMENTS, fileName)
      .setAllowedOverRoaming(false);
      downloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
      downloadId = downloadManager.enqueue(request);
      map.putBoolean("success",true);
      map.putString("message","Download Done22");
      File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),fileName);
//      if (file.exists()) {
        map.putString("path",file.getAbsolutePath());
//      }
      mcallback.resolve(map);
  }


  public class DownloadTask extends AsyncTask<String, Void, Boolean> {
    @Override
    protected Boolean doInBackground(String... params) {
      String fileUrl = params[0];
      Log.e("fileUrl",fileUrl);
      Boolean isFileExist = false;
      HttpURLConnection urlConnection = null;
      WritableMap map = Arguments.createMap();
      try {
        url = new URL(fileUrl);
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("HEAD");

        // Check if the response code is 200 (OK)
        if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
          // Create a new download request
          return true;
        } else {
          // The file doesn't exist or is unavailable
          map.putBoolean("success",false);
          map.putString("message","File not found: The specified file URL doesn't exist or is unavailable");
          map.putString("path",null);
//          mcallback.resolve(map);
          return false;
        }
      } catch (IOException e) {
        e.printStackTrace();
        // Handle the exception and reject the promise
        map.putBoolean("success",false);
        map.putString("message",e.toString());
        map.putString("path",null);
//        mcallback.resolve(map);
        return false;
      } finally {
        if (urlConnection != null) {
          urlConnection.disconnect();
        }
      }
    }

    @Override
    protected void onPostExecute(Boolean result) {
      if(result){
        startDownloadTask();
      } else {
        WritableMap map = Arguments.createMap();
        map.putBoolean("success",false);
        map.putString("message","File not found: The specified file URL doesn't exist or is unavailable");
        map.putString("path",null);
        mcallback.resolve(map);
      }
    }
  }


  // Example method
  // See https://reactnative.dev/docs/native-modules-android
  @ReactMethod
  public void multiply(double a, double b, Promise promise) {
    promise.resolve(a * b);
  }
}
