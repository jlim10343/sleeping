/*
References:
https://trinitytuts.com/pass-data-from-broadcast-receiver-to-activity-without-reopening-activity/
 */

package com.example.sleep_better;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer audio;
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    private TimePicker time;
    private boolean isAsleep;
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle b = intent.getExtras();
            isAsleep = b.getBoolean("sleep");
            Log.e(TAG, "Alarm resolved");
        }
    };

    private final String TAG = "in MainActivity";

    private boolean loadFragment (Fragment fragment)
    {
        if (fragment != null)
        {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        time=(TimePicker) findViewById(R.id.timePicker1);
        alarmMgr = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, MyAlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        isAsleep = false;
        registerReceiver(broadcastReceiver, new IntentFilter("unSleep"));

    }


    BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment = null;
            switch (menuItem.getItemId()) {
                case R.id.accelerate:
                    selectedFragment = new AccelerometerFragment();
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    selectedFragment).commit();

            return false;
        }
    };

    public void playAudio(View v) {
        if(audio == null) {
            audio = MediaPlayer.create(this,R.raw.catsound);
            audio.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopPlayer();
                }
            });
        }
        audio.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(audio != null) {
            audio.release();
        }
    }

    private void stopPlayer()
    {
        if(audio != null) {
            audio.release();
            audio = null;
            Toast.makeText(this,"YEEEE",Toast.LENGTH_SHORT).show();
        }
    }

    public void setAlarm(View v) {
        Log.d(TAG,Integer.toString(time.getCurrentHour()));
        isAsleep = true;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY,time.getCurrentHour());
        calendar.set(Calendar.MINUTE,time.getCurrentMinute());


        alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(), AlarmManager.INTERVAL_FIFTEEN_MINUTES, alarmIntent);

        Intent intent = new Intent(this, AppActive.class);
        startActivity(intent);
    }
}