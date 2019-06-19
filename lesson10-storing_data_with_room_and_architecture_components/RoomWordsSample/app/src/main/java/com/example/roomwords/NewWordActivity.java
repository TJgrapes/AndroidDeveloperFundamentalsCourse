package com.example.roomwords;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewWordActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY =
            "com.example.android.roomwordssample.REPLY";

    private EditText mEditWordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_word);

        mEditWordView = findViewById(R.id.edit_word);
        final Button button = findViewById(R.id.button_save);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             *
             * Create an Intent that will sent the word back to the Main Activity, where it will then be added to the database
             *
             */
            public void onClick(View v) {
                Intent replyIntent = new Intent();

                // Handle the case if there is no word entered
                if (TextUtils.isEmpty(mEditWordView.getText())) {

                    setResult(RESULT_CANCELED, replyIntent);

                }

                // If there is text entered, add it to the intent to send to the Main Activity
                else {

                    String word = mEditWordView.getText().toString();
                    replyIntent.putExtra(EXTRA_REPLY, word);
                    setResult(RESULT_OK, replyIntent);

                }

                // Call finish to go back to main activity
                finish();
            }
        });
    }
}
