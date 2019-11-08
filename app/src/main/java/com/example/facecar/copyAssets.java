package com.example.facecar;


import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class copyAssets {

    // 2.2.4
    // Code resource: https://gist.github.com/mhasby/026f02b33fcc4207b302a60645f6e217
    // Credit goes to mhasby
    // Changes made to suit our application
    public static final String PICTUREPATH = "/storage/emulated/0/Android/data/com.example.facecar/files/Pictures/";


        public static void copyAssets(Context context) {
            AssetManager assetManager = context.getAssets();
            String[] files = null;
            try {
                files = assetManager.list("");
            } catch (IOException e) {
                Log.e("tag", "Failed to get asset file list.", e);
            }
            if (files != null) for (String filename : files) {
                if (filename.endsWith(".xml")) {
                    InputStream in = null;
                    OutputStream out = null;
                    try {
                        in = assetManager.open(filename);
                        File outFile = new File(PICTUREPATH, filename);
                        out = new FileOutputStream(outFile);
                        copyFile(in, out);
                    } catch (IOException e) {
                        Log.e("tag", "Failed to copy asset file: " + filename, e);
                    } finally {
                        if (in != null) {
                            try {
                                in.close();
                                in = null;
                            } catch (IOException e) {

                            }
                        }
                        if (out != null) {
                            try {
                                out.flush();
                                out.close();
                                out = null;
                            } catch (IOException e) {

                            }
                        }
                    }
                }
            }
        }

        public static void copyFile(InputStream in, OutputStream out) throws IOException {
            byte[] buffer = new byte[1024];
            int read;
            while((read = in.read(buffer)) != -1){
                out.write(buffer, 0, read);
            }
        }

    }