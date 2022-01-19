package com.satyajitghosh.mediclock;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This Activity is used for Updating the existing data from FireBase
 *
 * @author SATYAJIT GHOSH
 * @since 1.0.0
 */
public class UpdateActivity extends AppCompatActivity {
    private TextInputLayout name;
    private TextInputLayout note;
    private Button updateBtn;
    private RadioGroup radioGroup;
    private MaterialButtonToggleGroup materialButtonToggleGroup;
    private boolean before_food;
    private GoogleSignInClient mGoogleSignInClient;
    private Button cancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("MedicineRecord").child(Objects.requireNonNull(account.getId()));
        String PersonID = account.getId();

        name = findViewById(R.id.up_name);
        note = findViewById(R.id.up_note);
        radioGroup = findViewById(R.id.up_radioGroup);
        materialButtonToggleGroup = findViewById(R.id.up_toggleButton);
        updateBtn = findViewById(R.id.up_updatebtn);
        cancelBtn = findViewById(R.id.up_cancel);
        Intent intent = getIntent();
        String key = intent.getStringExtra("key");
        prefillData(intent);

        //This code is for update button
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (InputValidationHandler.inputValidation(UpdateActivity.this, getData().getName(), getData().getReminder())) {


                    myRef.child(key).setValue(getData()); //Updates the data to the FireBase DataBase

                    AlarmManagerHandler.initAlarm(getData(), getApplicationContext());

                    startActivity(
                            new Intent(UpdateActivity.this, DisplayMedicineActivity.class)
                                    .putExtra("UserName", account.getDisplayName()).putExtra("Id", account.getId())
                    );
                } else {
                    InputValidationHandler.showDialog(UpdateActivity.this);
                }


            }
        });
        //This code is for Cancel Button
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(
                        new Intent(UpdateActivity.this, DisplayMedicineActivity.class)
                                .putExtra("UserName", account.getDisplayName()).putExtra("Id", account.getId())
                );
            }
        });


    }

    /**
     * It prefills the data to the fields for convenience of user when the activity starts
     *
     * @param intent it takes an intent with extras
     */
    public void prefillData(Intent intent) {
        String nameValue = intent.getStringExtra("name");
        String noteValue = intent.getStringExtra("note");
        Boolean beforeFood = intent.getBooleanExtra("beforeFood", true);


        if (beforeFood) {
            radioGroup.check(R.id.up_radio_button_1);
        } else {
            radioGroup.check(R.id.up_radio_button_2);
        }

        name.getEditText().setText(nameValue);
        note.getEditText().setText(noteValue);


    }

    /**
     * It captures all the data from different fields
     *
     * @return it returns a object of MedicineRecordHandler class
     */
    public MedicineRecordHandler getData() {

        String nameValue = name.getEditText().getText().toString();
        String noteValue = note.getEditText().getText().toString();
        Boolean beforeFoodValue = false;
        if (radioGroup.getCheckedRadioButtonId() == R.id.up_radio_button_1) {
            beforeFoodValue = true;
        }

        List<Integer> arr = materialButtonToggleGroup.getCheckedButtonIds();
        ArrayList<TIME.AlarmBundle> time = new ArrayList<>();
        for (Integer i : arr) {
            if (i == R.id.up_morning) {
                time.add(new TIME.AlarmBundle(TIME.MORNING, AlarmManagerHandler.setUniqueNotificationId()));
            } else if (i == R.id.up_lunch) {
                time.add(new TIME.AlarmBundle(TIME.AFTERNOON, AlarmManagerHandler.setUniqueNotificationId()));
            } else if (i == R.id.up_night) {
                time.add(new TIME.AlarmBundle(TIME.NIGHT, AlarmManagerHandler.setUniqueNotificationId()));
            }
        }


        MedicineRecordHandler mrh = new MedicineRecordHandler(
                nameValue,
                noteValue,
                beforeFoodValue,
                time
        );

        return mrh;
    }


}


