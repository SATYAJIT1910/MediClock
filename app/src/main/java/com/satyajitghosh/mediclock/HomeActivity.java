package com.satyajitghosh.mediclock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.lang.Math;
import java.util.Random;

public class HomeActivity extends AppCompatActivity {
    private Button logout;
    private GoogleSignInClient mGoogleSignInClient;

    private TextInputLayout name;
    private TextInputLayout note;
    private Button submitBtn;
    private RadioGroup radioGroup;
    private MaterialButtonToggleGroup materialButtonToggleGroup;
    private long maxID;
    private boolean before_food;
    private Button show;
    private static int notificationID=(int)new Random().nextInt(99999 - 00001) + 00001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

      name=findViewById(R.id.name);
      note=findViewById(R.id.note);
      submitBtn=findViewById(R.id.submitbtn);
      materialButtonToggleGroup=findViewById(R.id.toggleButton);
      radioGroup=findViewById(R.id.radioGroup);
      show=findViewById(R.id.show);
      maxID=0;

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        Intent intent = getIntent();
       String nameUser = intent.getStringExtra("UserName");
       String PersonID=intent.getStringExtra("Id");
//        logout=findViewById(R.id.button);
//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mGoogleSignInClient.signOut();
//                Toast.makeText(getApplicationContext(),"Logged Out",Toast.LENGTH_LONG).show();
//                startActivity(new Intent(HomeActivity.this,MainActivity.class));
//            }
//        });
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    maxID=(snapshot.child("MedicineRecord").child(PersonID).getChildrenCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,DisplayMedicineActivity.class));
            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               if(R.id.radio_button_1==radioGroup.getCheckedRadioButtonId()){
                   before_food=true;
               }else{
                   before_food=false;
               }
               List<Integer> arr= materialButtonToggleGroup.getCheckedButtonIds();
               ArrayList<String> time=new ArrayList<>();
               for (Integer i:arr){
                   if(i==R.id.morning){
                       time.add(TIME.MORNING);

                   }
                   else if(i==R.id.lunch){
                       time.add(TIME.AFTERNOON);
                   }
                   else if(i==R.id.night){
                       time.add(TIME.NIGHT);
                   }
                }

                MedicineRecordHandler mrh=new MedicineRecordHandler(
                        name.getEditText().getText().toString(),
                        note.getEditText().getText().toString(),
                        before_food,
                        time,
                        notificationID++
                 );
                myRef.child("MedicineRecord").child(PersonID).child(Long.toString(maxID+1)).setValue(mrh);
                AlarmManagerHandler.initAlarm(mrh,getApplicationContext());
                Toast.makeText(getApplicationContext(), "Added Successfully", Toast.LENGTH_SHORT).show();
            }
        });

    }


    }