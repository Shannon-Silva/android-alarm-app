package com.example.alarmsystem;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AppComponentFactory;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class Quiz extends AppCompatActivity {

    Button check;
    String questions[];
    String choices[][];
    String correctAnswer[];
    RadioButton opt1, opt2, opt3, opt4, radioButton;
    RadioGroup grp;
    TextView questionTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int heigt = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(heigt*.6));

        questions = new String[]{
                "Which is the largest animal in the world", //blue whale
                "Which is the largest animal on land", //elephant
                "Which is the smallest bird ?", //humming bird
                "Which is the largest fish in the world ?", //whale shark
                "Which is the tallest animal on the earth ?", //giraffe
                "Which is the biggest bird in the world ?", //ostrich
                "Which is the biggest continent in the world ?", //asia
                "Which is the largest country (area) in the world ?", //russia
                "Which is the longest river in the world ?", //nile
                "Which is the largest desert in the world ?"}; //sahara

        correctAnswer = new String[]{
                "Blue Whale",
                "Elephant",
                "Humming Bird",
                "Whale Shark",
                "Giraffe",
                "Ostrich",
                "Asia",
                "Russia",
                "Nile",
                "Sahara"
        };

        choices = new String[][] {
                {"Elephant","Giraffe","Shark","Blue Whale"},
                {"Elephant","Ostrich","Whale","Tiger"},
                {"Robin","Red Crow","Humming Bird", "Parrot"},
                {"Trout","Killer Whale","Whale Shark","Eel"},
                {"Giraffe","Bear","Crocodile","Ostrich"},
                {"Robin","Red Crow","Humming Bird","Ostrich"},
                {"Africa","Asia","Australia","Antarctica"},
                {"Brazil","Russia","Australia","Germany"},
                {"Yangtze","Nile","Mississippi","Ganges"},
                {"Antarctic","Sahara","Gobi","Atacama"}
        };

        //initialize buttons, TextView, radio group
        check = (Button) findViewById(R.id.check);
        opt1 = (RadioButton) findViewById(R.id.option1);
        opt2 = (RadioButton) findViewById(R.id.option2);
        opt3 = (RadioButton) findViewById(R.id.option3);
        opt4 = (RadioButton) findViewById(R.id.option4);
        questionTxt = (TextView) findViewById(R.id.questionTxt);
        grp = (RadioGroup) findViewById(R.id.group);

        //get random question
        final int index = new Random().nextInt(questions.length);
        questionTxt.setText(questions[index]);
        opt1.setText(getOption1(index));
        opt2.setText(getOption2(index));
        opt3.setText(getOption3(index));
        opt4.setText(getOption4(index));

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selected_id;
                String selectedAns;

                //get selected radio button from the group
                selected_id = grp.getCheckedRadioButtonId();

                //find the radio button by selected id
                radioButton=(RadioButton) findViewById(selected_id);
                selectedAns = radioButton.getText().toString();

                if(selectedAns == getCorrectAnswer(index)){
                    Log.e("correct ans","correct");

                    Intent intent = new Intent(Quiz.this,Ringtone.class);
                    intent.putExtra("want","no");

                    startService(intent);
                    finish();
                } else {
                    finish();
                    startActivity(getIntent());
                    Log.e("msg","try again!");
                }
            }
        });
    }

    public String getQuestion(int a){
        String question = questions[a];
        return question;
    }

    public String getOption1(int a){
        String choice = choices[a][0];
        return choice;
    }

    public String getOption2(int a){
        String choice = choices[a][1];
        return choice;
    }

    public String getOption3(int a){
        String choice = choices[a][2];
        return choice;
    }

    public String getOption4(int a){
        String choice = choices[a][3];
        return choice;
    }

    public String getCorrectAnswer(int a){
        String answer = correctAnswer[a];
        return answer;
    }
}
