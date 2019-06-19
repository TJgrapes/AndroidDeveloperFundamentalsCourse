package com.example.powerreceiver;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private CustomReceiver customReceiver;

    // Action string for our custom local broadcast
    private static final String ACTION_CUSTOM_BROADCAST = BuildConfig.APPLICATION_ID + ".ACTION_CUSTOM_BROADCAST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create a new Receiver object
        customReceiver = new CustomReceiver();

        // Create an Intent Filter programatically
        // This will be used to set the actions that we want our receiver to listen for
        IntentFilter intentFilter = new IntentFilter();

        intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        intentFilter.addAction(Intent.ACTION_POWER_CONNECTED);

        // Register the receiver with our filter that listens for POWER_DISCONNECTED AND POWER_CONNECTED system broadcasts
        // onReceive will be called when a broadcast is received from this receiver
        // Calling registerReceiver() on this registers the the receiver with the Activity context- this means the app will receive the broadcast as long as the Activity is running
        this.registerReceiver(customReceiver, intentFilter);

        // Register the receiver with our custom local broadcast
        // onReceive will be called when a broadcast is received from this receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(customReceiver, new IntentFilter(ACTION_CUSTOM_BROADCAST));
    }

    @Override
    protected void onDestroy() {
        this.unregisterReceiver(customReceiver);

        // Unregister the bradcast
        LocalBroadcastManager.getInstance(this).unregisterReceiver(customReceiver);
        super.onDestroy();
    }

    public void sendCustomBroadcast(View view) {

        // onReceive is still called; it is just passed the custom intent action

        Intent customBroadcastIntent = new Intent(ACTION_CUSTOM_BROADCAST);

        LocalBroadcastManager.getInstance(this).sendBroadcast(customBroadcastIntent);

    }
}
