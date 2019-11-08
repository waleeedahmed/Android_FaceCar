package com.example.facecar;

import android.util.Log;
import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_face;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.IntBuffer;
import java.util.Arrays;

import static org.bytedeco.javacpp.opencv_core.Mat;
import static org.bytedeco.javacpp.opencv_core.MatVector;
import static org.bytedeco.javacpp.opencv_core.CV_32SC1;
import static org.bytedeco.javacpp.opencv_face.createEigenFaceRecognizer;
import static org.bytedeco.javacpp.opencv_face.createFisherFaceRecognizer;
import static org.bytedeco.javacpp.opencv_imgcodecs.IMREAD_GRAYSCALE;
import static org.bytedeco.javacpp.opencv_imgcodecs.imread;



public class FaceRecognizer extends FaceDetection {

    private static final String TAG = "FaceRecognizer";


    // Second argument > int 0 = train only || int 1 = predict only based on trained model
    public static int[] beginRecognition(String path, int number) {


        int arr[] = new int[2];
        String trainingDir = faceDb;
        Log.i(TAG, "Training Dir is " + trainingDir);

        // Getting the training directory of Pics
        File root = new File(trainingDir);

        // Only fetch image files
        FilenameFilter imgFilter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                name = name.toLowerCase();
                return name.endsWith(".jpg") || name.endsWith(".pgm") || name.endsWith(".png");
            }
        };

        // List those photos into an array
        File[] imageFiles = root.listFiles(imgFilter);

        MatVector images = new MatVector(imageFiles.length);

        // Declare labels Matrix
        Mat labels = new Mat(imageFiles.length, 1, CV_32SC1);
        IntBuffer labelsBuf = labels.createBuffer();
        Arrays.sort(imageFiles);

        int counter = 0;

        Log.i(TAG, "Organising Labels for training preparation...");
        for (File image : imageFiles) { // for each photo

            Mat img = imread(image.getAbsolutePath(), IMREAD_GRAYSCALE);

            int label = Integer.parseInt(image.getName().split("\\-")[0]); // Get the label

            images.put(counter, img);

            labelsBuf.put(counter, label);

            counter++;
        }

        // These are the types of recognizers OpenCv offers, You can try other recognizers as well, They may work better possibly.
        //opencv_face.FaceRecognizer faceRecognizer = createFisherFaceRecognizer();
        opencv_face.FaceRecognizer faceRecognizer = createEigenFaceRecognizer();
        // FaceRecognizer faceRecognizer = LBPHFaceRecognizer.create();

        // Code from a previous version
            //Log.i(TAG, "Now beginning Training.......");
            //faceRecognizer.train(images, labels);
            //faceRecognizer.save();
            //Log.i(TAG, "Training Complete!!!!");
            //IntPointer label = new IntPointer(1);
            //DoublePointer confidence = new DoublePointer(1);
            //faceRecognizer.predict(testImage, label, confidence);
            //faceRecognizer.predict_label(testImage);
            //int predictedLabel = label.get(0);
            //int predictedLabel = faceRecognizer.predict_label(testImage);
            //Double predictedConfidence = confidence.get();


        if (number == 0) { // Executed when training only
            Log.i(TAG, "Now beginning Training.......");
            faceRecognizer.train(images, labels);
            Log.i(TAG, "Training Complete!!!!");
            faceRecognizer.save(FaceDetection.imagePath1 + "/eigenfaces.yml");
            Log.i(TAG, "model trained and saved!!!");

        } else { // Executed when pre-trained, only predicting

            IntPointer label = new IntPointer(1);
            DoublePointer confidence = new DoublePointer(1);
            // load the yml file
            Log.i(TAG, "Loading saved YML file");
            faceRecognizer.load(FaceDetection.imagePath1 + "/eigenfaces.yml");
            Log.i(TAG, "model successfully loaded >>>>>><<<<<<");
            Mat testImage = imread(path, IMREAD_GRAYSCALE); // gray scaling again after detection, possible warning!
            Log.i(TAG, "TestImage successfully taken in, testImage has: " + testImage);
            faceRecognizer.predict(testImage, label, confidence);
            int predictedLabel = label.get(0);
            Double predictedConfidence = confidence.get();
            Log.i(TAG, "Predicted label for our test image is: " + predictedLabel);
            Log.i(TAG, "Predicted confidence for our test image is: " + predictedConfidence); // threshold 6000-6200.
            arr[0] = predictedLabel;
            arr[1] = (int) Math.round(predictedConfidence);
        }

        // This will go to the UI to be displayed in another activity.
        return arr;
    }
}

