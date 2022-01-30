package com.satyajitghosh.mediclock.doctor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.satyajitghosh.mediclock.R;

public class DoctorAddActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_add);
        findViewById(R.id.date_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialDatePicker datePicker =
                        MaterialDatePicker.Builder.datePicker()
                                .setTitleText("Select date")
                                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                                .build();

                datePicker.show(getSupportFragmentManager(), "tag");

            }
        });
    }
}