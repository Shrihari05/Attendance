package com.example.justmark;

import android.content.Context;
import android.os.Environment;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVWriter {

    public static boolean writeCSV(Context context, List<String[]> data, String fileName) {
        // Check if external storage is available
        if (!isExternalStorageWritable()) {
            return false;
        }
        DownloadsLocationHelper h=new DownloadsLocationHelper();
        File csvFile = new File(h.getDownloadsDirectory(), fileName);

        try {
            FileWriter csvWriter = new FileWriter(csvFile,false);

            for (String[] row : data) {
                for (int i = 0; i < row.length; i++) {
                    csvWriter.append(row[i]);
                    if (i < row.length - 1) {
                        csvWriter.append(",");
                    }
                }
                csvWriter.append("\n");
            }

            csvWriter.flush();
            csvWriter.close();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }
}
