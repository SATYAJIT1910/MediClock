package com.satyajitghosh.mediclock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class UpdateActivity extends AppCompatActivity {
    private TextInputLayout name;
    private TextInputLayout note;
    private Button updateBtn;
    private RadioGroup radioGroup;
    private MaterialButtonToggleGroup materialButtonToggleGroup;
    private boolean before_food;
    private GoogleSignInClient mGoogleSignInClient;
    private Button cancelBtn;
    private static int notificationID=(int)Math.random()*100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        String PersonID=account.getId();

        name=findViewById(R.id.up_name);
        note=findViewById(R.id.up_note);
        radioGroup=findViewById(R.id.up_radioGroup);
        materialButtonToggleGroup=findViewById(R.id.up_toggleButton);
        updateBtn=findViewById(R.id.up_updatebtn);
        cancelBtn=findViewById(R.id.up_cancel);
        Intent intent=getIntent();
        String key=intent.getStringExtra("key");
        prefillData(intent);

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            myRef.child("MedicineRecord").child(PersonID).child(key).setValue(getData());
                startActivity(
                        new Intent(UpdateActivity.this,DisplayMedicineActivity.class)
                                .putExtra("UserName",account.getDisplayName()).putExtra("Id",account.getId())
                );
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(
                        new Intent(UpdateActivity.this,DisplayMedicineActivity.class)
                        .putExtra("UserName",account.getDisplayName()).putExtra("Id",account.getId())
                );
            }
        });


    }
    public void prefillData(Intent intent){

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
            if(i.contains(TIME.MORNING)){
                materialButtonToggleGroup.check(R.id.up_morning);
            }
            else if(i.contains(TIME.AFTERNOON)){
                materialButtonToggleGroup.check(R.id.up_lunch);
            }
            else if(i.contains(TIME.NIGHT)){
                materialButtonToggleGroup.check(R.id.up_night);
            }
        }

        name.getEditText().setText(nameValue);
        note.getEditText().setText(noteValue);



    }

    public MedicineRecordHandler getData(){

        String nameValue=name.getEditText().getText().toString();
        String noteValue=note.getEditText().getText().toString();
        Boolean beforeFoodValue=false;
        if(radioGroup.getCheckedRadioButtonId()==R.id.up_radio_button_1){
            beforeFoodValue=true;
        }

        List<Integer> arr= materialButtonToggleGroup.getCheckedButtonIds();
        ArrayList<String> time=new ArrayList<>();
        for (Integer i:arr){
            if(i==R.id.up_morning){
                time.add(TIME.MORNING);
            }
            else if(i==R.id.up_lunch){
                time.add(TIME.AFTERNOON);
            }
            else if(i==R.id.up_night){
                time.add(TIME.NIGHT);
            }
        }

        MedicineRecordHandler mrh=new MedicineRecordHandler(
                nameValue,
                noteValue,
                beforeFoodValue,
                time,
                notificationID++
        );

     return mrh;
    }

}