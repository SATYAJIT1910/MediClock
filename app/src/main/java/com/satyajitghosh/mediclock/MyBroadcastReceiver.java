package com.satyajitghosh.mediclock;

import static android.content.Context.POWER_SERVICE;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.legacy.content.WakefulBroadcastReceiver;

public class MyBroadcastReceiver extends BroadcastReceiver{
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {

            String MedicineName=intent.getStringExtra("MedicineName");
            String Food=intent.getStringExtra("Food");

            try{

            context.startService(new Intent(context,MyAlarmService.class)
                        .putExtra("MedicineName",MedicineName)
                        .putExtra("Food",Food)
                        .addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES)

            );
            }catch (Exception e){
                Log.d("ServiceStartError",e.getMessage());
                context.startForegroundService(new Intent(context,MyAlarmService.class)
                        .putExtra("MedicineName",MedicineName)
                        .putExtra("Food",Food)
                        .addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES)

                );

            }

    }


}
