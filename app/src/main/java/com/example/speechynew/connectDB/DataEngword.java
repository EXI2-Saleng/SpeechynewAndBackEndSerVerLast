package com.example.speechynew.connectDB;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataEngword {
    @Expose
    @SerializedName("ENGWORD_ID")
    private int ENGWORD_ID;

    @Expose
    @SerializedName("USER_ID")
    private String USER_ID;

    @Expose
    @SerializedName("engword")
    private String engword;

    @Expose
    @SerializedName("word")
    private String word;

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
    @SerializedName("dataword")
    private List<String> dataword;

    @Expose
    @SerializedName("datahour")
    private List<String>datahour;

    @Expose
    @SerializedName("datadate")
    private List<String>datadate;

    @Expose
    @SerializedName("device")
    private String device;

    public DataEngword(){}

    public int getENGWORD_ID() {
        return ENGWORD_ID;
    }

    public void setENGWORD_ID(int ENGWORD_ID) {
        this.ENGWORD_ID = ENGWORD_ID;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public String getEngword() {
        return engword;
    }

    public void setEngword(String engword) {
        this.engword = engword;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
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

    public List<String> getDataword() {
        return dataword;
    }

    public void setDataword(List<String> dataword) {
        this.dataword = dataword;
    }

    public List<String> getDatahour() {
        return datahour;
    }

    public void setDatahour(List<String> datahour) {
        this.datahour = datahour;
    }

    public List<String> getDatadate() {
        return datadate;
    }

    public void setDatadate(List<String> datadate) {
        this.datadate = datadate;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }
}
