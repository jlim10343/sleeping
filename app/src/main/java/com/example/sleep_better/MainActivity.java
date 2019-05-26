/*
References:
https://trinitytuts.com/pass-data-from-broadcast-receiver-to-activity-without-reopening-activity/
https://stackoverflow.com/questions/18310759/going-back-to-mainactivity-from-child-activity-in-android
http://soundbible.com/2197-Analog-Watch-Alarm.html
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
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Calendar;

//Alarm set page
public class MainActivity extends AppCompatActivity {

    private static MediaPlayer audio;


    private final String TAG = "in MainActivity";

    //Loads fragment
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


    //Sets up navigation
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.empty);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);


    }

    //Sets up navbar
    BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Log.d(TAG,"LHEPH");
            Fragment selectedFragment = null;
            switch (menuItem.getItemId()) {
                case R.id.instruct:
                    selectedFragment = new Instructions();
                    break;
                case R.id.soundChoose:
                    selectedFragment = new SoundChooser();
                    break;
                case R.id.goHome:
                    selectedFragment = new HomeFragment();
                    break;
            }

            TextView tv = findViewById(R.id.textView4);
            tv.setVisibility(View.INVISIBLE);

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    selectedFragment).commit();

            return false;
        }
    };

    //Plays default beep noise
    public void playAudio(View v) {
        if(audio == null) {
            audio = MediaPlayer.create(this,R.raw.catnoise);
            audio.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopPlayer();
                }
            });
        }
        audio.start();
    }

    //Pauses sound playback
    @Override
    protected void onPause() {
        super.onPause();
        if(audio != null) {
            audio.release();
        }
    }

    //Stops playback
    private void stopPlayer()
    {
        if(audio != null) {
            audio.release();
            audio = null;
        }
    }
}