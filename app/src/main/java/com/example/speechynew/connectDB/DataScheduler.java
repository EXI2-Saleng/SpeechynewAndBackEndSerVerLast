package com.example.speechynew.connectDB;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataScheduler {
    @Expose
    @SerializedName("SCHEDULER_ID")
    private int SCHEDULER_ID;

    @Expose
    @SerializedName("USER_ID")
    private String USER_ID;

    @Expose
    @SerializedName("ID")
    private String ID;

    @Expose
    @SerializedName("scheduler")
    private String scheduler;


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
    @SerializedName("starthour")
    private String starthour;

    @Expose
    @SerializedName("startminute")
    private String startminute;

    @Expose
    @SerializedName("stophour")
    private String stophour;

    @Expose
    @SerializedName("stopminute")
    private String stopminute;

    @Expose
    @SerializedName("status")
    private String status;


    @Expose
    @SerializedName("success")
    private boolean success;

    @Expose
    @SerializedName("messages")
    private String messages;

    public DataScheduler(){}

    public int getSCHEDULER_ID() {
        return SCHEDULER_ID;
    }

    public void setSCHEDULER_ID(int SCHEDULER_ID) {
        this.SCHEDULER_ID = SCHEDULER_ID;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public String getScheduler() {
        return scheduler;
    }

    public void setScheduler(String scheduler) {
        this.scheduler = scheduler;
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

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
