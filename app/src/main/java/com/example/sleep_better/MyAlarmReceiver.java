package com.example.sleep_better;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

//Receives an alarm then sends feedback
public class MyAlarmReceiver extends BroadcastReceiver {

    private final String TAG = "in onReceive";

    //Receives and responds to alarm
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Alarm received");

        //Sends a response broadcast
        Intent unSleep = new Intent("unSleep");
        unSleep.putExtra("sleep",false);
        context.sendBroadcast(unSleep);
    }


}