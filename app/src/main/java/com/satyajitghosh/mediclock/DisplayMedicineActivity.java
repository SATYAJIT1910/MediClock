package com.satyajitghosh.mediclock;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

/**
 * This activity is used to fetch the data from the FireBase DataBase and to populate it to the arraylist of CustomAdapter.
 *
 * @author SATYAJIT GHOSH
 * @since 1.0.0
 */
public class DisplayMedicineActivity extends AppCompatActivity {
    private ListView listview;
    private DatabaseReference mDatabase;
    private ArrayList<MedicineRecordHandler> arrayList;
    protected GoogleSignInAccount account;
    private CustomAdapter c;
    private BottomAppBar bottomAppBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_medicine);

        account = GoogleSignIn.getLastSignedInAccount(this);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("MedicineRecord").child(Objects.requireNonNull(account.getId())); //Takes the relative path of the user to get the instance of only that user not others.
        listview = findViewById(R.id.listview);
        bottomAppBar = findViewById(R.id.bottomAppBar);

        arrayList = new ArrayList<>();
        TextView account_user_name_view = findViewById(R.id.account_user_name_view);
        account_user_name_view.setText("Hi, " + account.getDisplayName()); //This is used to show the name of user on screen
        account_user_name_view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade));

        c = new CustomAdapter(getApplicationContext(), arrayList);
        listview.setAdapter(c);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    findViewById(R.id.empty).setVisibility(View.INVISIBLE);
                    findViewById(R.id.emptyText).setVisibility(View.INVISIBLE);
                    refreshData(snapshot);
                } else {
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
        bottomAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_settings:
                        startActivity(new Intent(getApplicationContext(), TimeChangeActivity.class));
                        break;
                    case R.id.nav_doctor:
                        break;
                    case R.id.nav_lab:
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });
    }



    /**
     * This method is used to update the arraylist on CustomAdapter and refreshes the listview
     *
     * @param snapshot it gives the snapshot of the database
     */
    public void refreshData(DataSnapshot snapshot) {
        Iterator<DataSnapshot> items = snapshot.getChildren().iterator();
        while (items.hasNext()) {
            DataSnapshot item = items.next();

            MedicineRecordHandler mrd = item.getValue(MedicineRecordHandler.class);

            mrd.key = item.getKey();
            c.UpdateArrayList(mrd);
        }
        c.requestUpdate();

    }

}

