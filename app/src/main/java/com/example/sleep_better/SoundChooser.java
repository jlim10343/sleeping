package com.example.sleep_better;

import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class SoundChooser extends Fragment {

    private MediaPlayer audio;

    @Override
    @Nullable
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_sound_chooser, container, false);

        return view;
    }

    public void play1000Hz(View v) {
        if(audio == null) {
            audio = MediaPlayer.create(getActivity(), R.raw.small);
            MainActivity.setAudio(audio);
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
            audio = MediaPlayer.create(getActivity(), R.raw.big);
            MainActivity.setAudio(audio);
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
            audio = MediaPlayer.create(getActivity(),R.raw.catnoise);
            MainActivity.setAudio(audio);
            audio.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopPlayer();
                }
            });
        }
        audio.start();
    }

    private void stopPlayer() {
            if(audio != null) {
                audio.release();
                audio = null;
                Toast.makeText(getActivity(),"YEEEE",Toast.LENGTH_SHORT).show();
            }
    }
}
