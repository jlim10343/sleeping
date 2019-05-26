package com.example.sleep_better;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AccelerometerFragment extends Fragment {
    private SensorManager sensorManager;
    private Sensor sensor;
    private View view;
    private double mag;
    private boolean running = true;
    private Handler handler;

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle b = intent.getExtras();
            Log.e("AccelerometerFragment", "Update received");
            if(b.getBoolean("sleep")) {
                sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_UI);
            } else {
                sensorManager.unregisterListener(listener,sensor);
            }
        }
    };

    private SensorEventListener listener = new SensorEventListener() {
        Handler handler = new Handler();
        @Override
        public void onSensorChanged(SensorEvent event) {
            float[] vals = event.values;
            //Log.d("values", "magnitude: " + Math.sqrt(Math.pow(vals[0], 2) + Math.pow(vals[1], 2) + Math.pow(vals[2], 2)) +
              //      " \tvalue[] = " + vals[0] + " " + vals[1] + " " + vals[2]);
            mag = Math.sqrt(Math.pow(vals[0], 2) + Math.pow(vals[1], 2) + Math.pow(vals[2], 2));

            if(running) {
                Log.d("EOIS","EIONSODICNOEIWAF");
                running = false;
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
            handler.postDelayed(this,3000);
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(listener,sensor,SensorManager.SENSOR_DELAY_NORMAL);
        handler.post(turnOn);
    }

    @Override
    public void onPause() {
        handler.removeCallbacks(turnOn);
        super.onPause();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.accfragment, container, false);

        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_UI);
        getActivity().registerReceiver(broadcastReceiver, new IntentFilter("unSleep"));
        handler = new Handler();
        sensorManager.unregisterListener(listener,sensor);
        return view;
    }


}
