package com.example.notificationscheduler;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

public class NotificationJobService extends JobService {

    NotificationManager notificationManager;

    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";

    private static final int NOTIFICATION_ID = 1;


    @Override
    public boolean onStartJob(JobParameters params) {

        // Call createNotificationChannel() to create the channel
        createNotificationChannel();

        // Set the notification content intent

        PendingIntent contentPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class),PendingIntent.FLAG_UPDATE_CURRENT );

        // Create the notification
        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID);

        notification
                .setContentTitle("Job Service")
                .setContentText("Your job is running!")
                .setSmallIcon(R.drawable.ic_autorenew_black_24dp)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(contentPendingIntent);

        // Deploy the notification
        notificationManager.notify(NOTIFICATION_ID, notification.build());

        return false;

    }

    @Override
    public boolean onStopJob(JobParameters params) {

        // Return true because if the job fails, we want the job to be rescheduled instead of dropped
        return true;
    }

    /**
     * Create a notification channel for OREO and higher
     */

    public void createNotificationChannel() {

        // Get a NotificationManager object
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Add check to see if SDK version is 26 or above

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            // Create the Notification channel object
            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID, "NotificationJobScheduler", NotificationManager.IMPORTANCE_HIGH);

            // Set the default behavior for the NotificationChannel
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notifications for the JobScheduler");

            // Create the Notification channel
            notificationManager.createNotificationChannel(notificationChannel);

        }



    }
}
