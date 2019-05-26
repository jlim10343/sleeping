package com.example.sleep_better;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;

import java.util.Calendar;

//Fragment for managing alarms
public class HomeFragment extends Fragment {
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    private TimePicker time;
    private static MediaPlayer audio;
    private final String TAG = "in HomeFragment";

    public static void setAudio(MediaPlayer m) { audio=m; }

    //Alarm set page
    @Override
    @Nullable
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Sets up alarms and TimePicker
        View view = inflater.inflate(R.layout.activity_main, container, false);
        time=(TimePicker) view.findViewById(R.id.timePicker1);
        alarmMgr = (AlarmManager) this.getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this.getContext(), MyAlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(this.getContext(), 0, intent, 0);

        Button b = view.findViewById(R.id.timeButton);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Gets time from TimePicker
                getActivity().registerReceiver(broadcastReceiver, new IntentFilter("unSleep"));
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.HOUR_OF_DAY,time.getCurrentHour());
                calendar.set(Calendar.MINUTE,time.getCurrentMinute()-1);

                //Sets an alarm for the specified time to repeat every 15 minutes
                alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                        calendar.getTimeInMillis(), AlarmManager.INTERVAL_FIFTEEN_MINUTES, alarmIntent);

                //Opens AppActive class
                Intent intent = new Intent(getContext(), AppActive.class);
                startActivity(intent);
            }
        });

        //Sets up alarm
        audio = MediaPlayer.create(this.getContext(),R.raw.analogwatchalarmdanielsimion);

        return view;
    }

    //Receives alarm feedback broadcast and plays alarm
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            getActivity().unregisterReceiver(broadcastReceiver);
            Log.d(TAG, "Alarm resolved");
            if(!intent.getBooleanExtra("sleep",false)) {
                audio.start();
            }

        }
    };


}
