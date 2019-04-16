package com.example.dialogforalert;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showAlert(View view) {

        // Create the Alert Dialog object using the Builder class
        AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(MainActivity.this);

        // Set the title and the message
        myAlertBuilder.setTitle("Alert");
        myAlertBuilder.setMessage("Click OK to continue, or Cancel to stop:");

        /// Set the dialog buttons

        // Set the positive button
        myAlertBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            // Action to perform when the positive button is clicked
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"Pressed OK" ,Toast.LENGTH_SHORT ).show();
            }
        });

        // Set the negative button
        myAlertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"Pressed Cancel" ,Toast.LENGTH_SHORT ).show();
            }
        });

        // Show the Alert
        myAlertBuilder.show();



    }
}
