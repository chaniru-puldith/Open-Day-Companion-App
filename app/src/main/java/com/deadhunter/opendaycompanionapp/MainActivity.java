package com.deadhunter.opendaycompanionapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    ImageButton logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}