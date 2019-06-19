package com.example.powerreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * This class is created after we selected create new Broadcast Receiver
 */

public class CustomReceiver extends BroadcastReceiver {

    private static final String ACTION_CUSTOM_BROADCAST = BuildConfig.APPLICATION_ID + ".ACTION_CUSTOM_BROADCAST";


    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        String intentAction = intent.getAction();

        if(intentAction != null) {

            String toastMessage = "Unknown intent action";

            switch (intentAction) {
                // Have our receiver listen for power connected and power disconnected system broadcasts
                case Intent.ACTION_POWER_CONNECTED:
                    toastMessage = "Power connected!";
                    break;
                case Intent.ACTION_POWER_DISCONNECTED:
                    toastMessage = "Power Disconnected!";
                    break;
                // Have our receiver listen for our local broadcast that we defined
                case ACTION_CUSTOM_BROADCAST:
                    toastMessage = "Custom Local Broadcast Received";
                    break;
                default:
                    toastMessage = "Unknown Intent action";
                    break;
            }

            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show();
        }
    }
}
