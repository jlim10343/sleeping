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

public class MyAlarmReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub


        Log.e("onReceive", "Alarm received");
        Toast.makeText(context, "OnReceive alarm test", Toast.LENGTH_SHORT).show();
        Intent unSleep = new Intent("unSleep");
        unSleep.putExtra("sleep",false);
        context.sendBroadcast(unSleep);
    }


}