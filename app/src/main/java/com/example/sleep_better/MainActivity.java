package com.example.sleep_better;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer audio;
    private String hour_select = "";
    private String minute_select = "";
    private String morn_night_select = "";

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

        final ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this.getBaseContext(),
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

        hour.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                hour_select = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        min.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                minute_select = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        amPm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                morn_night_select = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button go = findViewById(R.id.go_button);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!hour_select.equals("") && !minute_select.equals("") && !morn_night_select.equals("")) {
                    Intent intent = new Intent(getBaseContext(), AppActive.class);
                    intent.putExtra("hour", Integer.parseInt(hour_select));
                    intent.putExtra("minute", Integer.parseInt(minute_select));
                    intent.putExtra("meridian", morn_night_select);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getBaseContext(), "Please enter a time", Toast.LENGTH_LONG).show();
                }
            }
        });

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
        if(audio != null) audio.release();
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