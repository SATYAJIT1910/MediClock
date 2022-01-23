package com.satyajitghosh.mediclock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

public class TimeChangeActivity extends AppCompatActivity {
    String time = "0000";
    private TextView morning_edit_time;
    private TextView lunch_edit_time;
    private TextView night_edit_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_change);
        morning_edit_time=findViewById(R.id.morning_edit_time);
        lunch_edit_time=findViewById(R.id.afternoon_edit_time);
        night_edit_time=findViewById(R.id.night_edit_time);

        Animation animation= AnimationUtils.loadAnimation(this,R.anim.fade);

        morning_edit_time.startAnimation(animation);
        lunch_edit_time.startAnimation(animation);
        night_edit_time.startAnimation(animation);


        morning_edit_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MaterialTimePicker picker =
                        new MaterialTimePicker.Builder()
                                .setTimeFormat(TimeFormat.CLOCK_24H)
                                .setHour(12)
                                .setMinute(10)
                                .build();

                picker.show(getSupportFragmentManager(), "tag");

                picker.addOnPositiveButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        morning_edit_time.setText(timeTextView(picker.getHour(),picker.getMinute()));
                    }
                });

            }
        });
        lunch_edit_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MaterialTimePicker picker =
                        new MaterialTimePicker.Builder()
                                .setTimeFormat(TimeFormat.CLOCK_24H)
                                .setHour(12)
                                .setMinute(10)
                                .build();

                picker.show(getSupportFragmentManager(), "tag");

                picker.addOnPositiveButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        lunch_edit_time.setText(timeTextView(picker.getHour(),picker.getMinute()));
                    }
                });

            }
        });
        night_edit_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MaterialTimePicker picker =
                        new MaterialTimePicker.Builder()
                                .setTimeFormat(TimeFormat.CLOCK_24H)
                                .setHour(12)
                                .setMinute(10)
                                .build();

                picker.show(getSupportFragmentManager(), "tag");

                picker.addOnPositiveButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        night_edit_time.setText(timeTextView(picker.getHour(),picker.getMinute()));
                    }
                });

            }
        });
    }
    public String timeTextView(int hour,int minute){
        String result="";
        if(hour<10){
            result="0"+Integer.toString(hour);
        }
        else{
            result=Integer.toString(hour);
        }
        result+=":";
        if(minute<10){
            result+="0"+Integer.toString(minute);
        }
        else{
            result+=Integer.toString(minute);
        }

        return result;
    }

    }
