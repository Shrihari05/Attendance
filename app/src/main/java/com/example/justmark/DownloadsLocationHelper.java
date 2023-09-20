package com.example.justmark;

import android.os.Environment;

public class DownloadsLocationHelper {
public  DownloadsLocationHelper(){}
    public static String getDownloadsDirectory() {
        String downloadsDirectory = null;

        if (isExternalStorageReadable()) {
            downloadsDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
        }

        return downloadsDirectory;
    }

    private static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }
}
