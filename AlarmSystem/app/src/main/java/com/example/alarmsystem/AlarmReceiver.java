package com.example.alarmsystem;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.e("in receiver","yay");

        //fetch extra string from intent
        String extra_String = intent.getExtras().getString("Extra");

        //fetch alarm song choice
        Long extra_selection = intent.getExtras().getLong("alarm_song");

        //create service intent
        Intent serviceIntent = new Intent(context, Ringtone.class);

        //pass extra string from .Main Activity to .Ringtone
        serviceIntent.putExtra("extra", extra_String);

        //pass extra selection -> .Ringtone
        serviceIntent.putExtra("alarm_long",extra_selection);

        //start service
        context.startService(serviceIntent);

    }
}
