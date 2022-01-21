package com.satyajitghosh.mediclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

            String MedicineName=intent.getStringExtra("MedicineName");
            String Food=intent.getStringExtra("Food");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(new Intent(context,MyAlarmService.class)
                        .putExtra("MedicineName",MedicineName)
                        .putExtra("Food",Food)
            );
        }


    }


}
