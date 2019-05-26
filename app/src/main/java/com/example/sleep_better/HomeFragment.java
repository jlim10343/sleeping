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

public class HomeFragment extends Fragment {
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    private TimePicker time;
    private boolean isAsleep;
    private static MediaPlayer audio;
    private final String TAG = "in MainActivity";

    public static void setAudio(MediaPlayer m) { audio=m; }

    @Override
    @Nullable
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);

        time=(TimePicker) view.findViewById(R.id.timePicker1);
        alarmMgr = (AlarmManager) this.getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this.getContext(), MyAlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(this.getContext(), 0, intent, 0);
        isAsleep = false;

        Button b = view.findViewById(R.id.timeButton);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,Integer.toString(time.getCurrentHour()));
                getActivity().registerReceiver(broadcastReceiver, new IntentFilter("unSleep"));
                Intent sleep = new Intent("doSleep");
                sleep.putExtra("sleep",true);
                getActivity().sendBroadcast(sleep);
                isAsleep = true;
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.HOUR_OF_DAY,time.getCurrentHour());
                calendar.set(Calendar.MINUTE,time.getCurrentMinute()-1);
                //frag = new AccelerometerFragment();
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                //      frag).commit();

                alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                        calendar.getTimeInMillis(), AlarmManager.INTERVAL_FIFTEEN_MINUTES, alarmIntent);

                Intent intent = new Intent(getContext(), AppActive.class);
                startActivity(intent);
            }
        });


        audio = MediaPlayer.create(this.getContext(),R.raw.analogwatchalarmdanielsimion);

        return view;
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle b = intent.getExtras();
            isAsleep = b.getBoolean("sleep");
            getActivity().unregisterReceiver(broadcastReceiver);
            //frag = null;
            Log.e(TAG, "Alarm resolved");
            //audio = MediaPlayer.create(context,R.raw.analogwatchalarmdanielsimion);
            audio.start();
        }
    };

}
