package com.satyajitghosh.mediclock;

import java.util.ArrayList;

public class MedicineRecordHandler {
    String name;
    String notes;
    Boolean beforeAfterFood; //0 for before food and 1 for after food
    ArrayList<String> reminder;

    public MedicineRecordHandler(String name, String notes, Boolean beforeAfterFood, ArrayList<String> reminder) {
        this.name = name;
        this.notes = notes;
        this.beforeAfterFood = beforeAfterFood;
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

    public Boolean getBeforeAfterFood() {
        return beforeAfterFood;
    }

    public void setBeforeAfterFood(Boolean beforeAfterFood) {
        this.beforeAfterFood = beforeAfterFood;
    }

    public ArrayList<String> getReminder() {
        return reminder;
    }

    public void setReminder(ArrayList<String> reminder) {
        this.reminder = reminder;
    }
}
