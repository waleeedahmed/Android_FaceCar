package com.example.facecar;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;

// All necessary imports for face detector
import static org.bytedeco.javacpp.helper.opencv_objdetect.cvHaarDetectObjects;
import static org.bytedeco.javacpp.opencv_core.cvClearMemStorage;
import static org.bytedeco.javacpp.opencv_core.CvMemStorage;
import static org.bytedeco.javacpp.opencv_core.cvCloneImage;
import static org.bytedeco.javacpp.opencv_core.cvCopy;
import static org.bytedeco.javacpp.opencv_core.cvCreateImage;
import static org.bytedeco.javacpp.opencv_core.cvGetSeqElem;
import static org.bytedeco.javacpp.opencv_core.cvGetSize;
import static org.bytedeco.javacpp.opencv_core.cvLoad;
import static org.bytedeco.javacpp.opencv_core.CvRect;
import static org.bytedeco.javacpp.opencv_core.CvSeq;
import static org.bytedeco.javacpp.opencv_core.IplImage;
import static org.bytedeco.javacpp.opencv_core.cvRect;
import static org.bytedeco.javacpp.opencv_core.cvResetImageROI;
import static org.bytedeco.javacpp.opencv_core.cvSetImageROI;
import static org.bytedeco.javacpp.opencv_objdetect.CvHaarClassifierCascade;
import static org.bytedeco.javacpp.opencv_imgcodecs.cvLoadImage;
import static org.bytedeco.javacpp.opencv_imgcodecs.cvSaveImage;
import static org.bytedeco.javacpp.opencv_imgproc.cvCvtColor;
import static org.bytedeco.javacpp.opencv_imgproc.cvResize;
import static org.bytedeco.javacpp.opencv_imgproc.CV_BGR2GRAY;
import static org.bytedeco.javacpp.opencv_imgproc.CV_INTER_LINEAR;
import static org.bytedeco.javacpp.opencv_objdetect.CV_HAAR_DO_ROUGH_SEARCH;
import static org.bytedeco.javacpp.opencv_core.IPL_DEPTH_8U;
import static org.bytedeco.javacpp.opencv_objdetect.CV_HAAR_DO_CANNY_PRUNING;
import static org.bytedeco.javacpp.opencv_imgproc.cvEqualizeHist;
import static org.bytedeco.javacpp.opencv_imgproc.cvRectangle;


public class FaceDetection {

    private static IplImage temp;
    private static final String TAG = "FaceDetection";
    public static final String imagePath = "/storage/emulated/0/Android/data/com.example.facecar/files/Pictures/";
    public static final String imagePath1 = "/storage/emulated/0/Android/data/com.example.facecar/files/Pictures";
    public static final String faceDb = "/storage/emulated/0/Android/data/com.example.facecar/files/Pictures/faceDataset";
    public static final String faceImage = "1-subject_01.jpg";
    public static final String faceDbAndImage = faceDb + "/" + faceImage;
    private static final int SCALE = 2; // scaling factor to reduce size of input image

    // cascade definition for face detection
    private static final String CASCADE_FILE = "haarcascade_frontalface_alt.xml";

    //private static final String OUT_FILE = "markedFaces.jpg";
    public static String OUT_FILE ;

    // TODO 2.2.5 + 2.2.6
    private static double dbWidth;
    private static double dbHeight;
    public static int intWidth;
    public static int intHeight;


    public static String detect(String path, int num)
    {
        // load an image
        IplImage origImg = cvLoadImage(path);
        Log.i(TAG, "OrigImg contains: " + origImg);

        // convert to grayscale
        IplImage grayImg = cvCreateImage(cvGetSize(origImg), IPL_DEPTH_8U, 1);
        cvCvtColor(origImg, grayImg, CV_BGR2GRAY);

        // Downsize the image according to SCALE variable.
        IplImage smallImg = IplImage.create(grayImg.width()/SCALE,
                grayImg.height()/SCALE, IPL_DEPTH_8U, 1);
        cvResize(grayImg, smallImg, CV_INTER_LINEAR);


        // Equalize the small gray scale (optional)
        //cvEqualizeHist(smallImg, smallImg);

        Log.i(TAG, "smallImg contains: " + smallImg);

        // create temp storage, used during object detection
        CvMemStorage storage = CvMemStorage.create();

        // instantiate a classifier cascade for face detection
        CvHaarClassifierCascade cascade = new CvHaarClassifierCascade(cvLoad(imagePath + CASCADE_FILE));

        Log.i(TAG, "haar classifier cascade object contains: " + cascade);


        CvSeq faces = cvHaarDetectObjects(smallImg, cascade, storage, 1.1, 5, //CV_HAAR_DO_CANNY_PRUNING);
         CV_HAAR_DO_ROUGH_SEARCH);
        // 0);

        cvClearMemStorage(storage);


        // get CvRectangle where the face is in the image
        int total = faces.total();
        for (int i = 0; i < total; i++) {
            CvRect r = new CvRect(cvGetSeqElem(faces, i));
            //cvRectangle(smallImg, cvPoint( r.x()*SCALE, r.y()*SCALE),    // undo the scaling
            //        cvPoint( (r.x() + r.width()*SCALE), (r.y() + r.height())*SCALE),
            //        CvScalar.YELLOW, 6, CV_AA, 0);


            // This piece of code checks if 1-subject_01.jpg is in file so that
            // this can grab its resolution to be consistent.
            // If 1-subject_01.jpg is not in the dataset folder
            // it will create an image and generate an image resolution
            File file = new File(faceDbAndImage);
            if (file.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(faceDbAndImage);
                intHeight = bitmap.getHeight();
                intWidth = bitmap.getWidth();

            } else {
                // Do something else.
                // TODO 2.2.5 + 2.2.6
                dbWidth = ((double) r.width()/ 100);
                dbHeight = ((double) r.height()/ 100);
                Log.i(TAG, "r height and width are: " + r.height() + "x" + r.width());
                Log.i(TAG, "double height and width are: " + dbHeight + "x" + dbWidth);

                intWidth = (int) Math.round(dbWidth) * 100;
                intHeight = (int) Math.round(dbHeight) * 100;
            }


            Log.i(TAG, "int height and width are: " + intHeight + "x" + intWidth);


            // Cropping Region of Interest from original image.
            // was originally r.width() and r.height()
            // Change to 175x175 for Joseph
            // Change to 300x300 for Tam

            cvSetImageROI(smallImg, cvRect(r.x(), r.y(), intHeight, intWidth));
            //cvSetImageROI(smallImg, cvRect(r.x(), r.y(), 200, 200));

            // 3 for colored image, 1 for gray scale smallImg
            temp = cvCreateImage(cvGetSize(smallImg), IPL_DEPTH_8U, 1);

            Log.i(TAG, "temp contains: " + temp);
            cvCopy(smallImg, temp, null);
            cvResetImageROI(smallImg);
            smallImg = cvCloneImage(temp);

        }


        if (total > 0) {
            //Log.i(TAG, "orig image contains: " + origImg);
            Log.i(TAG, "in the end, smallImg now contains: " + smallImg);


            // Only take in those files that end with jpg, png or pgm
            FilenameFilter imgFilter = new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    name = name.toLowerCase();
                    return name.endsWith(".jpg") || name.endsWith(".pgm") || name.endsWith(".png");
                }
            };


            // Locating the dataset directory
            File f = new File(faceDb);
            //File f = new File("/storage/emulated/0/Android/data/com.example.facecar/files/Pictures");


            // fetching all images from the dataset directory
            File[] fileArray = f.listFiles(imgFilter);

            // 2.2.4
            if (fileArray.length != 0) {
                Log.i(TAG, "Array of files >>>> " + fileArray);

                // Sort those images
                Arrays.sort(fileArray);

                // Get the last file name so we can decide on a new file name according to that
                String lastFileName = fileArray[fileArray.length - 1].getName();
                Log.i(TAG, "lastFileName in FaceDB is: " + lastFileName);

                // Get the last file's label
                String[] arrOfString = lastFileName.split("-");
                int number = Integer.parseInt(arrOfString[0]);

                // Splitting for image subject number
                String[] arrOfString2 = lastFileName.split("[_.]");
                Log.i(TAG, "arrOfString2 looks like: " + arrOfString2[0] + arrOfString2[1] + arrOfString2[2]);

                // number2 is the subject number
                int number2 = Integer.parseInt(arrOfString2[1]);

                // 6 below means you can take 6 images before label shifts to a new value, change 6 to a bigger int if you want
                // more images to take in the Register function
                if (number2 != 1 && number2 >= 6) {
                    OUT_FILE = "/" + Integer.toString(number + 1) + "-subject_01.jpg";
                } else {
                    OUT_FILE = "/" + Integer.toString(number) + "-subject_0" + Integer.toString(number2 + 1) + ".jpg";
                }
            } else {
                OUT_FILE = "/1-subject_01.jpg";
            }

            Log.i(TAG, "OUTPUT file becomes: " + OUT_FILE);

            // Save image in dataset if registering, otherwise save in pictures directory if Begin Scan
            if (num == 0) {
                cvSaveImage(faceDb + OUT_FILE, smallImg);
            } else cvSaveImage(imagePath1 + OUT_FILE, smallImg);
        }

        // Return directory based on whether detection was used for Register or Begin Scan
        if (num == 0) { // Register
            return faceDb + OUT_FILE;
        } else return imagePath1 + OUT_FILE; // Begin Scan

    } // end of detect method
}  // end of FaceDetection class