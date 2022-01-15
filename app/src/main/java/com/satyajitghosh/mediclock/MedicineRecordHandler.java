package com.satyajitghosh.mediclock;

import java.util.ArrayList;

public class MedicineRecordHandler {
    String name;
    String notes;
    Boolean beforeFood;
    ArrayList<String> reminder;

    public MedicineRecordHandler(String name, String notes, Boolean beforeFood, ArrayList<String> reminder) {
        this.name = name;
        this.notes = notes;
        this.beforeFood = beforeFood;
        this.reminder = reminder;
    }
    MedicineRecordHandler(){}

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

    public ArrayList<String> getReminder() {
        return reminder;
    }

    public void setReminder(ArrayList<String> reminder) {
        this.reminder = reminder;
    }
}
