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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private TextView textView;
    private Button logout;
    private GoogleSignInClient mGoogleSignInClient;

    private EditText name;
    private EditText note;
    private EditText time;
    private RadioButton beforeORafterFood;
    private Button submitBtn;
    private long maxID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        name=findViewById(R.id.name);
        note=findViewById(R.id.note);
        time=findViewById(R.id.time);
        submitBtn=findViewById(R.id.submitbtn);
        beforeORafterFood=findViewById(R.id.afterorbeforeradio);
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
        textView=findViewById(R.id.textView);
        textView.setText(nameUser+" Logged In");
        logout=findViewById(R.id.button);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGoogleSignInClient.signOut();
                Toast.makeText(getApplicationContext(),"Logged Out",Toast.LENGTH_LONG).show();
                startActivity(new Intent(HomeActivity.this,MainActivity.class));
            }
        });
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

                boolean beAfter=false;
                if(beforeORafterFood.isChecked()){
                    beAfter=true;
                }
                ArrayList<String> arr=new ArrayList<>();
                arr.add(time.getText().toString());

                MedicineRecordHandler mrh=new MedicineRecordHandler(
                        name.getText().toString(),
                        note.getText().toString(),
                        beAfter,
                        arr
                 );
                myRef.child("MedicineRecord").child(PersonID).child(Long.toString(maxID+1)).setValue(mrh);
            }
        });




    }


}