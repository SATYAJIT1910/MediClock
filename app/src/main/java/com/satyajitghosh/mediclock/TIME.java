package com.satyajitghosh.mediclock;

import static android.content.Context.MODE_PRIVATE;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This class is used for storing the information of times , corresponding to Morning,Afternoon,Night declarations.
 * The inner class AlarmBundle is used to store the time and its notificationId in a bundle
 * @author SATYAJIT GHOSH
 * @since 1.0.0
 */




public class TIME {
   public static String MORNING="0800";
   public static String AFTERNOON="1400";
   public static String NIGHT="2030";


   public static class AlarmBundle {
      String time;
      int notificationID;
      AlarmBundle(){}

      AlarmBundle(String time, int notificationID){
         this.time=time;
         this.notificationID=notificationID;
      }

      public String getTime() {
         return time;
      }

      public void setTime(String time) {
         this.time = time;
      }

      public int getNotificationID() {
         return notificationID;
      }

      public void setNotificationID(int notificationID) {
         this.notificationID = notificationID;
      }


   }


}
