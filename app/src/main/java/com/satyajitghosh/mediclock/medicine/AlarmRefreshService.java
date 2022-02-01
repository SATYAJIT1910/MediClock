package com.satyajitghosh.mediclock.medicine;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

/**
 * This service starts at the program launch from the MainActivity and it refreshes all the alarms after fetching the details from the fireBase Database.
 * It is also used for adding and updating data to the firebase.
 * AlarmManager class doesn't store the alarms on the device restart , so it is also necessary.
 *
 * @author SATYAJIT GHOSH
 * @since 1.5.0
 */
public class AlarmRefreshService extends Service {
    private DatabaseReference mDatabase;
    protected GoogleSignInAccount account;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("MedicineRecord").child(Objects.requireNonNull(account.getId()));
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                refreshData(snapshot);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                refreshData(snapshot);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void refreshData(DataSnapshot snapshot) {

        MedicineRecordHandler mrd = snapshot.getValue(MedicineRecordHandler.class);
        AlarmManagerHandler.initAlarm(mrd, getApplicationContext());
    }


}
