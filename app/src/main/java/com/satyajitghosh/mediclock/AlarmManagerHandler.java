package com.satyajitghosh.mediclock;

import static android.content.Context.ALARM_SERVICE;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AlarmManagerHandler extends AppCompatActivity {

    private static final String CHANNEL_ID = "10";

    public void startAlert(Context context, int hour, int minute,String medicineName){

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY,hour);
        cal.set(Calendar.MINUTE,minute);
        cal.set(Calendar.SECOND,00);
        long time=cal.getTimeInMillis();


        Intent intent = new Intent(context, MyBroadcastReceiver.class)
                .putExtra("MedicineName",medicineName);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context.getApplicationContext(), 9999, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time ,AlarmManager.INTERVAL_DAY,pendingIntent);
        Toast.makeText(context, "Alarm added",Toast.LENGTH_LONG).show();
    }
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Medicine Reminder";
            String description = "This is to send notification about Medicine times";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


}
