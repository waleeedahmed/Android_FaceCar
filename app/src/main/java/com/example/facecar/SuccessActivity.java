package com.example.facecar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SuccessActivity extends AppCompatActivity {

    Button btnReturn02;

    Websockets webby = new Websockets();

    private TextView resultView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        resultView = findViewById(R.id.textView9);
        btnReturn02 = (Button) findViewById(R.id.button_return02);

        Intent fetchedIntent = getIntent();
        // getting the label
        int labelMessage = fetchedIntent.getIntExtra(loaderActivity.EXTRA_MESSAGE0, 0);
        int predictMessage = fetchedIntent.getIntExtra(loaderActivity.EXTRA_MESSAGE1, 0);
        resultView.setText("Image Label: " + labelMessage + " Prediction: " + predictMessage);
        webby.sendMessageSuccess();

        // Setting up event listeners
        // Return button will run returnMainIntent02()
        btnReturn02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnMainIntent02();
            }
        });

    }

    // Function to start intent to go back to main activity
    public void returnMainIntent02() {
        Intent returnMain02 = new Intent(this, MainActivity.class);
        returnMain02.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(returnMain02);


    }

    // Physical back button pressed to call returnMainIntent01() function
    @Override
    public void onBackPressed() {
        returnMainIntent02();

    }
}
