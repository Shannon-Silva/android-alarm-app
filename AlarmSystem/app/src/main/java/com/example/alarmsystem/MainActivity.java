package com.example.alarmsystem;

import android.app.ActionBar;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fab;
    Context context;
    ListView list;
    ArrayList<AlarmModel> alarmModels = new ArrayList<AlarmModel>();
    private CustomAdapter adapter;
    String time, label;
    TextView textView;
    ImageButton delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        ActionBar bar = getActionBar();
//        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#004D40")));

        fab = (FloatingActionButton) findViewById(R.id.fab);
        list = (ListView) findViewById(R.id.list);
        textView = (TextView) findViewById(R.id.txtView);
        delete = (ImageButton) findViewById(R.id.delete);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getApplicationContext(), Pop.class),999);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==999 && resultCode == RESULT_OK){
            time =data.getStringExtra("timeString");
            label = data.getStringExtra("nameString");
            textView.setText(null);

            alarmModels= new ArrayList<>();

            if(adapter == null){
                alarmModels.add(new AlarmModel(time,label));
                //adapter = new CustomAdapter(alarmModels,getApplicationContext());
                //list.setAdapter(adapter);
                adapter = new CustomAdapter(alarmModels, this);
                list.setAdapter(adapter);
            } else {
                //alarmModels.add(new AlarmModel(time,label));
                AlarmModel alarm = new AlarmModel(time, label);
                adapter.add(alarm);
            }

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    AlarmModel alarmModel=alarmModels.get(i);
                    //snack bar
//                Snackbar.make(view, dataModel.getName()+"\n"+dataModel.getType()+" API: "+dataModel.getVersion_number(), Snackbar.LENGTH_LONG)
//                        .setAction("No action", null).show();
                }
            });
        }
    }

    //method to delete an alarm row
    public void deleteAlarm(int position){
        final int deletePosition = position;

        final AlertDialog.Builder dialogBox = new AlertDialog.Builder(
                MainActivity.this);

        dialogBox.setTitle("Delete Alarm");
        dialogBox.setMessage("Are you sure you want to delete this alarm?");
        dialogBox.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //after yes is clicked

                alarmModels.remove(deletePosition);
                adapter.notifyDataSetChanged();
                adapter.notifyDataSetInvalidated();
            }
        });

        dialogBox.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialogBox.show();
    }
}
