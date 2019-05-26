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
import android.widget.Button;
import android.widget.Toast;

public class SoundChooser extends Fragment {

    private MediaPlayer audio;

    @Override
    @Nullable
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_sound_chooser, container, false);

        Button first = view.findViewById(R.id.buttonFirst);
        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(audio == null) {
                    audio = MediaPlayer.create(getActivity(), R.raw.small);
                    HomeFragment.setAudio(audio);
                    audio.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            stopPlayer();
                        }
                    });
                }
                audio.start();
            }
        });

        Button second = view.findViewById(R.id.buttonSecond);
        second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(audio == null) {
                    audio = MediaPlayer.create(getActivity(), R.raw.big);
                    HomeFragment.setAudio(audio);
                    audio.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            stopPlayer();
                        }
                    });
                }
                audio.start();
            }
        });

        Button cat = view.findViewById(R.id.button3);
        cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(audio == null) {
                    audio = MediaPlayer.create(getActivity(),R.raw.catnoise);
                    HomeFragment.setAudio(audio);
                    audio.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            stopPlayer();
                        }
                    });
                }
                audio.start();
            }
        });

        return view;
    }

    //Stops audio playback
    private void stopPlayer() {
            if(audio != null) {
                audio.release();
                audio = null;
            }
    }
}
