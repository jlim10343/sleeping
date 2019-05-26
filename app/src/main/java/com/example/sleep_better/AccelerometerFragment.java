package com.example.sleep_better;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class AccelerometerFragment extends Fragment {
    private SensorManager sensorManager;
    private Sensor sensor;
    private View view;
    private double mag;
    private boolean running = true;
    private Handler handler;
    private MediaPlayer audio;

    private SensorEventListener listener = new SensorEventListener() {
        Handler handler = new Handler();
        @Override
        public void onSensorChanged(SensorEvent event) {
            float[] vals = event.values;
            //Log.d("values", "magnitude: " + Math.sqrt(Math.pow(vals[0], 2) + Math.pow(vals[1], 2) + Math.pow(vals[2], 2)) +
              //      " \tvalue[] = " + vals[0] + " " + vals[1] + " " + vals[2]);
            mag = Math.sqrt(Math.pow(vals[0], 2) + Math.pow(vals[1], 2) + Math.pow(vals[2], 2));


            if(running) {
                Log.d("EOIS",mag + "");
                if(mag < 9.68) {
                    Log.e("magnitude", mag + "");
                    if(audio == null) {
                        audio = MediaPlayer.create(getContext(),R.raw.catnoise);
                        /*audio.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                stopPlayer();
                            }
                        });*/
                    }
                    audio.start();
                }
                running = false;
            }

        }

        private void stopPlayer()
        {
            if(audio != null) {
                audio.release();
                audio = null;
                Toast.makeText(getContext(),"YEEEE",Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    private final Runnable turnOn = new Runnable() {
        @Override
        public void run() {
            running = true;
            handler.postDelayed(this,20000);
        }
    };


    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(listener,sensor,SensorManager.SENSOR_DELAY_NORMAL);
        handler.postDelayed(turnOn,20000);
    }

    @Override
    public void onPause() {
        handler.removeCallbacks(turnOn);
        super.onPause();
        if(audio != null) {
            audio.release();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.accfragment, container, false);
        Log.d("EOIS","SDFOIIDFIJOSNQIWONCDSLKN");
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_UI);
        handler = new Handler();
        handler.post(turnOn);

        return view;
    }


}
