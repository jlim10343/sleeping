package com.example.sleep_better;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyAlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub


        Log.e("onReceive", "ladskjflsakjdflskjdflskjdfslkjdflasdf");
        Toast.makeText(context, "OnReceive alarm test", Toast.LENGTH_SHORT).show();
        Intent unSleep = new Intent("unSleep");
        unSleep.putExtra("sleep",false);
        context.sendBroadcast(unSleep);
    }
}