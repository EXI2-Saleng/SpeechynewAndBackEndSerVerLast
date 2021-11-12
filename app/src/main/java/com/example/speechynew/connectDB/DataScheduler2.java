package com.example.speechynew.connectDB;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataScheduler2 {
    String USER_ID;
    String ID;
    String day;
    String date;
    String month;
    String year;
    String starthour;
    String startminute;
    String stophour;
    String stopminute;
    String status;

    @Expose
    @SerializedName("messages")
    private String messages;



    public DataScheduler2(){}

    public DataScheduler2(String USER_ID, String ID, String day, String date, String month, String year, String starthour, String startminute, String stophour, String stopminute, String status) {
        this.USER_ID = USER_ID;
        this.ID = ID;
        this.day = day;
        this.date = date;
        this.month = month;
        this.year = year;
        this.starthour = starthour;
        this.startminute = startminute;
        this.stophour = stophour;
        this.stopminute = stopminute;
        this.status = status;
    }

    public DataScheduler2(String USER_ID, String ID) {
        this.USER_ID = USER_ID;
        this.ID = ID;
    }

    public DataScheduler2(String USER_ID, String ID, String status) {
        this.USER_ID = USER_ID;
        this.ID = ID;
        this.status = status;
    }


    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getStarthour() {
        return starthour;
    }

    public void setStarthour(String starthour) {
        this.starthour = starthour;
    }

    public String getStartminute() {
        return startminute;
    }

    public void setStartminute(String startminute) {
        this.startminute = startminute;
    }

    public String getStophour() {
        return stophour;
    }

    public void setStophour(String stophour) {
        this.stophour = stophour;
    }

    public String getStopminute() {
        return stopminute;
    }

    public void setStopminute(String stopminute) {
        this.stopminute = stopminute;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }



    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

}
