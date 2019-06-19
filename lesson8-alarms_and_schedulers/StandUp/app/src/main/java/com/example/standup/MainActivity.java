package com.example.standup;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    private NotificationManager notificationManager;

    private static final int NOTIFICATION_ID = 0;

    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";

    AlarmManager alarmManager;

    boolean alarmUp;

    PendingIntent alarmPendingIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ToggleButton toggleButton = findViewById(R.id.alarmToggle);


        // Create the AlarmManager that will deliver the broadcast intent every 15 minutes
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        // Create the intent for the alarm that will be delivered to update the remaining time in the notification
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);

        /// Fix the issue that sets the toggle button to off if the app is closed, even if the alarm has been set
        // Create a boolean that checks whether a pending intent exists (whether the alarm has been set)
        alarmUp = (PendingIntent.getBroadcast(this, NOTIFICATION_ID, alarmIntent, PendingIntent.FLAG_NO_CREATE) != null);

        toggleButton.setChecked(alarmUp);




        alarmPendingIntent = PendingIntent.getBroadcast(this, NOTIFICATION_ID, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                String toastMessage;

                if (isChecked) {

                    long repeatInterval = AlarmManager.INTERVAL_FIFTEEN_MINUTES;
                    long minuteInterval = repeatInterval / 15;
                    long triggerTime = SystemClock.elapsedRealtime() + minuteInterval;

                    alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, minuteInterval, alarmPendingIntent);


                    toastMessage = "Stand Up Alarm On!";


                } else {

                    notificationManager.cancelAll();

                    toastMessage = "Stand Up Alarm Off!";

                    if (alarmManager != null) {

                        alarmManager.cancel(alarmPendingIntent);

                    }

                }

                Toast.makeText(MainActivity.this, toastMessage, Toast.LENGTH_SHORT).show();

            }
        });


        createNotificationChannel();


    }

    public void createNotificationChannel() {

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID, "Stand up Notifications", NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notifies every 15 minutes to stand up and walk");

            notificationManager.createNotificationChannel(notificationChannel);

        }

    }


}
