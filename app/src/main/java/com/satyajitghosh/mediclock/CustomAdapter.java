package com.satyajitghosh.mediclock;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * This is a customAdapter to the ListView that shows the record of the Medicines
 *
 * @author SATYAJIT GHOSH
 * @since 1.0.0
 */
public class CustomAdapter extends ArrayAdapter<MedicineRecordHandler> {

    private final ArrayList<MedicineRecordHandler> arrayList;

    //This codes helps to get the reference of the FireBase Database and the instances of it.
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getContext());


    public CustomAdapter(@NonNull Context context, @NonNull ArrayList<MedicineRecordHandler> arrayList) {
        super(context, 0, arrayList);
        this.arrayList = arrayList;
    }

    public void UpdateArrayList(MedicineRecordHandler data) {
        arrayList.add(data);
    }

    public void requestUpdate() {
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View currentItemView = convertView;
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.custom_list_view, parent, false);
        }
        TextView name_view = currentItemView.findViewById(R.id.name_view);
        name_view.setText(arrayList.get(position).getName());

        TextView food_view = currentItemView.findViewById(R.id.food_view);
        if (arrayList.get(position).getBeforeFood()) {
            food_view.setText("Before Food");
        } else {
            food_view.setText("After Food");
        }
        TextView time_view = currentItemView.findViewById(R.id.time_view);
        String output_time = "";
        for (TIME.AlarmBundle i : arrayList.get(position).getReminder()) {
            String j = i.getTime();
            if (j.contains(TIME.MORNING)) {
                output_time += "Morning ";
            } else if (j.contains(TIME.AFTERNOON)) {
                output_time += "Afternoon ";

            } else if (j.contains(TIME.NIGHT)) {
                output_time += "Night";
            } else {
                output_time += "ERROR";
            }
        }
        time_view.setText(output_time);

        TextView note_view = currentItemView.findViewById(R.id.note_view);

        if (arrayList.get(position).getNotes().isEmpty()) {
            note_view.setText("No Notes");
        } else {
            note_view.setText(arrayList.get(position).getNotes());
        }

        //This code is for delete button
        currentItemView.findViewById(R.id.delete_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MedicineRecordHandler mrd = arrayList.get(position);
                arrayList.clear();
                myRef.child("MedicineRecord").child(account.getId()).child(mrd.key).removeValue(); //This removes the child from the FireBase database .
                Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), MyBroadcastReceiver.class);
                AlarmManagerHandler.cancelAlarm(getContext(), intent, mrd);
                notifyDataSetChanged();
            }
        });

        //This code is for update button
        currentItemView.findViewById(R.id.update_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getContext(), UpdateActivity.class);
                MedicineRecordHandler mrd = arrayList.get(position);
                arrayList.clear();

                // This extras helps to pre-fill the Update Activity fields.
                i.putExtra("name", mrd.getName());
                i.putExtra("note", mrd.getNotes());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("beforeFood", mrd.getBeforeFood());
                i.putExtra("key", mrd.key);
                getContext().startActivity(i);
            }
        });

        return currentItemView;
    }
}
