package com.satyajitghosh.mediclock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.RadioGroup;

import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class UpdateActivity extends AppCompatActivity {
    private TextInputLayout name;
    private TextInputLayout note;
    private Button submitBtn;
    private RadioGroup radioGroup;
    private MaterialButtonToggleGroup materialButtonToggleGroup;
    private boolean before_food;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        name=findViewById(R.id.up_name);
        note=findViewById(R.id.up_note);
        radioGroup=findViewById(R.id.up_radioGroup);
        materialButtonToggleGroup=findViewById(R.id.up_toggleButton);
        Intent intent=getIntent();
        String nameValue=intent.getStringExtra("name");
        String noteValue=intent.getStringExtra("note");
        Boolean beforeFood=intent.getBooleanExtra("beforeFood",true);
        ArrayList<String> time= intent.getStringArrayListExtra("time");
        //Log.d("CheckValue",Boolean.toString(beforeFood));
        if(beforeFood){
            radioGroup.check(R.id.up_radio_button_1);
        }
        else{
            radioGroup.check(R.id.up_radio_button_2);
        }
        for(String i:time){
            if(i.contains("0800")){
                materialButtonToggleGroup.check(R.id.up_morning);
            }
            else if(i.contains("1300")){
                materialButtonToggleGroup.check(R.id.up_lunch);
            }
            else if(i.contains("2030")){
                materialButtonToggleGroup.check(R.id.up_night);
            }
        }

        name.getEditText().setText(nameValue);
        note.getEditText().setText(noteValue);


    }
}