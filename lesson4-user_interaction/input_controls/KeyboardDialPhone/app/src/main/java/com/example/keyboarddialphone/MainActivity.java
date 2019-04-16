package com.example.keyboarddialphone;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText phoneEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phoneEditText = findViewById(R.id.phone_edit_text);

        // Set a listener for the phoneEditText view to detect when the send key is pressed
        if(phoneEditText != null) {
            phoneEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

                // This method handles the send key
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    boolean handled = false;

                    // If the pressed key matches the IME_ACTION_SEND (That is, we set the imeOptions attribute to send in xml), call the dial phone number method
                    if(actionId == EditorInfo.IME_ACTION_SEND) {
                        dialNumber();
                        handled = true;
                    }

                    return handled;
                }
            });

    }
}

    // Send the phone number entered to the Dialer
    private void dialNumber() {

        String phoneNum = null;

        // Get the phone number string and prefix it with tel:
        if(phoneEditText != null) phoneNum = "tel:" + phoneEditText.getText().toString();

        // Create an Implicit intent that with the dial action
        Intent intent = new Intent(Intent.ACTION_DIAL);

        // Set the data
        intent.setData(Uri.parse(phoneNum));

        if(intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        else {
            Log.d("ImplicitIntent", "Can't handle this!");
        }

    }
    }
