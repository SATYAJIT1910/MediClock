package com.satyajitghosh.mediclock;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class DisplayMedicineActivity extends AppCompatActivity {
    private ListView listview;
    private DatabaseReference mDatabase;
    private ArrayList<MedicineRecordHandler> arrayList;
    protected GoogleSignInAccount account;
    private CustomAdapter c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_medicine);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        listview = findViewById(R.id.listview);
        account = GoogleSignIn.getLastSignedInAccount(this);
        arrayList = new ArrayList<>();
        TextView account_user_name_view = findViewById(R.id.account_user_name_view);
        account_user_name_view.setText("Hi, " + account.getDisplayName());

        c = new CustomAdapter(getApplicationContext(), arrayList);
        listview.setAdapter(c);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    findViewById(R.id.empty).setVisibility(View.INVISIBLE);
                    findViewById(R.id.emptyText).setVisibility(View.INVISIBLE);
//                    long n = snapshot.child("MedicineRecord").child(account.getId()).getChildrenCount();
                      refreshData(snapshot);
                }else{
                    findViewById(R.id.empty).setVisibility(View.VISIBLE);
                    findViewById(R.id.emptyText).setVisibility(View.VISIBLE);
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
                startActivity(new Intent(DisplayMedicineActivity.this, HomeActivity.class).putExtra("UserName", account.getDisplayName()).putExtra("Id", account.getId())
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK)
                );
            }
        });
    }

    public void refreshData(DataSnapshot snapshot) {
        Iterator<DataSnapshot> items = snapshot.child("MedicineRecord").child(account.getId()).getChildren().iterator();
        while (items.hasNext()) {
            DataSnapshot item = items.next();

            MedicineRecordHandler mrd = item.getValue(MedicineRecordHandler.class);
            Log.d("DisplayProblem", mrd.toString());

            mrd.key = item.getKey();
            // arrayList.add(mrd);
            c.UpdateArrayList(mrd);
        }
        c.requestUpdate();

    }

}

