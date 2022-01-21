package com.satyajitghosh.mediclock;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

/**
 * This class is used to handle all the things related to setting up/ cancelling the alarms for Notifications
 *
 * @author SATYAJIT GHOSH
 * @since 1.0.0
 */
public class AlarmManagerHandler extends AppCompatActivity {


    public static final String CHANNEL_ID = "10";

    /**
     * This method is used to add an alarm .
     *
     * @param context        it provides the context
     * @param hour           it provides the hour to set the alarm
     * @param minute         it provides the minute to set the alarm
     * @param medicineName   it provides the medicineName to set up the alert , this further passed to the BroadCastReceiver to show the detail on Notifications.
     * @param notificationId it provides the notificationID
     * @param Food           it tells whether the medicine is to consume after food/before food.
     */
    public static void addAlert(Context context, int hour, int minute, String medicineName, int notificationId, String Food) {






        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, 00);
        long time = cal.getTimeInMillis();


        Intent intent = new Intent(context, MyBroadcastReceiver.class)
                .putExtra("MedicineName", medicineName)
                .putExtra("Food", Food);


        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notificationId, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
       try{

        // alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time, AlarmManager.INTERVAL_DAY, pendingIntent);
         alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time, 70000, pendingIntent); //TODO: REMOVE THE COMMENT FOR TESTING
       }catch (Exception e){
           Log.d("AlarmManagerError",e.toString());
           Toast.makeText(context.getApplicationContext(), "We cannot setup reminder on your Device", Toast.LENGTH_LONG).show();
       }
    }

    /**
     * This method is used to Create the notification Channel.
     *
     * @param context it provides the context
     */

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

    /**
     * This method is used to cancel the alarms.
     *
     * @param context it provides the context
     * @param intent  it provides the intent to cancel the alarm
     * @param mrh     it is the object of MedicineRecordHandler class. It is used here to fetch the notificationId of a alarm to cancel that particular alarm.
     */
    public static void cancelAlarm(Context context, Intent intent, MedicineRecordHandler mrh) {

        for (TIME.AlarmBundle i : mrh.getReminder()) {
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, i.getNotificationID(), intent, 0);
            alarmManager.cancel(pendingIntent);
            pendingIntent.cancel();
        }

    }

    /**
     * This method is used to initiate the alarms. It traverse for all the time selected for a single Record , then based on it sets all the alarm for a Record.
     *
     * @param mrh     it is the Object of a MedicineRecordHandler class . It holds all the information regarding a single Entry .
     * @param context it provides the context .
     */
    public static void initAlarm(MedicineRecordHandler mrh, Context context) {

        for (TIME.AlarmBundle i : mrh.getReminder()) {
            int hour = Integer.parseInt(i.getTime().substring(0, 2));
            int minutes = Integer.parseInt(i.getTime().substring(2, 4));
            String Food;

            if (mrh.getBeforeFood()) {
                Food = "before food";
            } else {
                Food = "after food";
            }

           // AlarmManagerHandler.addAlert(context, hour, minutes, mrh.getName(), i.getNotificationID(), Food);
            AlarmManagerHandler.addAlert(context, 10, 12, mrh.getName(), i.getNotificationID(), Food); //TODO: REMOVE THE COMMENT FOR TESTING
        }
    }

    /**
     * This method is used to generate unique set of RequestCode for the PendingIntents of AlarmManager.
     * This method is also used along with medicineName to construct the child name in FireBase DataBase & to give unique id to notifications.
     *
     * @return a random generated number
     */
    public static int setUniqueNotificationId() {
        int result = ((int) ((Math.random() * (99999 - 11111)) + 11111)) + ((int) ((Math.random() * (9999 - 1111)) + 1111));
        return result;
    }


}
