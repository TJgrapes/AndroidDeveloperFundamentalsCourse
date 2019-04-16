package com.example.hellocompat;

import android.os.PersistableBundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView helloTextView;

    private String[] colorArray = {"red", "pink", "purple", "deep_purple",
            "indigo", "blue", "light_blue", "cyan", "teal", "green",
            "light_green", "lime", "yellow", "amber", "orange", "deep_orange",
            "brown", "grey", "blue_grey", "black" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helloTextView = findViewById(R.id.hello_textview);

        //restore the color state

        if(savedInstanceState != null) {
            helloTextView.setTextColor(savedInstanceState.getInt("color"));
        }
    }

    // implement onSaveInstanceState to save the color


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        // save the current text color
        // getCurrentTextColor is a method in the text view class that returns the current color of the text view
        outState.putInt("color", helloTextView.getCurrentTextColor());
    }

    public void changeColor(View view) {

        Random random = new Random();

        String colorName = colorArray[random.nextInt(20)];

        int colorResourceName = getResources().getIdentifier(colorName,
                "color", getApplicationContext().getPackageName());

        int colorRes = ContextCompat.getColor(this, colorResourceName);

        helloTextView.setTextColor(colorRes);

        }

    public void imageViewToast(View view) {

        Toast toast = Toast.makeText(this,"I am now a clickable view", Toast.LENGTH_SHORT);

        toast.show();

    }
}
