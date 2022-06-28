package com.shaxhxnk.rnspark.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.VideoView;


import com.shaxhxnk.rnspark.R;

import java.util.Timer;
import java.util.TimerTask;

public class StartingActivity extends AppCompatActivity {
        Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);

        timer =new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent =new Intent(StartingActivity.this, StartupActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);




    }
}