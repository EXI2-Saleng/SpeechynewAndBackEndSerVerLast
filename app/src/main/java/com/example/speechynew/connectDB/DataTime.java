package com.example.speechynew.connectDB;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataTime {
    @Expose
    @SerializedName("TIME_ID")
    private int TIME_ID;

    @Expose
    @SerializedName("USER_ID")
    private String USER_ID;

    @Expose
    @SerializedName("time")
    private String time;

    @Expose
    @SerializedName("day")
    private String day;

    @Expose
    @SerializedName("date")
    private String date;

    @Expose
    @SerializedName("month")
    private String month;

    @Expose
    @SerializedName("year")
    private String year;

    @Expose
    @SerializedName("hour")
    private String hour;

    @Expose
    @SerializedName("minute")
    private String minute;

    @Expose
    @SerializedName("second")
    private String second;


    @Expose
    @SerializedName("totaltime")
    private String totaltime;

    @Expose
    @SerializedName("success")
    private boolean success;

    @Expose
    @SerializedName("messages")
    private String messages;

    @Expose
    @SerializedName("sumtime")
    private int sumtime ;

    @Expose
    @SerializedName("sumtimeMonth")
    private int sumtimeMonth;

    @Expose
    @SerializedName("device")
    private String device;

    public DataTime(){}

    public int getTIME_ID() {
        return TIME_ID;
    }

    public void setTIME_ID(int TIME_ID) {
        this.TIME_ID = TIME_ID;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public String getTotaltime() {
        return totaltime;
    }

    public void setTotaltime(String totaltime) {
        this.totaltime = totaltime;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public int getSumtime() {
        return sumtime;
    }

    public void setSumtime(int sumtime) {
        this.sumtime = sumtime;
    }

    public int getSumtimeMonth() {
        return sumtimeMonth;
    }

    public void setSumtimeMonth(int sumtimeMonth) {
        this.sumtimeMonth = sumtimeMonth;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }
}
