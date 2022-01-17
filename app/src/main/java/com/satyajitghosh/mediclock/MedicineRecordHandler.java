package com.satyajitghosh.mediclock;

import java.util.ArrayList;

public class MedicineRecordHandler {
    public String key;
    String name;
    String notes;
    Boolean beforeFood;
    ArrayList<String> reminder;
    int notificationID;

    public MedicineRecordHandler(String name, String notes, Boolean beforeFood, ArrayList<String> reminder,int notificationID) {
        this.name = name;
        this.notes = notes;
        this.beforeFood = beforeFood;
        this.reminder = reminder;
        this.notificationID=notificationID;
    }
    public MedicineRecordHandler(){}

    public String getName() {
        return name;
    }

    public int getNotificationID() {
        return notificationID;
    }

    public void setNotificationID(int notificationID) {
        this.notificationID = notificationID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Boolean getBeforeFood() {
        return beforeFood;
    }

    public void setBeforeFood(Boolean beforeFood) {
        this.beforeFood = beforeFood;
    }

    public ArrayList<String> getReminder() {
        return reminder;
    }

    public void setReminder(ArrayList<String> reminder) {
        this.reminder = reminder;
    }

    @Override
    public String toString() {
        return "MedicineRecordHandler{" +
                "name='" + name + '\'' +
                ", notes='" + notes + '\'' +
                ", beforeFood=" + beforeFood +
                ", reminder=" + reminder +
                '}';
    }
}
