package com.example.speechynew.connectDB;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataContinuemax2 {
    String USER_ID;
    String device;
    String maxcon;
    String date;
    String month;
    String year;
    String hour;
    String minute;
    String second;
    @Expose
    @SerializedName("messages")
    private String messages;

    public  DataContinuemax2(){}

    public DataContinuemax2(String USER_ID, String device, String maxcon, String date, String month, String year, String hour, String minute, String second) {
        this.USER_ID = USER_ID;
        this.device = device;
        this.maxcon = maxcon;
        this.date = date;
        this.month = month;
        this.year = year;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getMaxcon() {
        return maxcon;
    }

    public void setMaxcon(String maxcon) {
        this.maxcon = maxcon;
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

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }
}
