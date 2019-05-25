package com.example.sleep_better;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    MediaPlayer audio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        audio=MediaPlayer.create(MainActivity.this,R.raw.catSound);
    }

    public void playAudio(View v) {
        audio.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        audio.release();
    }


}
