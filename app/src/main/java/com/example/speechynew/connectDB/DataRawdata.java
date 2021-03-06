package com.example.speechynew.connectDB;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataRawdata {

    @Expose
    @SerializedName("RAWDATA_ID")
    private int RAWDATA_ID;

    @Expose
    @SerializedName("USER_ID")
    private String USER_ID;

    @Expose
    @SerializedName("rawdata")
    private String rawdata;

    @Expose
    @SerializedName("totalword")
    private String totalword;

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
    @SerializedName("success")
    private boolean success;

    @Expose
    @SerializedName("messages")
    private String messages;

    public DataRawdata(){}

    public int getRAWDATA_ID() {
        return RAWDATA_ID;
    }

    public void setRAWDATA_ID(int RAWDATA_ID) {
        this.RAWDATA_ID = RAWDATA_ID;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public String getRawdata() {
        return rawdata;
    }

    public void setRawdata(String rawdata) {
        this.rawdata = rawdata;
    }

    public String getTotalword() {
        return totalword;
    }

    public void setTotalword(String totalword) {
        this.totalword = totalword;
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
}
