package com.example.zeid.lab5;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;


public class BatteryLevelReceiver extends BroadcastReceiver
{
    private static final String TAG = BatteryLevelReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent)
    {
        Settings.System.putInt(context.getContentResolver(),Settings.System.SCREEN_BRIGHTNESS,50);
        Log.d("Battery Status","Battery Low");
    }
}