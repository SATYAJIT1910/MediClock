package com.satyajitghosh.mediclock;

import static android.content.Context.ALARM_SERVICE;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AlarmManagerHandler extends AppCompatActivity {

    public static final String CHANNEL_ID = "10";


    public static void addAlert(Context context, int hour, int minute,String medicineName,int notificationId,String Food){

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY,hour);
        cal.set(Calendar.MINUTE,minute);
        cal.set(Calendar.SECOND,00);
        long time=cal.getTimeInMillis();


        Intent intent = new Intent(context, MyBroadcastReceiver.class)
                .putExtra("MedicineName",medicineName)
                .putExtra("Food",Food);


        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notificationId, intent,0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
      //  alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time ,AlarmManager.INTERVAL_DAY,pendingIntent);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time ,70000,pendingIntent); //For testing
      //  Toast.makeText(context, "Alarm added",Toast.LENGTH_LONG).show(); //For testing
    }
    public static void createNotificationChannel(Context context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Medicine Reminder";
            String description = "This is to send notification about Medicine reminders";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public static void cancelAlarm(Context context, Intent intent, MedicineRecordHandler mrh) {

        for(TIME.AlarmBundle i:mrh.getReminder()){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, i.getNotificationID(), intent,0);
        alarmManager.cancel(pendingIntent);
        pendingIntent.cancel();
        }

    }

    public static void initAlarm(MedicineRecordHandler mrh, Context context) {

        for (TIME.AlarmBundle i : mrh.getReminder()) {
            int hour = Integer.parseInt(i.getTime().substring(0, 2));
            int minutes = Integer.parseInt(i.getTime().substring(2, 4));
            String Food;

            if(mrh.getBeforeFood()){
                Food="before food";
            }else{
                Food="after food";
            }

           // AlarmManagerHandler.addAlert(context, hour, minutes, mrh.getName(), i.getNotificationID(),Food);
            AlarmManagerHandler.addAlert(context, 10, 12, mrh.getName(), i.getNotificationID(),Food); //for testing
        }
    }
    public static int setUniqueNotificationId(){
        /*
        * This function is to generate unique set of NotificationID
        * */
        int result=((int) ((Math.random() * (99999 - 11111)) + 11111))+((int) ((Math.random() * (9999 - 1111)) + 1111));
        return result;
    }


}
