package com.example.facecar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class WarningActivity extends AppCompatActivity {

    private TextView clearDataTextView;
    private Button btnReturn00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warning);
        clearDataTextView = (TextView) findViewById(R.id.tvWarning);
        btnReturn00 = (Button) findViewById(R.id.button_return00);

        // Setting up event listeners
        // Return button will run returnMainIntent00()
        btnReturn00.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnMainIntent00();

            }
        });






        Bundle fetchClearDataStorageIntent = getIntent().getExtras();

        //clearDataTextView.setText(fetchClearDataStorageIntent.getString("testy01"));

        //clearDataTextView.setText();

    }

    // Function to start intent to go back to main activity
    public void returnMainIntent00() {
        Intent returnMain00 = new Intent(this, MainActivity.class);
        returnMain00.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(returnMain00);

    }

    // Physical back button pressed to call returnMainIntent01() function
    @Override
    public void onBackPressed() {
        returnMainIntent00();

    }

    /*

        Intent fetchedIntent = getIntent();
        // getting the label
        int labelMessage = fetchedIntent.getIntExtra(loaderActivity.EXTRA_MESSAGE0, 0);
        int predictMessage = fetchedIntent.getIntExtra(loaderActivity.EXTRA_MESSAGE1, 0);
        resultView.setText("Image Label: " + labelMessage + " Prediction: " + predictMessage);
        webby.sendMessageFail();

    }


     */


}
