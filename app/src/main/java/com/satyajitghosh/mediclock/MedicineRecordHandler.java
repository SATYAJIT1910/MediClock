package com.satyajitghosh.mediclock;

import java.util.ArrayList;




public class MedicineRecordHandler {
    public String key;
    String name;
    String notes;
    Boolean beforeFood;
    ArrayList<TIME.AlarmBundle> reminder;

    public MedicineRecordHandler(String name, String notes, Boolean beforeFood, ArrayList<TIME.AlarmBundle> reminder) {
        this.name = name;
        this.notes = notes;
        this.beforeFood = beforeFood;
       this.reminder = reminder;

    }
    public MedicineRecordHandler(){}

    public String getName() {
        return name;
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

    public ArrayList<TIME.AlarmBundle> getReminder() {
        return reminder;
    }

    public void setReminder(ArrayList<TIME.AlarmBundle> reminder) {
        this.reminder = reminder;
    }

    @Override
    public String toString() {
        return "MedicineRecordHandler{" +
                "key='" + key + '\'' +
                ", name='" + name + '\'' +
                ", notes='" + notes + '\'' +
                ", beforeFood=" + beforeFood +
                ", reminder=" + reminder +
                '}';
    }
}
