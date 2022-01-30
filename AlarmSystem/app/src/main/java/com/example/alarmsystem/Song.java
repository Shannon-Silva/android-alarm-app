package com.example.alarmsystem;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Song extends AppCompatActivity {

    Context context;
    Button select, cancel;
    RadioGroup grp;
    RadioButton rb;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.song);
        this.context = this;

        //popup dimensions
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int heigt = dm.heightPixels;

        getWindow().setLayout((int)(width*.55),(int)(heigt*.35));

        //initializa
        cancel = (Button) findViewById(R.id.cancel);
        select = (Button) findViewById(R.id.select);
        grp = (RadioGroup) findViewById(R.id.group);

        //setVolumeControlStream(AudioManager.STREAM_MUSIC);
        mp=null;
        int rbid=grp.indexOfChild(findViewById(grp.getCheckedRadioButtonId()));
        if(rbid==0){
            playAudio(R.raw.alarm_1);
        } else if (rbid==1){
            playAudio(R.raw.alarm_2);
        } else if (rbid==2){
            playAudio(R.raw.alarm_3);
        } else if (rbid==3){
            playAudio(R.raw.alarm_4);
        } else if (rbid==4){
            playAudio(R.raw.alarm_5);
        }

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent i = getIntent();
        String message=i.getStringExtra("msg");

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selected_id;

                //get selected radio button from the group
                selected_id=grp.indexOfChild(findViewById(grp.getCheckedRadioButtonId()));
                RadioButton r = (RadioButton) grp.getChildAt(selected_id);
                String num = r.getText().toString();

                if(!num.equals(""))
                {
                    //Create new Intent Object, and specify class
                    Intent intent = new Intent();
//
                    //Set your data using putExtra method which take
                    //any key and value which we want to send
                    intent.putExtra("id",selected_id);

                    //send back the intent and resilt code as response to .Pop
                    setResult(RESULT_OK,intent);
                }
//                mp.release();
//                mp=null;
                finish();
            }
        });
    }

    public void playAudio(int audioId)
    {
        // stop the previous playing audio
        if(mp != null && mp.isPlaying())
        {
            mp.stop();
            mp.release();
            mp = null;
        }
        mp = MediaPlayer.create(this, audioId);
        mp.start();
    }
}
