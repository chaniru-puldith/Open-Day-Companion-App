package com.deadhunter.opendaycompanionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import java.util.Calendar;

public class AgendaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);

        createNotificationChannel1();

        // Set the time for the first notification
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(Calendar.YEAR, 2023);
        calendar1.set(Calendar.MONTH, Calendar.MAY);
        calendar1.set(Calendar.DAY_OF_MONTH, 9);
        calendar1.set(Calendar.HOUR_OF_DAY, 22);
        calendar1.set(Calendar.MINUTE, 17);
        calendar1.set(Calendar.SECOND, 0);

// Set the time for the second notification
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(Calendar.YEAR, 2023);
        calendar2.set(Calendar.MONTH, Calendar.MAY);
        calendar2.set(Calendar.DAY_OF_MONTH, 9);
        calendar2.set(Calendar.HOUR_OF_DAY, 10);
        calendar2.set(Calendar.MINUTE, 30);
        calendar2.set(Calendar.SECOND, 0);

// Set the time for the third notification
        Calendar calendar3 = Calendar.getInstance();
        calendar3.set(Calendar.YEAR, 2023);
        calendar3.set(Calendar.MONTH, Calendar.MAY);
        calendar3.set(Calendar.DAY_OF_MONTH, 9);
        calendar3.set(Calendar.HOUR_OF_DAY, 11);
        calendar3.set(Calendar.MINUTE, 0);
        calendar3.set(Calendar.SECOND, 0);

// Create intents for each notification
        Intent intent1 = new Intent(AgendaActivity.this, ReminderBroadcast.class);
        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(AgendaActivity.this, 123, intent1, PendingIntent.FLAG_IMMUTABLE);
        Intent intent2 = new Intent(AgendaActivity.this, ReminderBroadcast.class);
        PendingIntent pendingIntent2 = PendingIntent.getBroadcast(AgendaActivity.this, 1, intent2, PendingIntent.FLAG_IMMUTABLE);
        Intent intent3 = new Intent(AgendaActivity.this, ReminderBroadcast.class);
        PendingIntent pendingIntent3 = PendingIntent.getBroadcast(AgendaActivity.this, 2, intent3, PendingIntent.FLAG_IMMUTABLE);

// Set a separate alarm for each notification
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar1.getTimeInMillis(), pendingIntent1);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar2.getTimeInMillis(), pendingIntent2);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar3.getTimeInMillis(), pendingIntent3);


    }

    private void createNotificationChannel1() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name1 = "Event1";
            String description1 = "Main Event 1";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel1 = new NotificationChannel("CINECOD1", name1, importance);
            channel1.setDescription(description1);

            NotificationManager notificationManager1 = getSystemService(NotificationManager.class);
            notificationManager1.createNotificationChannel(channel1);

        }
    }
}

