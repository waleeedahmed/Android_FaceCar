package com.example.facecar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FailureActivity extends AppCompatActivity {

    Websockets webby = new Websockets();
    Button btnReturn03;

    private TextView resultView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_failure);
        resultView = findViewById(R.id.textView14);
        btnReturn03 = (Button) findViewById(R.id.button_return03);

        Intent fetchedIntent = getIntent();
        // getting the label
        int labelMessage = fetchedIntent.getIntExtra(loaderActivity.EXTRA_MESSAGE0, 0);
        int predictMessage = fetchedIntent.getIntExtra(loaderActivity.EXTRA_MESSAGE1, 0);
        resultView.setText("Image Label: " + labelMessage + " Prediction: " + predictMessage);
        webby.sendMessageFail();

        // Setting up event listeners
        // Return button will run returnMainIntent03()
        btnReturn03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnMainIntent03();

            }
        });

    }

    // Function to start intent to go back to main activity
    public void returnMainIntent03() {
        Intent returnMain03 = new Intent(this, MainActivity.class);
        returnMain03.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(returnMain03);


    }

    // Physical back button pressed to call returnMainIntent01() function
    @Override
    public void onBackPressed() {
        returnMainIntent03();

    }
}
