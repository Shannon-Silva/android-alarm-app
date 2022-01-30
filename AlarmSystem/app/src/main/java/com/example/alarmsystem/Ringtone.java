package com.example.alarmsystem;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.security.Provider;

public class Ringtone extends Service {

    MediaPlayer mediaPlayer;
    int startId;
    boolean ringtone;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService","Received start id " + startId + ": " + intent);

        //fetch extra string
        String s = intent.getExtras().getString("extra");

        //fetch extra_Selection
        Long selection = intent.getExtras().getLong("alarm_long");

        String sss = intent.getExtras().getString("want");
        if(sss == "no"){
            this.ringtone=false;
            this.startId=0;
            stopSelf();
        }

        if (s !=null && s.equals("on")) {
            startId = 1;

        } else if (s !=null && s.equals("off")) {
            startId = 0;

        } else {
            startId = 0;
        }

        if(!this.ringtone && startId== 1){
            //no music playing && press alarm on -> start playing
            this.ringtone=true;
            this.startId=0;

            //play based on selection
            if(selection == 0){
                //play Alarm 1
                mediaPlayer = MediaPlayer.create(this, R.raw.alarm_1);
                mediaPlayer.setLooping(true);
                mediaPlayer.start();
            } else if (selection == 1){
                //play Alarm 2
                mediaPlayer = MediaPlayer.create(this, R.raw.alarm_2);
                mediaPlayer.setLooping(true);
                mediaPlayer.start();
            } else if (selection == 2){
                //play Alarm 3
                mediaPlayer = MediaPlayer.create(this, R.raw.alarm_3);
                mediaPlayer.setLooping(true);
                mediaPlayer.start();
            } else if (selection == 3){
                //play Alarm 4
                mediaPlayer = MediaPlayer.create(this, R.raw.alarm_4);
                mediaPlayer.setLooping(true);
                mediaPlayer.start();
            } else if (selection == 4){
                //play Alarm 5
                mediaPlayer = MediaPlayer.create(this, R.raw.alarm_5);
                mediaPlayer.setLooping(true);
                mediaPlayer.start();
            } else {
                Log.e("error ringtone","error");
            }
            Intent dialogIntent = new Intent(this, Quiz.class);
            dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.startActivity(dialogIntent);

        } else if(this.ringtone && startId==0){
            //music is playing && press alarm off -> stop playing
            mediaPlayer.stop();
            mediaPlayer.reset();

            this.ringtone=false;
            this.startId=0;

        } else if(!this.ringtone && startId==0){
            //no music playing && press alarm off -> do nothing
            this.ringtone=false;
            this.startId=0;

        } else if(this.ringtone && startId==1){
            //music playing && press alarm on -> do nothing
            this.ringtone=false;
            this.startId=0;

        } else{
            Log.e("error","error");
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.ringtone=false;

        this.stopSelf();
        Log.e("d","des");
    }
}
