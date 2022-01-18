package com.satyajitghosh.mediclock;

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
