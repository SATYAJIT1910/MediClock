package com.satyajitghosh.mediclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MyBroadcastReceiver extends BroadcastReceiver {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {

            String MedicineName=intent.getStringExtra("MedicineName");
            String Food=intent.getStringExtra("Food");

            context.startService(new Intent(context,MyAlarmService.class)
                        .putExtra("MedicineName",MedicineName)
                        .putExtra("Food",Food)
            );



    }


}
