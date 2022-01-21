package com.satyajitghosh.mediclock;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MyAlarmService extends Service {
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String MedicineName=intent.getStringExtra("MedicineName");
        String Food=intent.getStringExtra("Food");
        AlarmManagerHandler.createNotificationChannel(this);
        showNotification(this , MedicineName, Food);
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


    public void showNotification(Context context, String MedicineName, String Food){

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, AlarmManagerHandler.CHANNEL_ID)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("MediClock Reminder")
                .setContentText("Hey, Take your medicine "+MedicineName+" "+Food+".")
                .setStyle(new NotificationCompat.BigTextStyle())
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        // notificationId is a unique id for each notification
        notificationManager.notify(AlarmManagerHandler.setUniqueNotificationId(), builder.build());

    }
}
