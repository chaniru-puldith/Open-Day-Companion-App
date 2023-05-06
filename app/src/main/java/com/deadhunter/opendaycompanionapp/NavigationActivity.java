package com.deadhunter.opendaycompanionapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class NavigationActivity extends AppCompatActivity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        CardView fac1 = (CardView) findViewById(R.id.fac1);
        CardView fac2 = (CardView) findViewById(R.id.fac2);
        CardView fac3 = (CardView) findViewById(R.id.fac3);
        CardView fac4 = (CardView) findViewById(R.id.fac4);
        CardView fac5 = (CardView) findViewById(R.id.fac5);
        CardView fac6 = (CardView) findViewById(R.id.fac6);

        fac1.setOnClickListener((View.OnClickListener) this);
        fac2.setOnClickListener((View.OnClickListener) this);
        fac3.setOnClickListener((View.OnClickListener) this);
        fac4.setOnClickListener((View.OnClickListener) this);
        fac5.setOnClickListener((View.OnClickListener) this);
        fac6.setOnClickListener((View.OnClickListener) this);
    }


    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.fac1:
                i = new Intent(this, Faculty1Activity.class);
                startActivity(i);
                break;
            case R.id.fac2:
                i = new Intent(this, Faculty2Activity.class);
                startActivity(i);
                break;
            case R.id.fac3:
                i = new Intent(this, Faculty3Activity.class);
                startActivity(i);
                break;
            case R.id.fac4:
                i = new Intent(this, Faculty4Activity.class);
                startActivity(i);
                break;
            case R.id.fac5:
                i = new Intent(this, Faculty5Activity.class);
                startActivity(i);
                break;
            case R.id.fac6:
                i = new Intent(this, Faculty6Activity.class);
                startActivity(i);
                break;

        }

    }
}