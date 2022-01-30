package com.example.alarmsystem;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<AlarmModel> implements View.OnClickListener{

    private ArrayList<AlarmModel> alarms;
    Context context;

    //view lookup cache
    private static class ViewHolder {
        TextView timeText;
        TextView labelText;
    }

    public CustomAdapter(ArrayList<AlarmModel> data, Context context) {
        super(context, R.layout.alarm_row, data);
        this.alarms = data;
        this.context=context;
    }

    @Override
    public void onClick(View view) {
        int position=(Integer) view.getTag();
        Object object= getItem(position);
        AlarmModel alarmModel=(AlarmModel) object;
    }

    private int lastPosition = -1;

    public View getView(int position, View convertView, ViewGroup parent){
        //get data item from this position
        AlarmModel alarmModel=getItem(position);
        //check if an existing view is being reuse, otherwise inflate the view
        ViewHolder viewHolder; //view lookup cache stored in tag

        final View result;

        if(convertView == null){
            viewHolder= new ViewHolder();
            LayoutInflater inflater=LayoutInflater.from(getContext());
            convertView=inflater.inflate(R.layout.alarm_row, parent, false);
            viewHolder.timeText=(TextView)convertView.findViewById(R.id.timeText);
            viewHolder.labelText=(TextView) convertView.findViewById(R.id.nameText);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder=(ViewHolder) convertView.getTag();
            result=convertView;
        }

        viewHolder.timeText.setText(alarmModel.getAlarmTime());
        viewHolder.labelText.setText(alarmModel.getAlarmName());

        return convertView;
    }

}
