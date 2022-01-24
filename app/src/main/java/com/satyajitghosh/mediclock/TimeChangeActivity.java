package com.satyajitghosh.mediclock;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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
        morning_edit_time = findViewById(R.id.morning_edit_time);
        lunch_edit_time = findViewById(R.id.afternoon_edit_time);
        night_edit_time = findViewById(R.id.night_edit_time);

        morning_edit_time.setText(TIMEClassToTextView(TIME.MORNING));
        lunch_edit_time.setText(TIMEClassToTextView(TIME.AFTERNOON));
        night_edit_time.setText(TIMEClassToTextView(TIME.NIGHT));


        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fade);

        morning_edit_time.startAnimation(animation);
        lunch_edit_time.startAnimation(animation);
        night_edit_time.startAnimation(animation);


        morning_edit_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MaterialTimePicker picker =
                        new MaterialTimePicker.Builder()
                                .setTimeFormat(TimeFormat.CLOCK_24H)
                                .setHour(TIMEClassToHour(TIME.MORNING))
                                .setMinute(TIMEClassToMinutes(TIME.MORNING))
                                .build();

                picker.show(getSupportFragmentManager(), "tag");

                picker.addOnPositiveButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        morning_edit_time.setText(timeTextView(picker.getHour(), picker.getMinute()));
                        String time_to_string = timeToString(picker.getHour(), picker.getMinute());
                        setTime(time_to_string, 0);
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
                                .setHour(TIMEClassToHour(TIME.AFTERNOON))
                                .setMinute(TIMEClassToMinutes(TIME.AFTERNOON))
                                .build();

                picker.show(getSupportFragmentManager(), "tag");

                picker.addOnPositiveButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        lunch_edit_time.setText(timeTextView(picker.getHour(), picker.getMinute()));
                        String time_to_string = timeToString(picker.getHour(), picker.getMinute());
                        setTime(time_to_string, 1);
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
                                .setHour(TIMEClassToHour(TIME.NIGHT))
                                .setMinute(TIMEClassToMinutes(TIME.NIGHT))
                                .build();

                picker.show(getSupportFragmentManager(), "tag");

                picker.addOnPositiveButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        night_edit_time.setText(timeTextView(picker.getHour(), picker.getMinute()));
                        String time_to_string = timeToString(picker.getHour(), picker.getMinute());
                        setTime(time_to_string, 2);
                    }
                });

            }
        });
    }

    public String timeTextView(int hour, int minute) {
        String result = "";
        if (hour < 10) {
            result = "0" + hour;
        } else {
            result = Integer.toString(hour);
        }
        result += ":";
        if (minute < 10) {
            result += "0" + minute;
        } else {
            result += Integer.toString(minute);
        }

        return result;
    }

    public String timeToString(int hour, int minute) {
        String result = "";
        if (hour < 10) {
            result = "0" + hour;
        } else {
            result = Integer.toString(hour);
        }
        if (minute < 10) {
            result += "0" + minute;
        } else {
            result += Integer.toString(minute);
        }

        return result;
    }


    public String TIMEClassToTextView(String time) {
        return time.substring(0, 2) + ":" + time.substring(2, 4);
    }

    public int TIMEClassToHour(String time) {
        return Integer.parseInt(time.substring(0, 2));

    }

    public int TIMEClassToMinutes(String time) {
        return Integer.parseInt(time.substring(2, 4));
    }


    public void getTime() {
        SharedPreferences sharedPref = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        TIME.MORNING = sharedPref.getString("MORNING", "");
        TIME.AFTERNOON = sharedPref.getString("AFTERNOON", "");
        TIME.NIGHT = sharedPref.getString("NIGHT", "");
    }


    public void setTime(String time, int flag) {
        SharedPreferences sharedPref = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        if (flag == 0) {
            editor.putString("MORNING", time);
        } else if (flag == 1) {
            editor.putString("AFTERNOON", time);
        } else if (flag == 2) {
            editor.putString("NIGHT", time);
        }
        editor.apply();
        getTime();
    }

}
