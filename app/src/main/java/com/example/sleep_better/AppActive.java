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
        registerReceiver(broadcastReceiver, new IntentFilter("unSleep"));


        Log.d("info", hour + ":" + min + " " + amPm);

    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle b = intent.getExtras();
            Log.e(TAG, "Alarm resolved");
            Intent i = new Intent(AppActive.this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }
    };
}
