package com.satyajitghosh.mediclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MyBroadcastReceiver extends BroadcastReceiver {
    private static int notificationId=1;

    @Override
    public void onReceive(Context context, Intent intent) {
            String MedicineName=intent.getStringExtra("MedicineName");
            String Food=intent.getStringExtra("Food");
            showNotification(context,MedicineName,Food);
      //  Toast.makeText(context, MedicineName, Toast.LENGTH_SHORT).show(); //for testing
    }
    public void showNotification(Context context,String MedicineName,String Food){

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, AlarmManagerHandler.CHANNEL_ID)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("MediClock Reminder")
                .setContentText("Hey, Take your medicine "+MedicineName+" "+Food+".")
                .setStyle(new NotificationCompat.BigTextStyle())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(notificationId++, builder.build());


    }





}
