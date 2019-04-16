package com.example.implicit_intents;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText mWebsideEditText;
    private EditText mLocationEditText;
    private EditText mShareEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWebsideEditText = findViewById(R.id.website_edittext);

        mLocationEditText = findViewById(R.id.location_edittext);

        mShareEditText = findViewById(R.id.share_edittext);
    }

    public void openWebsite(View view) {

        // Get the text in the edit text view
        String url = mWebsideEditText.getText().toString();

        // Parse the String to a Uri object
        Uri webpage = Uri.parse(url);

        // Create a new implicit intent with the View action and the url data, to display the web page
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(webpage);

        // Create a chooser wrapper intent to display a chooser every time
        Intent chooserIntent = Intent.createChooser(intent, "Choose your browser:");

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(chooserIntent);
        }
        else {
            Log.d("ImplicitIntents", "Can't handle this!");
        }
    }

    public void openLocation(View view) {

        String loc = mLocationEditText.getText().toString();

        Uri addressUri = Uri.parse("geo:0,0?q=" + loc);

        Intent intent = new Intent(Intent.ACTION_VIEW, addressUri);

        if(intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        else {
            Log.d("ImplicitIntents", "Can't handle this intent!");
        }

    }

    public void shareText(View view) {

        String text = mShareEditText.getText().toString();

        String mimeType = "text/plain";

        ShareCompat.IntentBuilder.from(this).setType(mimeType).setChooserTitle(R.string.set_chooser_title).setText(text).startChooser();

    }
}
