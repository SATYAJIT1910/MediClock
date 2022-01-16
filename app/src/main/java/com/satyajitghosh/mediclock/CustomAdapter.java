package com.satyajitghosh.mediclock;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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

import org.w3c.dom.Text;

import java.util.ArrayList;

    public class CustomAdapter extends ArrayAdapter<MedicineRecordHandler> {
        private ArrayList<MedicineRecordHandler> arrayList;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getContext());
        public CustomAdapter(@NonNull Context context, @NonNull ArrayList<MedicineRecordHandler> arrayList) {
            super(context, 0, arrayList);
            this.arrayList = arrayList;
        }
        public void UpdateArrayList(MedicineRecordHandler data){
            arrayList.add(data);

        }
        public void requestUpdate(){
            notifyDataSetChanged();
        }

        public boolean empty(){
            return arrayList.isEmpty();
        }


        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View currentItemView = convertView;
            if (currentItemView == null) {
                currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.custom_list_view, parent, false);
            }
            TextView name_view=currentItemView.findViewById(R.id.name_view);
            name_view.setText(arrayList.get(position).getName());

            for(MedicineRecordHandler i:arrayList){
                Log.d("FireData",i.toString());
            }


            TextView food_view=currentItemView.findViewById(R.id.food_view);
            if(arrayList.get(position).getBeforeFood()){
                food_view.setText("Before Food");
            }
            else{
                food_view.setText("After Food");
            }
            TextView time_view=currentItemView.findViewById(R.id.time_view);
            String output_time="";
            for(String i:arrayList.get(position).getReminder()){
                if(i.contains("0800")){
                    output_time+="Morning ";
                }
                else if(i.contains("1300")){
                    output_time+="Afternoon ";

                }
                else if(i.contains("2030")){
                    output_time+="Night";
                }
                else{
                    output_time+="error";
                }
            }
            time_view.setText(output_time);

            TextView note_view=currentItemView.findViewById(R.id.note_view);

            if(arrayList.get(position).getNotes().isEmpty()){
                note_view.setText("No Notes");
            }
            else{
            note_view.setText(arrayList.get(position).getNotes());
            }


            currentItemView.findViewById(R.id.delete_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MedicineRecordHandler mrd=arrayList.get(position);
                    arrayList.clear();
                    myRef.child("MedicineRecord").child(account.getId()).child(mrd.key).removeValue();
                    Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                }
            });
            currentItemView.findViewById(R.id.update_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getContext(), UpdateActivity.class);
                    MedicineRecordHandler mrd=arrayList.get(position);
                    i.putExtra("name",mrd.getName());
                    i.putExtra("note",mrd.getNotes());
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //add this line
//                    i.putExtra("beforeFood",mrd.getBeforeFood());
//                    i.putExtra("time",mrd.getReminder());
//                    i.putExtra
                    getContext().startActivity(i);
                }
            });

            return currentItemView;
        }
    }
