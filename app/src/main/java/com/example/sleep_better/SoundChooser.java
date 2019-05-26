package com.example.sleep_better;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class SoundChooser extends AppCompatActivity {

    private MediaPlayer audio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_chooser);
    }

    public void play1000Hz(View v) {
        if(audio == null) {
            audio = MediaPlayer.create(this,R.raw.hz1000);
            audio.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopPlayer();
                }
            });
        }
        audio.start();
    }

    public void play2000Hz(View v) {
        if(audio == null) {
            audio = MediaPlayer.create(this,R.raw.hz2000);
            audio.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopPlayer();
                }
            });
        }
        audio.start();
    }

    public void playCats(View v) {
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
}
