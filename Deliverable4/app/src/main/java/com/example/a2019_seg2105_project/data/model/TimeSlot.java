package com.example.a2019_seg2105_project.data.model;

import java.util.ArrayList;

public class TimeSlot {

    public final static ArrayList<String> getTimeSlots(){
        ArrayList<String> workingHours = new ArrayList<>();
        workingHours.add("08:00-09:00");
        workingHours.add("09:00-10:00");
        workingHours.add("10:00-11:00");
        workingHours.add("11:00-12:00");
        workingHours.add("12:00-13:00");
        workingHours.add("13:00-14:00");
        workingHours.add("14:00-15:00");
        workingHours.add("15:00-16:00");
        workingHours.add("16:00-17:00");
        return workingHours;
    }
}
