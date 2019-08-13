package com.example.abcinterviewapp;

class WorkingHours {
    private String workingHours;
    private String startHours;
    private String startMinutes;
    private String endHours;

    public String getStartHours() {
        return startHours;
    }

    public void setStartHours(String startHours) {
        this.startHours = startHours;
    }

    public String getStartMinutes() {
        return startMinutes;
    }

    public void setStartMinutes(String startMinutes) {
        this.startMinutes = startMinutes;
    }

    public String getEndHours() {
        return endHours;
    }

    public void setEndHours(String endHours) {
        this.endHours = endHours;
    }

    public String getEndMinutes() {
        return endMinutes;
    }

    public void setEndMinutes(String endMinutes) {
        this.endMinutes = endMinutes;
    }

    private String endMinutes;
    private int day;


    public WorkingHours(String workingHours, int day) {
        this.workingHours = workingHours;
        this.day = day;
    }

    public String getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(String workingHours) {
        this.workingHours = workingHours;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
