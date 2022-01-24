package com.satyajitghosh.mediclock;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * This Activity is used for adding new data to the FireBase Database.
 *
 * @author SATYAJIT GHOSH
 * @since 1.0.0
 */
public class HomeActivity extends AppCompatActivity {
    private Button logout;
    private GoogleSignInClient mGoogleSignInClient;

    private TextInputLayout name;
    private TextInputLayout note;
    private Button submitBtn;
    private RadioGroup radioGroup;
    private MaterialButtonToggleGroup materialButtonToggleGroup;
    private boolean before_food;
    private Button show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        name = findViewById(R.id.name);
        note = findViewById(R.id.note);
        submitBtn = findViewById(R.id.submitbtn);
        materialButtonToggleGroup = findViewById(R.id.toggleButton);
        radioGroup = findViewById(R.id.radioGroup);
        show = findViewById(R.id.show);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        Intent intent = getIntent();
        String nameUser = intent.getStringExtra("UserName");
        String PersonID = intent.getStringExtra("Id");


        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, DisplayMedicineActivity.class));
            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                before_food = R.id.radio_button_1 == radioGroup.getCheckedRadioButtonId();
                List<Integer> arr = materialButtonToggleGroup.getCheckedButtonIds();
                ArrayList<TIME.AlarmBundle> time = new ArrayList<>();
                for (Integer i : arr) {
                    if (i == R.id.morning) {
                        time.add(new TIME.AlarmBundle(TIME.MORNING, AlarmManagerHandler.setUniqueNotificationId()));
                    } else if (i == R.id.lunch) {
                        time.add(new TIME.AlarmBundle(TIME.AFTERNOON, AlarmManagerHandler.setUniqueNotificationId()));
                    } else if (i == R.id.night) {
                        time.add(new TIME.AlarmBundle(TIME.NIGHT, AlarmManagerHandler.setUniqueNotificationId()));
                    }
                }

                //It checks for all the required fields are filed with data or not .
                if (InputValidationHandler.inputValidation(HomeActivity.this, name.getEditText().getText().toString(), time)) {

                    MedicineRecordHandler mrh = new MedicineRecordHandler(
                            name.getEditText().getText().toString(),
                            note.getEditText().getText().toString(),
                            before_food,
                            time
                    );
                    myRef.child("MedicineRecord").child(PersonID).child(mrh.getName() + AlarmManagerHandler.setUniqueNotificationId()).setValue(mrh); // It writes the new data to FireBase Database
                    //  AlarmManagerHandler.initAlarm(mrh, getApplicationContext()); //It set up the alarm for that record
                    Toast.makeText(getApplicationContext(), "Added Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    InputValidationHandler.showDialog(HomeActivity.this); // It shows a dialog box informing user to fill the required fields.
                }
            }
        });

    }


}