package com.example.sleep_better;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer audio;


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

        Spinner hour = findViewById(R.id.hour);
        Spinner min = findViewById(R.id.minute);
        Spinner amPm = findViewById(R.id.day_or_night);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this.getBaseContext(),
                R.array.hours, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this.getBaseContext(),
                R.array.minutes, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this.getBaseContext(),
                R.array.morning_or_night, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        hour.setAdapter(adapter1);
        min.setAdapter(adapter2);
        amPm.setAdapter(adapter3);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
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
        audio.release();
    }

    private void stopPlayer()
    {
        if(audio != null) {
            audio.release();
            audio = null;
            Toast.makeText(this,"YEEEE",Toast.LENGTH_SHORT).show();
        }
    }
}