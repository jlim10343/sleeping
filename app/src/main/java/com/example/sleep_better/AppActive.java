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
        registerReceiver(broadcastReceiver, new IntentFilter("unSleep"));

    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle b = intent.getExtras();
            Log.e(TAG, "Alarm resolved");
            Intent i = new Intent(AppActive.this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            unregisterReceiver(broadcastReceiver);
            startActivity(i);
        }
    };
}
