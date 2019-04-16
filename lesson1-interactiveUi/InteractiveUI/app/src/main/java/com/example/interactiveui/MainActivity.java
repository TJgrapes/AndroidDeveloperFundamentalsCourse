package com.example.interactiveui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private int clickCounter = 0;
    private TextView counterDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_linearlayout);

        counterDisplay = findViewById(R.id.show_count);
    }

    public void showToast(View view) {
        Toast toast = Toast.makeText(this, R.string.hello_toast,Toast.LENGTH_SHORT );
        toast.show();
    }

    public void countUp(View view) {
        clickCounter++;
        if(counterDisplay != null) {
            counterDisplay.setText(Integer.toString(clickCounter));
        }
    }
}
