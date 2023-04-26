package com.deadhunter.opendaycompanionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CoursesActivity extends AppCompatActivity {
    Button courses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses2);
        courses = findViewById(R.id.course_details);
        courses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String link = "https://www.cinec.edu/course-list/a-z-courses.html";
                gotoUrl(link);
            }
        });
    }

    private void gotoUrl(String link){
        Uri uri = Uri.parse(link);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }
}