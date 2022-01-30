package com.example.alarmsystem;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;

public class Pop extends Activity implements AdapterView.OnItemSelectedListener{

    AlarmManager alarmManager;
    TimePicker timePicker;
    EditText alarmName;
    Context context;
    Button add, cancel;
    PendingIntent pendingIntent;
    Long selected_alarm_song;
    ArrayList<String> times = new ArrayList<String>();
    ArrayList<String> names = new ArrayList<String>();
    String time, label;
    Intent intent,shano;
    Spinner spinner;
    public static final int CODE =1;
    public static final int CODE1 =2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datepicker);
        this.context = this;

        //popup dimensions
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int heigt = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(heigt*.8));

        //initialize Alarm manager, timepicker, alarm Name, buttons, spinner, listView
         alarmManager=(AlarmManager) getSystemService(ALARM_SERVICE);
         timePicker = (TimePicker) findViewById(R.id.timePicker);
         alarmName = (EditText) findViewById(R.id.alarmNameLabel);
         add = (Button) findViewById(R.id.add);
         cancel = (Button) findViewById(R.id.cancel);
         spinner = (Spinner) findViewById(R.id.spinner1);

         //Alarm receiver intent
         intent = new Intent(this.context, AlarmReceiver.class);

         //calendar instance
         final Calendar calendar = Calendar.getInstance();

        //Ringtone spinner
        //create an array adapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tones, android.R.layout.simple_spinner_item);

        //specify the layout to use when the list of choices appear
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);

        //apply the adapter to the spinner
        spinner.setAdapter(adapter);

        //onclick listeners
         add.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 label= alarmName.getText().toString();
                 //set calendar to timepicker hour and min
                 calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
                 calendar.set(Calendar.MINUTE,timePicker.getMinute());

                 int hour = timePicker.getHour();
                 int min = timePicker.getMinute();

                 String hr_string = String.valueOf(hour);
                 String min_String = String.valueOf(min);

                 if(hour > 12) {
                     hr_string = String.valueOf(hour);
                 }
                 if(min <10){
                     min_String = "0"+String.valueOf(min);
                 }
                 time=hr_string+ ":" +min_String;

                 intent.putExtra("Extra", "on");

                 Intent i = new Intent();
                 i.putExtra("timeString",time);
                 i.putExtra("nameString",label);
                 setResult(RESULT_OK,i);

                 Log.e("timeString",time);
                 Log.e("nameStr",label);

                 //pass the extra long value in the intent
                 intent.putExtra("alarm_song", selected_alarm_song);

                 //add to time array & name array
                 String str = alarmName.getText().toString();
                 names.add(str);
                 times.add(hr_string+": "+min_String);

                 //pending intent
                 pendingIntent = PendingIntent.getBroadcast(Pop.this,0,
                         intent,PendingIntent.FLAG_UPDATE_CURRENT);

                 //set alarm manager
                 alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),
                         pendingIntent);

                 finish();
             }
         });

        Intent shut = new Intent();
        String msg = shut.getStringExtra("shut");
        if(msg != null && msg== "shut"){
            alarmManager.cancel(pendingIntent);
            sendBroadcast(intent);
        }

         cancel.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 finish();
             }
         });

         View.OnTouchListener Spinner_OnTouch = new View.OnTouchListener() {
             public boolean onTouch(View v, MotionEvent event) {
                 if (event.getAction() == MotionEvent.ACTION_UP) {
                     Intent intent = new Intent(Pop.this,Song.class);
                     intent.putExtra("msg","msg");
                     startActivityForResult(intent,CODE);
                 }
                 return true;
             }
         };

         spinner.setOnTouchListener(Spinner_OnTouch);

     }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case CODE:
                if(resultCode == RESULT_OK){
                    int id = data.getExtras().getInt("id");
                    spinner.setSelection(id);
                    Log.e("iddddd", String.valueOf(id));
                    selected_alarm_song= Long.valueOf(id);
                }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long id) {
        Bundle extra =getIntent().getExtras();
            selected_alarm_song = id;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}



