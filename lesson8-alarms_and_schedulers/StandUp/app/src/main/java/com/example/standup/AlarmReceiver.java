package com.example.standup;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {

    private NotificationManager notificationManager;
    private static final int NOTIFICATION_ID = 0;

    // Notification channel ID.
    private static final String PRIMARY_CHANNEL_ID =
            "primary_notification_channel";

    @Override
    public void onReceive(Context context, Intent intent) {

        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        deliverNotification(context);

    }

    private void deliverNotification(Context context) {

        // Create the content intent for the notification
        Intent contentIntent = new Intent(context, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, NOTIFICATION_ID, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        /// Create the notification

        // Create the notification object using the NotificationCompat.Builder class
        NotificationCompat.Builder notification = new NotificationCompat.Builder(context, PRIMARY_CHANNEL_ID);

        // Set the behavior of the notification
        notification
                .setSmallIcon(R.drawable.ic_directions_walk_black_24dp)
                .setContentTitle("Stand Up!")
                .setContentText("Stand Up and Walk")
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        // Deliver the notification
        notificationManager.notify(NOTIFICATION_ID, notification.build());



    }
}
