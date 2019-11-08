package com.example.facecar;

import java.io.File;

public class deleteClass {

    // 2.2.2
    public static final String subject = "-subject";
    public static final int divider = 6;

    public deleteClass() {


    }


    // 2.2.1 function to delete files recursively working
    public void deletePictureRecursive(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory()) {
            for (File child : fileOrDirectory.listFiles()) {
                if (child.getName().endsWith(".jpg") || child.getName().endsWith(".yml") ) {
                    // do something
                    child.delete();

                }

            }
            // Do something here
            //fileOrDirectory.delete()
        } else {
            // Do something here
            //fileOrDirectory.delete();
        }
    }


    /*// 2.2.2
    // function to delete files in dataset. Only each image label at a time
    public void deleteDatasetRecursive(File fileOrDirectory) {

        File[] fileCountinArray = fileOrDirectory.listFiles();
        int fileCount = fileCountinArray.length;
        fileCount = fileCount / 6;

        String intSubject = Integer.toString(fileCount) + subject;

        if (fileOrDirectory.isDirectory()) {
            for (File child : fileOrDirectory.listFiles()) {
                if (child.getName().startsWith(intSubject)) {
                    // do something
                    child.delete();

                } else {

                }

            }
            // Do something here
            //fileOrDirectory.delete()
        } else {
            // Do something here
            //fileOrDirectory.delete();
        }
    } //*/

    // 2.2.3
    // function to delete files in dataset. Only one image label at a time

    // 2.2.4
    // TODO fix so that if directory is null, display a message saying no images in PICTUREPATH
    public void deleteDatasetRecursive(File fileOrDirectory) {

        File[] fileCountinArray = fileOrDirectory.listFiles();
        double fileCount = fileCountinArray.length;
        int finalFileCount = (int) Math.ceil(fileCount / divider);
        String intSubject = Integer.toString(finalFileCount) + subject;

        if (fileOrDirectory.isDirectory()) {
            for (File child : fileOrDirectory.listFiles()) {
                if (child.getName() != null) {
                    if (child.getName().startsWith(intSubject)) {
                        // do something
                        child.delete();

                    } else {

                    }

                } else {

                }

            }
            // Do something here
            //fileOrDirectory.delete()
        } else {
            // Do something here
            //fileOrDirectory.delete();
        }
    } //
}
