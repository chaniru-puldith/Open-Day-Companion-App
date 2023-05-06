package com.deadhunter.opendaycompanionapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class CoursesActivity extends AppCompatActivity {
    Button courses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses2);
        courses = findViewById(R.id.course_details);
        courses.setOnClickListener(view -> {
            String link = "https://www.cinec.edu/course-list/a-z-courses.html";
            gotoUrl(link);
        });
    }

    private void gotoUrl(String link){
        Uri uri = Uri.parse(link);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }
}