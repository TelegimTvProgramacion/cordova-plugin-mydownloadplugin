package com.example;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyDownloadPlugin extends CordovaPlugin {
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("downloadFile")) {
            String fileUrl = args.getString(0);
            String fileName = args.getString(1);
            downloadFile(fileUrl, fileName, callbackContext);
            return true;
        }
        return false;
    }

    private void downloadFile(final String fileUrl, final String fileName, final CallbackContext callbackContext) {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                try {
                    URL url = new URL(fileUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.connect();

                    InputStream inputStream = connection.getInputStream();
                    FileOutputStream outputStream = new FileOutputStream("/sdcard/" + fileName);

                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, length);
                    }

                    outputStream.flush();
                    outputStream.close();
                    inputStream.close();

                    callbackContext.success("File downloaded successfully");
                } catch (Exception e) {
                    callbackContext.error("Error downloading file: " + e.getMessage());
                }
            }
        });
    }
}
