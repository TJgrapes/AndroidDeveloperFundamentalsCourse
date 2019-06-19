package com.example.twoactivitiesuitesting;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.twoactivitiesuitesting.extra.Message";

    private EditText mMessageEditText;

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    public static final int TEXT_REQUEST = 1;
    private TextView mReplyHeadTextView;
    private TextView mReplyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Saved instance state is the state the activity was in
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(LOG_TAG, "-------");
        Log.d(LOG_TAG, "onCreate");

        mMessageEditText = findViewById(R.id.editText_main);
        mReplyHeadTextView = findViewById(R.id.text_header_reply);
        mReplyTextView = findViewById(R.id.text_message_reply);

        //  The savedInstanceState is null when the activity is first created
        // If there was state, we want to restore it from savedInstance state so that when the activity is destroyed and recreated, the reply header text view and reply text views show, along with its text
        // See onSaveInstanceState() for what gets saved.
        if (savedInstanceState != null) {

            Log.d(LOG_TAG,"Restoring instance state..." );
            boolean isVisible = savedInstanceState.getBoolean("reply_visible");
            // Show both the header and the message views. If isVisible is
            // false or missing from the bundle, use the default layout.
            if (isVisible) {
                mReplyHeadTextView.setVisibility(View.VISIBLE);
                mReplyTextView.setText(savedInstanceState.getString("reply_text"));
                mReplyTextView.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "OnResume");
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
    // We want to save the state of the text of mReplyTextView for when a configuration change occurs
    // outState is the new savedInstanceState
    protected void onSaveInstanceState(Bundle outState) {

        Log.d(LOG_TAG,"Saving Instance State..." );
        // if the heading is visible, we know a reply message was entered, so we must make sure to save the data in the reply text view and both the header and reply text views by setting them to visible in the onCreate method
        if (mReplyHeadTextView.getVisibility() == View.VISIBLE) {
            outState.putBoolean("reply_visible", true);
            outState.putString("reply_text", mReplyTextView.getText().toString());
        }

        super.onSaveInstanceState(outState);
    }

    public void launchSecondActivity(View view) {
        Log.d(LOG_TAG, "Button clicked!");

        /// Create an intent to launch the second activity and sends the message entered in the edit text field to the second activity
        Intent intent = new Intent(this, SecondActivity.class);

        String message = mMessageEditText.getText().toString();

        // Put the entered message in the intent, using EXTRA_MESSAGE as the identifier
        intent.putExtra(EXTRA_MESSAGE, message);

        // Start the intent with a request code
        // The request code is used to differentiate returned results
        startActivityForResult(intent, TEXT_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // If we are processing text, we want to set that returned text to the text view
        if (requestCode == TEXT_REQUEST) {
            if (resultCode == RESULT_OK) {
                // data is the return intent that we created in returnReply() method
                String reply = data.getStringExtra(SecondActivity.EXTRA_REPLY);
                mReplyTextView.setText(reply);
                mReplyTextView.setVisibility(View.VISIBLE);
                mReplyHeadTextView.setVisibility(View.VISIBLE);

            }
        }
    }
}
