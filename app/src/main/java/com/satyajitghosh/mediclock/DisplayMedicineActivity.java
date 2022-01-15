package com.satyajitghosh.mediclock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DisplayMedicineActivity extends AppCompatActivity {
    private ListView listview;
    private DatabaseReference mDatabase;
    private ArrayList<MedicineRecordHandler> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_medicine);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        listview=findViewById(R.id.listview);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        arrayList=new ArrayList<>();
        TextView account_user_name_view=findViewById(R.id.account_user_name_view);
        account_user_name_view.setText("Hi, "+account.getDisplayName());
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    long n = snapshot.child("MedicineRecord").child(account.getId()).getChildrenCount();

                    for (int i = 1; i <= n; i++) {
                        MedicineRecordHandler mrd = snapshot.child("MedicineRecord").child(account.getId()).child(Long.toString(i)).getValue(MedicineRecordHandler.class);
                        arrayList.add(mrd);
                        Log.d("FireData",mrd.getName());
                    }
                      //  Toast.makeText(getApplicationContext(), "Data fetched", Toast.LENGTH_SHORT).show();
                    CustomAdapter c=new CustomAdapter(getApplicationContext(),arrayList);
                    listview.setAdapter(c);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Check you internet connection", Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.add_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DisplayMedicineActivity.this,HomeActivity.class).putExtra("UserName",account.getDisplayName()).putExtra("Id",account.getId()));
            }
        });

    }
}