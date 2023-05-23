package com.deadhunter.opendaycompanionapp;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;


import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

/*
public class ReminderBroadcast extends BroadcastReceiver {
    private static final int REQUEST_CODE1 = 201;


    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationCompat.Builder builder1 = new NotificationCompat.Builder(context, "CINECOD1")
                .setSmallIcon(R.drawable.baseline_circle_notifications_24)
                .setContentTitle("Event 1")
                .setContentText("Event 1 begins @ 10.00AM, Please get seated")
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat notificationManager1 = NotificationManagerCompat.from(context);

        NotificationCompat.Builder builder2 = new NotificationCompat.Builder(context, "CINECOD1")
                .setSmallIcon(R.drawable.baseline_circle_notifications_24)
                .setContentTitle("Event 2")
                .setContentText("Event 2 begins @ 10.30AM, Please get seated")
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat notificationManager2 = NotificationManagerCompat.from(context);

        NotificationCompat.Builder builder3 = new NotificationCompat.Builder(context, "CINECOD1")
                .setSmallIcon(R.drawable.baseline_circle_notifications_24)
                .setContentTitle("Event 1")
                .setContentText("Event 1 begins @ 11.00AM, Please get seated")
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat notificationManager3 = NotificationManagerCompat.from(context);


        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_NOTIFICATION_POLICY}, REQUEST_CODE1);

            return;
        }
        notificationManager1.notify(201, builder1.build());
        notificationManager1.notify(202, builder2.build());
        notificationManager1.notify(203, builder3.build());


    }

}
*/

public class ReminderBroadcast extends BroadcastReceiver {
    private static final int REQUEST_CODE1 = 201;


    @Override
    public void onReceive(Context context, Intent intent) {
        String channelId = "CINECOD1";

        int notificationId = intent.getIntExtra("notificationId", -1);

        System.out.printf(notificationId+"");
        if (notificationId == 1) {
            NotificationCompat.Builder builder1 = new NotificationCompat.Builder(context, channelId)
                    .setSmallIcon(R.drawable.baseline_circle_notifications_24)
                    .setContentTitle("Event 1")
                    .setContentText("Event 1 Please get seated")
                    .setPriority(NotificationCompat.PRIORITY_HIGH);

            NotificationManagerCompat notificationManager1 = NotificationManagerCompat.from(context);

            // Check for permission before sending notification
            if (notificationManager1.areNotificationsEnabled()) {
                notificationManager1.notify(201, builder1.build());
            }
        }

        long scheduledTime2 = intent.getLongExtra("time2", 0);
        if (notificationId == 2) {
            NotificationCompat.Builder builder2 = new NotificationCompat.Builder(context, channelId)
                    .setSmallIcon(R.drawable.baseline_circle_notifications_24)
                    .setContentTitle("Event 2")
                    .setContentText("Event 2 Please get seated")
                    .setPriority(NotificationCompat.PRIORITY_HIGH);

            NotificationManagerCompat notificationManager2 = NotificationManagerCompat.from(context);

            // Check for permission before sending notification
            if (notificationManager2.areNotificationsEnabled()) {
                notificationManager2.notify(202, builder2.build());
            }
        }

        long scheduledTime3 = intent.getLongExtra("time3", 0);
        if (notificationId == 3) {
            System.out.printf("t3");
            NotificationCompat.Builder builder3 = new NotificationCompat.Builder(context, channelId)
                    .setSmallIcon(R.drawable.baseline_circle_notifications_24)
                    .setContentTitle("Event 3")
                    .setContentText("Event 3 Please get seated")
                    .setPriority(NotificationCompat.PRIORITY_HIGH);
            NotificationManagerCompat notificationManager3 = NotificationManagerCompat.from(context);

            // Check for permission before sending notification
            if (notificationManager3.areNotificationsEnabled()) {
                notificationManager3.notify(203, builder3.build());
            }
        }
    }
}

