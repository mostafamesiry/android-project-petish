package com.example.zeid.lab5;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.VideoView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

/**
 * Created by MostafaEl-Messiry on 10/15/16.
 */

public class NotificationEventReceiver extends WakefulBroadcastReceiver {

    private static final String ACTION_START_NOTIFICATION_SERVICE = "ACTION_START_NOTIFICATION_SERVICE";
    private static final String ACTION_DELETE_NOTIFICATION = "ACTION_DELETE_NOTIFICATION";
    private static final double NOTIFICATIONS_INTERVAL_IN_SECONDS = 0.00027778;
    private static int NOTIFICATION_ID = 1;
    public static AppCompatActivity mainActivity;
    public static SeekBar seekBar;
    public static VideoView videoView;
    public static int second;

    public static void setupAlarm(SeekBar s, VideoView v, Context context, AppCompatActivity main) {
        seekBar=s;
        videoView = v;
        mainActivity=main;
        second = 0;
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent alarmIntent = getStartPendingIntent(context);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                getTriggerAt(new Date()),
                ( (int) (NOTIFICATIONS_INTERVAL_IN_SECONDS * AlarmManager.INTERVAL_HOUR)),
                alarmIntent);
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if(videoView.isPlaying())
        {
            double set = (videoView.getCurrentPosition()*10)/5573;
            Log.d("SET",set+"");
            seekBar.setProgress((int)
                    set);
        }
        Log.d("VIDEO",videoView.getCurrentPosition()+"");

        if (second == 10) {
            second = 0;
            final NotificationCompat.Builder builder = new NotificationCompat.Builder(mainActivity);
            builder.setContentTitle("AD")
                    .setAutoCancel(true)
                    .setColor(mainActivity.getResources().getColor(R.color.colorAccent))
                    .setContentText("Enjoying our free stream, subscribe to our premium for better quality")
                    .setSmallIcon(R.mipmap.ic_launcher);
            NOTIFICATION_ID++;
            final NotificationManager manager = (NotificationManager) mainActivity.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(NOTIFICATION_ID, builder.build());
        } else {
            second++;


        }
    }


    private static long getTriggerAt(Date now) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        return calendar.getTimeInMillis();
    }

    private static PendingIntent getStartPendingIntent(Context context) {
        Intent intent = new Intent(context, NotificationEventReceiver.class);
        intent.setAction(ACTION_START_NOTIFICATION_SERVICE);
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public static PendingIntent getDeleteIntent(Context context) {
        Intent intent = new Intent(context, NotificationEventReceiver.class);
        intent.setAction(ACTION_DELETE_NOTIFICATION);
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}