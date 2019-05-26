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

//Manages the accelerometer functionality
public class AccelerometerFragment extends Fragment {
    private SensorManager sensorManager;
    private Sensor sensor;
    private View view;
    private double mag;
    private boolean running = true;
    private Handler handler;
    private MediaPlayer audio;

    private final String TAG = "in AccelFragment";

    //Triggers tone if the conditions have been met
    private SensorEventListener listener = new SensorEventListener() {
        Handler handler = new Handler();
        @Override
        public void onSensorChanged(SensorEvent event) {
            //Gets magnitude of acceleration
            float[] vals = event.values;
            mag = Math.sqrt(Math.pow(vals[0], 2) + Math.pow(vals[1], 2) + Math.pow(vals[2], 2));

            //Set up periodic data collection with boolean value running
            if(running) {
                Log.d(TAG,"Magnitude" + mag);

                //Threshold for audio play
                if(mag < 9.68) {
                    Log.e("magnitude", mag + "");
                    if(audio == null) {
                        audio = MediaPlayer.create(getContext(),R.raw.catnoise);
                    }
                    audio.start();
                }
                running = false;
            }

        }

        //Responds to sensor accuracy adjustments
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    //Looping code to set up periodic data collection every 20 sec
    private final Runnable turnOn = new Runnable() {
        @Override
        public void run() {
            running = true;
            handler.postDelayed(this,20000);
        }
    };

    //Resumes accelerometer and looping code
    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(listener,sensor,SensorManager.SENSOR_DELAY_NORMAL);
        handler.postDelayed(turnOn,20000);
    }

    //Pauses accelerometer and looping code
    @Override
    public void onPause() {
        handler.removeCallbacks(turnOn);
        super.onPause();
        if(audio != null) {
            audio.release();
        }
    }

    //Sets up sensors and handlers and starts data collection
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.app_active, container, false);
        Log.d(TAG,"Fragment started");
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_UI);
        handler = new Handler();
        handler.post(turnOn);

        return view;
    }


}
