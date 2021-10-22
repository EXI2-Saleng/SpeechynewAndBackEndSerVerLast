package com.example.speechynew.connectDB;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataContinuemax {
    @Expose
    @SerializedName("CONTINUEMAX_ID")
    private int CONTINUEMAX_ID;

    @Expose
    @SerializedName("USER_ID")
    private String USER_ID;

    @Expose
    @SerializedName("continuemax")
    private String continuemax;

    @Expose
    @SerializedName("maxcon")
    private String maxcon;

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

    @Expose
    @SerializedName("ConMax")
    private int ConMax;

    @Expose
    @SerializedName("ConMaxMonth")
    private int ConMaxMonth;

    @Expose
    @SerializedName("device")
    private String device;

    public DataContinuemax(){}

    public int getCONTINUEMAX_ID() {
        return CONTINUEMAX_ID;
    }

    public void setCONTINUEMAX_ID(int CONTINUEMAX_ID) {
        this.CONTINUEMAX_ID = CONTINUEMAX_ID;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public String getContinuemax() {
        return continuemax;
    }

    public void setContinuemax(String continuemax) {
        this.continuemax = continuemax;
    }

    public String getMaxcon() {
        return maxcon;
    }

    public void setMaxcon(String maxcon) {
        this.maxcon = maxcon;
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

    public int getConMax() {
        return ConMax;
    }

    public void setConMax(int conMax) {
        ConMax = conMax;
    }

    public int getConMaxMonth() {
        return ConMaxMonth;
    }

    public void setConMaxMonth(int conMaxMonth) {
        ConMaxMonth = conMaxMonth;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }
}
