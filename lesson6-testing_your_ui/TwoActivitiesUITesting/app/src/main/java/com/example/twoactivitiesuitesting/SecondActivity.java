package com.example.twoactivitiesuitesting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    // Extra identifier
    public static final String EXTRA_REPLY = "com.example.twoactivitiesuitesting.extra.REPLY";

    private EditText mReply;

    private static final String LOG_TAG = SecondActivity.class.getSimpleName();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        mReply = findViewById(R.id.editText_second);

        /// Get data from the main activity

        // Get the intent that started this activity
        Intent intent = getIntent();

        // Set the text view in the second activity to display the message entered in the first activity
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        // Identify the text view
        TextView textMessage = findViewById(R.id.text_message);
        textMessage.setText(message);

        Log.d(LOG_TAG, "------");
        Log.d(LOG_TAG, "OnCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG_TAG,"OnResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "OnPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(LOG_TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(LOG_TAG, "OnRestart");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(LOG_TAG,"Saving instance state..." );
    }

    // This method sends the reply message entered in the second activity back to the first activity
    // The first activity calls startActivityForResult() to get data back from the second activity

    public void returnReply(View view) {

        // Get the string entered in the second activity
        String reply = mReply.getText().toString();

        // Create a new intent to send data back to main activity, DIFFERENT than the intent which started this activity
        // The return intent does not need a class or component name to end up in the right place; the Android system directs the response back to the originating Activity
        Intent replyIntent = new Intent();

        replyIntent.putExtra(SecondActivity.EXTRA_REPLY, reply);

        // set the Response code for the reply intent
        setResult(RESULT_OK, replyIntent);

        Log.d(LOG_TAG, "End Second Activity");

        // Close the second activity (and go back to the first)
        finish();

    }
}
