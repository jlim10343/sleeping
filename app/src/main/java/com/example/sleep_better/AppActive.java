package com.example.sleep_better;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class AppActive extends AppCompatActivity {

    private final String TAG = "in AppActive";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_active);
        Intent intent = getIntent();
        int hour = intent.getIntExtra("hour", 0);
        int min = intent.getIntExtra("minute", 0);
        String amPm = intent.getStringExtra("meridian");


        Log.d("info", hour + ":" + min + " " + amPm);

    }


}
