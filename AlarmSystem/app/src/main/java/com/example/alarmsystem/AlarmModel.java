package com.example.alarmsystem;

public class AlarmModel {
    String alarmTime;
    String alarmName;

    public AlarmModel(String alarmTime, String alarmName) {
        this.alarmName=alarmName;
        this.alarmTime=alarmTime;
    }

    public String getAlarmTime() {
        return alarmTime;
    }

    public String getAlarmName() {
        return alarmName;
    }
}
