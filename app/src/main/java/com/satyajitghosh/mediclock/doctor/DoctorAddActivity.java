package com.satyajitghosh.mediclock.doctor;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.satyajitghosh.mediclock.AlarmManagerHandler;
import com.satyajitghosh.mediclock.InputValidationHandler;
import com.satyajitghosh.mediclock.R;

import java.util.Calendar;
import java.util.Objects;

public class DoctorAddActivity extends AppCompatActivity {
    public static TextView date_view;
    private static final int[] arr = new int[3];
    private String name;
    private String reason;
    private TextInputLayout doctor_name;
    private TextInputLayout doctor_reason;
    private Button doctor_add_btn;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_add);
        date_view = findViewById(R.id.date_view);
        doctor_name = findViewById(R.id.doctor_name);
        doctor_reason = findViewById(R.id.doctor_reason);
        doctor_add_btn = findViewById(R.id.doctor_add_btn);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("AppointmentRecord").child(Objects.requireNonNull(account.getId()));


        doctor_add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = doctor_name.getEditText().getText().toString();
                reason = doctor_reason.getEditText().getText().toString();

                if(inputValidation(name,reason)){
                DoctorDataModel obj = new DoctorDataModel(name, reason, arr[0], arr[1], arr[2], AlarmManagerHandler.setUniqueNotificationId());
                mDatabase.child(obj.getName() + AlarmManagerHandler.setUniqueNotificationId()).setValue(obj);
                Toast.makeText(getApplicationContext(), "Added", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(DoctorAddActivity.this,DoctorActivity.class));
                }else{
                    InputValidationHandler.showDialog(DoctorAddActivity.this);
                }
            }
        });


    }
    public boolean inputValidation(String name,String reason){
        return !name.isEmpty() && !reason.isEmpty() && arr[0] != 0 && name.length()<15 && reason.length()<40;
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            String result = day + "/" + (month + 1) + "/" + year;
            date_view.setText(result);
            arr[0] = day;
            arr[1] = month + 1;
            arr[2] = year;
        }

    }
}
