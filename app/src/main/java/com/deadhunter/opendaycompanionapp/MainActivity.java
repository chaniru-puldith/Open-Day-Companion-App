package com.deadhunter.opendaycompanionapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    ImageButton logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel1();

        // Create intent and pending intent for Event 1
        Intent intent1 = new Intent(MainActivity.this, ReminderBroadcast.class);
        intent1.putExtra("event_title", "Event 1");
        intent1.putExtra("event_time", "10.00AM");
        intent1.putExtra("notificationId", 1);
        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(MainActivity.this, 123, intent1, PendingIntent.FLAG_IMMUTABLE);

        // Set alarm for Event 1
        AlarmManager alarmManager1 = (AlarmManager) getSystemService(ALARM_SERVICE);
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(Calendar.YEAR, 2023);
        calendar1.set(Calendar.MONTH, Calendar.MAY);
        calendar1.set(Calendar.DAY_OF_MONTH, 11);
        calendar1.set(Calendar.HOUR_OF_DAY, 00);
        calendar1.set(Calendar.MINUTE, 25);
        calendar1.set(Calendar.SECOND, 0);
        long event1TimeInMillis = calendar1.getTimeInMillis();
        if (event1TimeInMillis > System.currentTimeMillis()) {
            alarmManager1.set(AlarmManager.RTC_WAKEUP, event1TimeInMillis, pendingIntent1);
        }

        // Create intent and pending intent for Event 2
        Intent intent2 = new Intent(MainActivity.this, ReminderBroadcast.class);
        intent2.putExtra("event_title", "Event 2");
        intent2.putExtra("event_time", "10.30AM");
        intent2.putExtra("notificationId", 2);
        PendingIntent pendingIntent2 = PendingIntent.getBroadcast(MainActivity.this, 124, intent2, PendingIntent.FLAG_IMMUTABLE);

        // Set alarm for Event 2
        AlarmManager alarmManager2 = (AlarmManager) getSystemService(ALARM_SERVICE);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(Calendar.YEAR, 2023);
        calendar2.set(Calendar.MONTH, Calendar.MAY);
        calendar2.set(Calendar.DAY_OF_MONTH, 11);
        calendar2.set(Calendar.HOUR_OF_DAY, 00);
        calendar2.set(Calendar.MINUTE, 22);
        calendar2.set(Calendar.SECOND, 0);
        long event2TimeInMillis = calendar2.getTimeInMillis();
        if (event2TimeInMillis > System.currentTimeMillis()) {
            alarmManager2.set(AlarmManager.RTC_WAKEUP, event2TimeInMillis, pendingIntent2);
        }

        // Create intent and pending intent for Event 3
        Intent intent3 = new Intent(MainActivity.this, ReminderBroadcast.class);
        intent3.putExtra("event_title", "Event 3");
        intent3.putExtra("event_time", "11.00AM");
        intent3.putExtra("notificationId", 3);
        PendingIntent pendingIntent3 = PendingIntent.getBroadcast(MainActivity.this, 125, intent3, PendingIntent.FLAG_IMMUTABLE);

        // Set alarm for Event 3
        AlarmManager alarmManager3 = (AlarmManager) getSystemService(ALARM_SERVICE);
        Calendar calendar3 = Calendar.getInstance();
        calendar3.set(Calendar.YEAR, 2023);
        calendar3.set(Calendar.MONTH, Calendar.MAY);
        calendar3.set(Calendar.DAY_OF_MONTH, 11);
        calendar3.set(Calendar.HOUR_OF_DAY, 00);
        calendar3.set(Calendar.MINUTE, 21);
        calendar3.set(Calendar.SECOND, 0);
        long event3TimeInMillis = calendar3.getTimeInMillis();
        if (event3TimeInMillis > System.currentTimeMillis()) {
            alarmManager3.set(AlarmManager.RTC_WAKEUP, event3TimeInMillis, pendingIntent3);
        }
        // Setting up slideshow on main menu
        ImageSlider imageSlider = findViewById(R.id.slider);

        List<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel(R.drawable.first, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.second, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.third, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.fourth, ScaleTypes.FIT));

        imageSlider.setImageList(slideModels, ScaleTypes.FIT);

        // Initialize the logout button
        logout = findViewById(R.id.logout_btn);

        // Logout the user if the logout button clicked
        logout.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(MainActivity.this, "Successfully Logged Out", Toast.LENGTH_SHORT).show();
        });

        // Initializing the CardViews on the main menu
        CardView agenda = (CardView) findViewById(R.id.agenda);
        CardView contact = (CardView) findViewById(R.id.contact);
        CardView courses = (CardView) findViewById(R.id.courses);
        CardView gMap = (CardView) findViewById(R.id.gMap);
        CardView qr = (CardView) findViewById(R.id.qr);
        CardView navi = (CardView) findViewById(R.id.navi);

        agenda.setOnClickListener((View.OnClickListener) this);
        contact.setOnClickListener((View.OnClickListener) this);
        courses.setOnClickListener((View.OnClickListener) this);
        gMap.setOnClickListener((View.OnClickListener) this);
        qr.setOnClickListener((View.OnClickListener) this);
        navi.setOnClickListener((View.OnClickListener) this);

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

    // Declaring what should be happened after clicking each CardView
    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.agenda : i = new Intent(this, AgendaActivity.class);
            startActivity(i);
            break;
            case R.id.contact: i = new Intent(this, ContactusActivity.class);
            startActivity(i);
            break;
            case R.id.courses: i = new Intent(this, CoursesActivity.class);
            startActivity(i);
            break;
            case R.id.gMap: i = new Intent(this, GooglemapActivity.class);
            startActivity(i);
            break;
            case R.id.navi: i = new Intent(this, NavigationActivity.class);
            startActivity(i);
            break;
            case R.id.qr: i = new Intent(this, QrActivity.class);
            startActivity(i);
            break;

        }
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}