package com.satyajitghosh.mediclock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;

import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textfield.TextInputLayout;

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

        Intent intent=getIntent();
        String nameValue=intent.getStringExtra("name");
        String noteValue=intent.getStringExtra("note");

        name.getEditText().setText(nameValue);
        note.getEditText().setText(noteValue);


    }
}