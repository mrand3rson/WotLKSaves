package com.funprojects.wotlksaves.mvp.models;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Andrei on 16.05.2018.
 */

public class RealmRestorer {

    public final static String EXPORT_REALM_PATH =
            Environment.getExternalStorageDirectory().getPath().concat("/sample.realm");
    public final static String IMPORT_REALM_NAME = "default.realm";


    public static void restore(Context applicationContext) {
        copyRealmFromBackup(applicationContext, EXPORT_REALM_PATH, IMPORT_REALM_NAME);
    }

    private static void copyRealmFromBackup(Context applicationContext, String oldFilePath, String outFileName) {
        try {
            File file = new File(applicationContext.getFilesDir(), outFileName);

            FileOutputStream outputStream = new FileOutputStream(file);
            FileInputStream inputStream = new FileInputStream(new File(oldFilePath));

            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buf)) > 0) {
                outputStream.write(buf, 0, bytesRead);
            }
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
