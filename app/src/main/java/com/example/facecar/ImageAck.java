package com.example.facecar;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.content.Intent;

// This activity is at work when "Success! Your image has been saved" is displayed
public class ImageAck extends MainActivity {

    // buttons on that activity for new photo and training
    public Button photoBtn;
    public Button trainBtn;
    public Button btnReturn01;

    public static String imagePath = "/storage/emulated/0/Android/data/com.example.facecar/files/Pictures/";

    /*
    // This code only used if needing to rotate snapped image
    public Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }
    */


    private static final String TAG = "ImageAck";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_image_ack);
        ImageView imgView = findViewById(R.id.imageView);



        // Fetching intent to extract image from mainActivity which is the homepage.
        Intent dirIntent = getIntent();
        Bundle extras = dirIntent.getExtras();
        String fetchedPhotoPath = extras.getString("EXTRA_MESSAGE");

        // Getting the orientation integer from MainActivity
        int orientation = extras.getInt("orient");
        //ExifInterface ei = extras.getParcelable("exif"); // code for image rotation

        Log.i(TAG,"fetchedPhotoPath is: " + fetchedPhotoPath);
        Log.i(TAG,"orientation integer is: " + orientation);

        // Displaying snapped image as a bitmap
        Bitmap bitmap = BitmapFactory.decodeFile(FaceDetection.detect(MainActivity.getImageSnap(), 0));
        imgView.setImageBitmap(bitmap);


        // CODE BELOW IS FOR IMAGE ROTATION
        /*
        Bitmap rotatedBitmap = null;
        switch (orientation) {

            case ExifInterface.ORIENTATION_ROTATE_90:
                rotatedBitmap = rotateImage(bitmap, 90);
                break;

            case ExifInterface.ORIENTATION_ROTATE_180:
                rotatedBitmap = rotateImage(bitmap, 180);
                break;

            case ExifInterface.ORIENTATION_ROTATE_270:
                rotatedBitmap = rotateImage(bitmap, 270);
                break;

            case ExifInterface.ORIENTATION_NORMAL:
            default:
                rotatedBitmap = bitmap;
        }

        imgView.setImageBitmap(rotatedBitmap);
        */

        btnReturn01 = (Button) findViewById(R.id.button_return00);


        // Setting up event listeners
        // Return button will run returnMainIntent01()
        btnReturn01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnMainIntent01();
            }
        });


        photoBtn = findViewById(R.id.button4); // "New Photo" button
        trainBtn = findViewById(R.id.button5); // "Train Model" button
        trainBtn.setText("Train Model");


        // Train Eigenfaces model if train button pressed
        trainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // only training, no predictions
                //trainBtn.setText("Training Now...");
                FaceRecognizer.beginRecognition("", 0);
                trainBtn.setText("Trained!");
            }
        });

        // take a new photo if new photo button pressed
        photoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent(v);

            }
        });
    }

    // Function to start intent to go back to main activity
    public void returnMainIntent01() {
        Intent returnMain01 = new Intent(this, MainActivity.class);
        returnMain01.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(returnMain01);


        }


    // Physical back button pressed to call returnMainIntent01() function
    @Override
    public void onBackPressed() {
        returnMainIntent01();

    }




}

    /* IMPLEMENTATION FOR BFMATCER

    public Bitmap drawMatches(String photoPath) {

        Mat img1 = Imgcodecs.imread(photoPath, 0);      // Sample Query gray scale Image
        Mat img2 = Imgcodecs.imread(imagePath + "JPEG_20190815_191746_27174374554425141.jpg", 0);    // Sample Train gray scale Image

        Log.i(TAG,"img1 is: " + img1);
        Log.i(TAG,"img2 is: " + img2);

        // initiate descriptors
        Mat des1 = new Mat();
        Mat des2 = new Mat();

        Log.i(TAG,"des1 is: " + des1);
        Log.i(TAG,"des2 is: " + des2);

        // Initiate key points
        MatOfKeyPoint kp1 = new MatOfKeyPoint();
        MatOfKeyPoint kp2 = new MatOfKeyPoint();


        // Creating an ORB object
        ORB customORB = ORB.create();

        Log.i(TAG,"orb obj created is: " + customORB);

        // Detect key points and compute their descriptors

        //customORB.detectAndCompute(img1, null, kp1, des1);
        //customORB.detectAndCompute(img2, null, kp2, des2);

        customORB.detect(img1, kp1);
        customORB.detect(img2, kp2);

        Log.i(TAG,"Made it past key point Detection");
        Log.i(TAG,"detected kp1 is: " + kp1);
        Log.i(TAG,"detected kp2 is: " + kp2);

        customORB.compute(img1, kp1, des1);
        customORB.compute(img2, kp2, des2);


        Log.i(TAG,"Successfully computed Key point descriptors");

        // Create matcher object
        BFMatcher bf = BFMatcher.create(NORM_HAMMING, true);

        Log.i(TAG,"Just created BFMatcher object >>>> " + bf);

        // Match these two key point sets
        //MatOfDMatch matches = new MatOfDMatch();


        List<MatOfDMatch> matches = new ArrayList<MatOfDMatch>();
        Log.i(TAG,"Made it past MatOfDMatch >>>> " + matches);

        Log.i(TAG,"des1 goes into bfmatcher as: " + des1);
        Log.i(TAG,"des2 goes into bfmatcher as: " + des2);

        //bf.match(des1, des2, matches);
        bf.knnMatch(des1, des2, matches, 5);




        Log.i(TAG,"BF.match complete!!!");


        // Draw output image
        Mat outputImg = new Mat();

        Log.i(TAG,"Drawing matches now!");
        Features2d.drawMatchesKnn(img1, kp1, img2, kp2, matches, outputImg);



        Log.i(TAG, "Matches drawn! Output image is: " + outputImg);

        // This part only saves the image, does not display it
        Imgcodecs.imwrite(imagePath + "result.jpg", outputImg);



        Bitmap bitmap1 = BitmapFactory.decodeFile(imagePath + "result.jpg");

        Log.i(TAG, "outputImg looks like: " + outputImg);
        Log.i(TAG, "bitmap1 attained image is: " + bitmap1);

        return bitmap1;

    }
    */