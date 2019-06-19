package com.example.notifyme;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button button_notification_1;
    private Button button_notification_2;

    private Button button_dismiss_notification_1;
    private Button button_dismiss_notification_2;
    private Button button_dismiss_all_notifications;

    private Button button_update_notification_1;
    private Button button_update_notification_2;

    // Notification Channel ID
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";

    // Notification IDs
    private static final int NOTIFICATION_ID_1 = 0;
    private static final int NOTIFICATION_ID_2 = 1;

    private NotificationManager notificationManager;

    // Identifier that represents the Notification Two Action
    private static final String ACTION_UPDATE_NOTIFICATION_TWO = "com.example.android.notifyme.ACTION_UPDATE_NOTIFICATION";

    private NotificationReceiver notificationReceiver = new NotificationReceiver();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_notification_1 = findViewById(R.id.notification_1_button);

        button_notification_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendNotification();

            }
        });

        button_notification_2 = findViewById(R.id.notification_2_button);

        button_notification_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendNotification2();

            }
        });

        button_dismiss_notification_1 = findViewById(R.id.dismiss_notification_1_button);
        button_dismiss_notification_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismissNotificationOne();

            }
        });

        button_dismiss_notification_2 = findViewById(R.id.dismiss_notification_2_button);
        button_dismiss_notification_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismissNotificationTwo();

            }
        });

        button_dismiss_all_notifications = findViewById(R.id.dismiss_all_notifications_button);
        button_dismiss_all_notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dismissNotifications();

            }
        });

        button_update_notification_1 = findViewById(R.id.update_notification_1_button);
        button_update_notification_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateNotificationOne();

            }
        });

        button_update_notification_2 = findViewById(R.id.update_notification_2_button);
        button_update_notification_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateNotificationTwo();

            }
        });

        // When the app is first created, only enable notification button and disable the update and disable buttons
        setNotificationOneButtonState(true, false, false);
        setNotificationTwoButtonState(true, false, false);

        createNotificationChannel();

        registerReceiver(notificationReceiver, new IntentFilter(ACTION_UPDATE_NOTIFICATION_TWO));

    }




    public void sendNotification() {

        NotificationCompat.Builder notification = getNotificationBuilder();

        notificationManager.notify(NOTIFICATION_ID_1, notification.build());

        // When the notification button is clicked, enable the update and dismiss buttons and disable the notification button
        setNotificationOneButtonState(false, true, true);


    }

    private void sendNotification2() {

        // Get the notification object we created
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder2();

        // To deliver the notification, call notify()
        // Use the NotificationManager object to call notify(), passing in the ID and the notification object
        notificationManager.notify(NOTIFICATION_ID_2, notifyBuilder.build());

        setNotificationTwoButtonState(false, true, true);


    }

    /// Set up the notification channel and its default settings
    public void createNotificationChannel() {

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        /// Create a notification channel

        // Notification Channels are only available in API 26 and higher; Added a check before creating the notification channel
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            // Create a notification channel with an id, name that will be displayed, and importance of notifications from this channel
            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID, "Mascot Notification", NotificationManager.IMPORTANCE_HIGH);

            // Set the notification channel's initial settings; This will set the initial settings for the notifications in this channel
            notificationChannel.enableLights(true);
            // Sets the LED icon indicator color
            notificationChannel.setLightColor(Color.YELLOW);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notification from Mascot");

            // Create the notification channel
            notificationManager.createNotificationChannel(notificationChannel);



        }

    }

    /// Create the notification and set its default values
    private NotificationCompat.Builder getNotificationBuilder() {


        /// Create an Intent (the content intent) that will occur when the notification is tapped
        // Open the Main Activity

        Intent contentIntent = new Intent(this, MainActivity.class);

        // Wrap the intent in an explicit intent, which allows the Intent to be launched by the system sometime in the future (when the notification is tapped)
        PendingIntent explicitIntent = PendingIntent.getActivity(this, NOTIFICATION_ID_1, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        /// Create an Intent (the action intent) that will occur when the action item in the notification is clicked on
        // Use an implicit intent to send the message "Hello"

        Intent actionIntent = new Intent(Intent.ACTION_SEND);

        actionIntent.setType("text/plain");

        actionIntent.putExtra(Intent.EXTRA_TEXT, "Hello");

        // Wrap the intent in a PendingIntent, which allows the Android system to perform the intent sometime in the future (when the notification is tapped)
        PendingIntent implicitIntent = PendingIntent.getActivity(this, NOTIFICATION_ID_1, actionIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        // Use the NotificationCompat.Builder class to create notifications
        // Create a builder with the channel ID

        // This is the "notification" object
        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID);

        // Set the attributes of the notification
        notifyBuilder
        .setContentTitle("Notification One")
        .setContentText("This is your first notification")
        .setSmallIcon(R.drawable.ic_android_black_24dp)
        // Set the content intent (the intent of the notification)
        .setContentIntent(explicitIntent)
        // Autocancel removes the notification after is is tapped
        .setAutoCancel(true)
        // Set the priority of the notification; High or Max shows a drop down notification on top of the active screen
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        // Sets the sound, vibration, and LED color pattern for the notification to the default notification channel values
        .setDefaults(NotificationCompat.DEFAULT_ALL)
        // Add an action to the notification that uses launches an implicit intent to send the message "Hello"
        .addAction(R.drawable.ic_message_black_24dp, "Say hello!", implicitIntent);

        return notifyBuilder;

    }

    private NotificationCompat.Builder getNotificationBuilder2() {

        /// Create another notification on the same channel

        // Use the NotificationCompat.Builder class to create notifications
        // Create a builder with the channel ID
        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID);

        /// Create an Intent that will use the custom update action
        Intent updateIntent = new Intent(ACTION_UPDATE_NOTIFICATION_TWO);
        PendingIntent updatePendingIntent = PendingIntent.getBroadcast(this, NOTIFICATION_ID_2, updateIntent, PendingIntent.FLAG_ONE_SHOT);

        notifyBuilder
                .setContentTitle("Another notification")
                .setContentText("This is your second notification")
                .setSmallIcon(R.drawable.ic_android_black_24dp)
                .addAction(R.drawable.ic_update_black_24dp, "Update Notification", updatePendingIntent)
                // Makes the notification non-dismissable
                //.setOngoing(true)
                // Make the notification an expandable notification
                // Use setStyle and use the InboxStyle, which displays a list of summary lines when the notification is expanded
                .setStyle(new NotificationCompat.InboxStyle().addLine("Hello").addLine("What's up").addLine("I am the techlead")).build();

        return notifyBuilder;

    }

    private void dismissNotificationOne() {

        // Dismiss the first notification
        notificationManager.cancel(NOTIFICATION_ID_1);

        // Once the notification is dismissed, show the notification button only (back to state it was when it was first created)
        setNotificationOneButtonState(true, false, false);

    }

    private void dismissNotificationTwo() {

        // Dismiss the second notification
        notificationManager.cancel(NOTIFICATION_ID_2);

        setNotificationTwoButtonState(true, false, false);

    }

    private void dismissNotifications() {

        // Call cancelAll() on the NotificationManger to dismiss (remove) the notifications
        notificationManager.cancelAll();

    }

    // Update the existing notification
    // Change it to an expanded notification that shows a picture by calling setStyle
    private void updateNotificationOne() {

        // Convert the drawable to a Bitmap
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.mascot_1);

        // Get a new instance of the Notification object
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();

        // Change the style to the big picture style, which shows an expandable notification with a large image
        // Set big content title sets the title of the notification when it is expanded
        notifyBuilder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap).setBigContentTitle("Notification 1 Updated!"));

        // Start the notification
        notificationManager.notify(NOTIFICATION_ID_1, notifyBuilder.build());

        // Once the notification is updated, disable the update button
        setNotificationOneButtonState(false, false, true);

    }

    private void updateNotificationTwo() {

        // Convert the drawable to a Bitmap
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.mascot_1);

        NotificationCompat.Builder notifyBuilder = getNotificationBuilder2();

        // Change the style to the big picture style, which shows an expandable notification with a large image
        // Set big content title sets the title of the notification when it is expanded
        notifyBuilder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap).setBigContentTitle("Notification 2 updated!"));

        notificationManager.notify(NOTIFICATION_ID_2, notifyBuilder.build());

        setNotificationTwoButtonState(false, false, true);

    }


    // Update the button state of the Notification One buttons to make a better UX experience
    public void setNotificationOneButtonState(Boolean isNotifyEnabled, Boolean isUpdateEnabled, Boolean isDisableEnabled) {

        button_notification_1.setEnabled(isNotifyEnabled);

        button_update_notification_1.setEnabled(isUpdateEnabled);

        button_dismiss_notification_1.setEnabled(isDisableEnabled);

    }

    // Update the button state of the Notification One buttons to make a better UX experience
    public void setNotificationTwoButtonState(Boolean isNotifyEnabled, Boolean isUpdateEnabled, Boolean isDisableEnabled) {

        button_notification_2.setEnabled(isNotifyEnabled);

        button_update_notification_2.setEnabled(isUpdateEnabled);

        button_dismiss_notification_2.setEnabled(isDisableEnabled);

    }

    // Add an inner class for the BroadcastReceiver that will handle the Notification 2 action intent
    public class NotificationReceiver extends BroadcastReceiver {


        @Override
        public void onReceive(Context context, Intent intent) {

            // Call updateNotificationTwo() to update notification two
            updateNotificationTwo();

        }
    }

    @Override
    protected void onDestroy() {


        unregisterReceiver(notificationReceiver);
        super.onDestroy();
    }
}
