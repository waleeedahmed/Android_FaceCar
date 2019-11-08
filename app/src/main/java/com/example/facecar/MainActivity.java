package com.example.facecar;

// necessary classes to import
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import androidx.exifinterface.media.ExifInterface;

import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.getkeepsafe.relinker.ReLinker;


import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;



// Class for setting up FaceCar home page
public class MainActivity extends AppCompatActivity {

    public Button scanBtn;
    public Button registerBtn;
    public Button clearBtn;
    Context context = this;
    public static final String EXTRA_MESSAGE = "com.example.facecar.MESSAGE";
    public static final String EXTRA_PHOTOSTR = "com.example.facecar.PHOTOSTR";
    private static final String TAG = "MainActivity";
    private static String currentPhotoPath;

    Websockets webby = new Websockets();

    // 2.2.4
    deleteClass deletey = new deleteClass();

    // Attempting to file delete
    // 2.2.1
    public static final String PICTUREPATH = "/storage/emulated/0/Android/data/com.example.facecar/files/Pictures/";
    public static final String DATASETPATH = "/storage/emulated/0/Android/data/com.example.facecar/files/Pictures/faceDataset/";
    private static final String CASCADEFILE = "haarcascade_frontalface_alt.xml";

    // 2.2.3
    public static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 0;


    // 2.2.4
    copyAssets copyassets = new copyAssets();



    @Override
    // This method fires up when homepage activity comes on screen
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Loading all libraries using relinker
        ReLinker.Logger logger = new ReLinker.Logger() {
            @Override
            public void log(String message) {
                Log.v("HODOR", "(hold the door) " + message);
            }
        };
        ReLinker.log(logger).recursively().loadLibrary(context, "opencv_core");
        ReLinker.log(logger).recursively().loadLibrary(context, "jniopencv_core");
        ReLinker.log(logger).recursively().loadLibrary(context, "jniopencv_imgcodecs");
        ReLinker.log(logger).recursively().loadLibrary(context, "opencv_imgcodecs");
        ReLinker.log(logger).recursively().loadLibrary(context, "jniopencv_imgproc");
        ReLinker.log(logger).recursively().loadLibrary(context, "opencv_imgproc");

        setContentView(R.layout.activity_main);


        scanBtn = findViewById(R.id.button2);  // setting up scan button
        registerBtn = findViewById(R.id.button);  // setting up register button
        clearBtn = findViewById(R.id.button3); // setting up clear data button

        // Setting up event listeners
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent(v);
            }
        });

        // Setting up event listeners
        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchScanPhotoIntent(v);
            }
        });


        // Setting up event listeners
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearDataStorageIntent(v);

            }
        });

        // Had extra code for displaying an alert dialog but didn't work
        //pd = new ProgressDialog(this);
        //AlertDialog.Builder build = new AlertDialog.Builder(this);
        //build.setCancelable(false);
        //build.setView(R.layout.activity_main);
        //dg = build.create();

        // 2.2.5
        checkingPermission();







    } // End onCreate()


    /*
    // Had extra code for displaying an alert dialog but didn't work
    // Method to show Progress bar
                private void showProgressDialogWithTitle(String title,String substring) {
                    pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    //Without this user can hide loader by tapping outside screen
                    pd.setCancelable(false);
                    //Setting Title
                    pd.setTitle(title);
                    pd.setMessage(substring);
                    pd.show();

                }

                // Method to hide/ dismiss Progress bar
                private void hideProgressDialogWithTitle() {
                    pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    pd.dismiss();
                }
                */


    // onDestroy method
    @Override
    protected void onDestroy() {
        super.onDestroy();
        webby.onDestroy();

    }


    // *File Management Subsystem Code*
    //String currentPhotoPath;
    String imageFileName;
    ExifInterface ei;
    int orientation;


    private File createImageFile() throws IOException {
        // Creating a file name for images to be saved
        String timeStamp = new SimpleDateFormat("HH:mm:ss.SSS").format(new Date());
        imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,    /* prefix */
                ".jpg",     /* suffix */
                storageDir        /* directory */

        );

        // Save a file: path for use with ACTION_VIEW intents

        currentPhotoPath = image.getAbsolutePath();
        //String[] currPhotoPath = currentPhotoPath.split("/");


        return image;
    }

    public static String getImageSnap() {
        return currentPhotoPath;
    }


    /*
     Method below is called when the user presses the 'Register' button
     Initiating intent to begin external camera access request
     view parameter inside the function initiates the event handle for button press
    */

    static final int REQUEST_IMAGE_CAPTURE = 1;
    Uri photoURI;

    public void dispatchTakePictureIntent(View v) {
        Log.i(TAG, "dispatchTakePictureIntent entered ");
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Log.i(TAG, "takePicture intent contains: " + takePictureIntent);

        // Making sure a camera app exists to handle to delegation request
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Creating the file where photos meant to be stored
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // File cannot be created error message
                Log.i(TAG,"IOException: " + ex);
            }
            // If file creation was successful
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(this,"com.example.android.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);


                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                Log.i(TAG,"Picture successfully saved: " + takePictureIntent);
                Log.i(TAG,"PhotoURI is: " + photoURI);
            }
        }
    }

    // Method below kick starts when external camera app gets back with the image
    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Run block below if Register was pressed
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            // Fetching image from external app
            //Intent fetchedInt = getIntent();
            Uri fetchedURI = photoURI;
            Log.i(TAG,"FETCHED URI IS >>>>> " + fetchedURI);


            // Below code is only needed if wanting to rotate image, see if its doable.
            InputStream IS;
            try {
                IS = getContentResolver().openInputStream(photoURI);
                ei = new ExifInterface(IS);
            } catch (IOException e) {
                // Handle errors
            }
            orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);
            Log.i(TAG,"EXIF Data Object: " + ei);


            /*
               Initiating a new intent to forward the image towards 'ImageAck' activity
               because we want to display the success page and the image goes over there.
            */

            Intent forwardImg = new Intent(this, ImageAck.class);
            Bundle extras = new Bundle();
            extras.putString("EXTRA_MESSAGE", currentPhotoPath);
            extras.putInt("orient", orientation);
            Log.i(TAG,"orientation integer put in intent is: " + orientation);
            forwardImg.putExtras(extras);
            Log.i(TAG,"forwardImg contains" + currentPhotoPath);
            startActivity(forwardImg);

        }
        // Run block below if Begin Scan was pressed
        else if (requestCode == REQUEST_IMAGE_SCAN) {

            Intent loaderIntent = new Intent(this, loaderActivity.class);
            startActivity(loaderIntent);

            // Tried other code to show progress dialog but didn't work
            //dg.show();
            //showProgressDialogWithTitle("OpenCv Recognizer", "Working on it...");
            //FaceRecognizer.beginRecognition(FaceDetection.detect(currentPhotoPath, 1));
            //hideProgressDialogWithTitle();
            //dg.dismiss();
        }
    }


    // This int goes into the result activity for begin scan
    static final int REQUEST_IMAGE_SCAN = 2;

    // Method below fires when Begin Scan pressed
    public void dispatchScanPhotoIntent(View v) {

        Log.i(TAG, "dispatchScanPhotoIntent entered ");
        Intent scanIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Log.i(TAG, "scanIntent contains: " + scanIntent);

        // Making sure a camera app exists to handle to delegation request
        if (scanIntent.resolveActivity(getPackageManager()) != null) {
            // Creating the file where photos meant to be stored
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // File cannot be created error message
                Log.i(TAG,"IOException: " + ex);
            }
            // If file creation was successful
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(this,"com.example.android.fileprovider", photoFile);
                scanIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                startActivityForResult(scanIntent, REQUEST_IMAGE_SCAN);
                Log.i(TAG,"Picture successfully saved: " + scanIntent);
                Log.i(TAG,"PhotoURI is: " + photoURI);
            }
        }
    }




    // 2.2.1 It works
    public void clearDataStorageIntent(View v) {


        Log.i(TAG, "running clear data now!");
        Intent clearDataIntent = new Intent(this, ClearActivity.class);

        // 2.2.1
        deletey.deletePictureRecursive(new File(PICTUREPATH));

        // 2.2.2
        deletey.deletePictureRecursive(new File(DATASETPATH));

        startActivity(clearDataIntent);

    }

    // 2.2.3
    // Here, thisActivity is the current activity
    public void checkingPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

                // MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
            createNecessaryFileFolder();


        }

    }

    // 2.2.3 onRequestPermissionResult
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                    // 2.2.5
                    createNecessaryFileFolder();

                } else {
                    Toast.makeText(this, "For full functionality, please allow storage permission.",Toast.LENGTH_LONG).show();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }


    // 2.2.5
    public void createNecessaryFileFolder() {

        // 2.2.4 Create necessary folders com.example.com/files/Pictures/ on startup and check
        File creatingDataFolder = new File(DATASETPATH);
        if (!creatingDataFolder.isDirectory()) {
            creatingDataFolder.mkdirs();

        }

        // Copying CASCADEFILE from assets folder into directory as required
        File xmlboy = new File(PICTUREPATH + CASCADEFILE);

        if (xmlboy.getName() != CASCADEFILE) {
            copyassets.copyAssets(this);

        }

    }

}