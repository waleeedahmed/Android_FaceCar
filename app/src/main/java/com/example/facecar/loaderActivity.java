package com.example.facecar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

public class loaderActivity extends AppCompatActivity {

    // Context of the above class
    Context context = this;

    // These messages are sent to the success/failure screens for label and prediction
    public static final String EXTRA_MESSAGE0 = "com.example.facecar.MESSAGE0";
    public static final String EXTRA_MESSAGE1 = "com.example.facecar.MESSAGE1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loader);

        // Work on training in the background without lagging the application UI
        new AsyncCaller().execute();
    }

    // Train the algorithm while displaying the progress screen, using asynchronous Java.
    private class AsyncCaller extends AsyncTask<Void,Void,int[]> {
        @Override
        protected int[] doInBackground(Void... params) {
            // predict only, not train, that's why begin recognition has number as 1 in its second argument
            return FaceRecognizer.beginRecognition(FaceDetection.detect(MainActivity.getImageSnap(), 1), 1);
        }

        // Once training and recognition is complete....
        protected void onPostExecute(int[] result) {
            // call success or failure activity depending on the prediction

            // Assuming threshold to be 7000, Change threshold here if need be
            if (result[1] < 6000) {
                // generate success UI
                Intent notifIntent = new Intent(context, SuccessActivity.class);
                notifIntent.putExtra(EXTRA_MESSAGE0, result[0]);
                notifIntent.putExtra(EXTRA_MESSAGE1, result[1]);
                startActivity(notifIntent);

            } else {
                // generate failure UI
                Intent notifIntent = new Intent(context, FailureActivity.class);
                notifIntent.putExtra(EXTRA_MESSAGE0, result[0]);
                notifIntent.putExtra(EXTRA_MESSAGE1, result[1]);
                startActivity(notifIntent);
            }
        }
    }
}
