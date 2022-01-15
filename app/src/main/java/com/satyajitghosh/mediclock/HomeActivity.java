package com.satyajitghosh.mediclock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

      name=findViewById(R.id.name);
      note=findViewById(R.id.note);
      submitBtn=findViewById(R.id.submitbtn);
      materialButtonToggleGroup=findViewById(R.id.toggleButton);
      radioGroup=findViewById(R.id.radioGroup);
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
                       time.add("0800");
                   }
                   else if(i==R.id.lunch){
                       time.add("1300");
                   }
                   else if(i==R.id.night){
                       time.add("2030");
                   }
                }

                MedicineRecordHandler mrh=new MedicineRecordHandler(
                        name.getEditText().getText().toString(),
                        note.getEditText().getText().toString(),
                        before_food,
                        time
                 );
                myRef.child("MedicineRecord").child(PersonID).child(Long.toString(maxID+1)).setValue(mrh);
            }
        });




    }


}