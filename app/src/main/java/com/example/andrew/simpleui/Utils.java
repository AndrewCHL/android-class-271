package com.example.andrew.simpleui;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Andrew on 8/2/16.
 */
public class Utils {
    public static void writeFile(Context context, String fileName, String content){
        try {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_APPEND);
            fos.write(content.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readFile(Context context, String fileName){
        try {
            FileInputStream fis = context.openFileInput(fileName);
            byte[] buffer = new byte[2048];
            fis.read(buffer, 0, buffer.length); // read form [0] to [2047]
            fis.close();

            String content = new String(buffer);
            return  content;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
