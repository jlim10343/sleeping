package com.example.sleep_better;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

//Manages activities performed while timer is active
public class AppActive extends AppCompatActivity {

    private final String TAG = "in AppActive";
    private Fragment frag;

    //Registers receivers and starts fragments
    //Part of 2-part method to enable/disable accelerometer in/out of AppActive
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Registers receivers and starts accelerometer
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_active);
        Log.d(TAG,"AppActive started");
        registerReceiver(broadcastReceiver, new IntentFilter("unSleep"));
        frag = new AccelerometerFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                frag).commit();

        //Sets up cancel functionality to stop alarm prematurely and goes back to alarm page
        Button b = findViewById(R.id.cancelButton);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Destroys fragments to disable accelerometer
                frag=null;

                //Unregisters receiver
                unregisterReceiver(broadcastReceiver);

                //Cancels alarm
                cancelAlarm();

                //Returns to alarm page
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HomeFragment()).commit();
                setContentView(R.layout.empty);
                TextView tv = findViewById(R.id.textView4);
                tv.setVisibility(View.INVISIBLE);
                Intent i = new Intent(AppActive.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                Log.d(TAG,"Alarm canceled");



            }
        });

    }

    //Receives unSleep broadcast to go back to main page
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "Alarm resolved");
            //Destroys fragments to disable accelerometer
            frag=null;

            //Unregisters receiver
            unregisterReceiver(broadcastReceiver);

            //Returns to timer page
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
            setContentView(R.layout.empty);
            TextView tv = findViewById(R.id.textView4);
            tv.setVisibility(View.INVISIBLE);
            Intent i = new Intent(AppActive.this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);

        }
    };


    //Prevents back button from glitching code
    @Override
    public void onBackPressed() {

    }

    public void cancelAlarm() {
        Intent unSleep = new Intent("unSleep");
        unSleep.putExtra("sleep",true);
        this.sendBroadcast(unSleep);
    }



}
