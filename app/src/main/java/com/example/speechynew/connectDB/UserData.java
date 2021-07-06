package com.example.speechynew.connectDB;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import retrofit2.http.Path;
import retrofit2.http.Query;

public class UserData {

    @Expose
    @SerializedName("USER_ID")
    private String USER_ID;

    @Expose
    @SerializedName("email")
    private String email;

    @Expose
    @SerializedName("totalwordday1")
    private String totalwordday1;

    @Expose
    @SerializedName("totaltimeday1")
    private String totaltimeday1;

    @Expose
    @SerializedName("wordminday1")
    private String wordminday1;

    @Expose
    @SerializedName("continuemaxday1")
    private String continuemaxday1;

    @Expose
    @SerializedName("wordtop1")
    private String wordtop1;

    @Expose
    @SerializedName("wordtop2")
    private String wordtop2;

    @Expose
    @SerializedName("wordtop3")
    private String wordtop3;

    @Expose
    @SerializedName("Day1")
    private String Day1;

    @Expose
    @SerializedName("Month1")
    private String Month1;

    @Expose
    @SerializedName("Year1")
    private String Year1;

    @Expose
    @SerializedName("success")
    private boolean success;

    @Expose
    @SerializedName("messages")
    private String messages;


    public UserData(){}
    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTotalwordday1() {
        return totalwordday1;
    }

    public void setTotalwordday1(String totalwordday1) {
        this.totalwordday1 = totalwordday1;
    }

    public String getTotaltimeday1() {
        return totaltimeday1;
    }

    public void setTotaltimeday1(String totaltimeday1) {
        this.totaltimeday1 = totaltimeday1;
    }

    public String getWordminday1() {
        return wordminday1;
    }

    public void setWordminday1(String wordminday1) {
        this.wordminday1 = wordminday1;
    }

    public String getContinuemaxday1() {
        return continuemaxday1;
    }

    public void setContinuemaxday1(String continuemaxday1) {
        this.continuemaxday1 = continuemaxday1;
    }

    public String getWordtop1() {
        return wordtop1;
    }

    public void setWordtop1(String wordtop1) {
        this.wordtop1 = wordtop1;
    }

    public String getWordtop2() {
        return wordtop2;
    }

    public void setWordtop2(String wordtop2) {
        this.wordtop2 = wordtop2;
    }

    public String getWordtop3() {
        return wordtop3;
    }

    public void setWordtop3(String wordtop3) {
        this.wordtop3 = wordtop3;
    }

    public String getDay1() {
        return Day1;
    }

    public void setDay1(String day1) {
        Day1 = day1;
    }

    public String getMonth1() {
        return Month1;
    }

    public void setMonth1(String month1) {
        Month1 = month1;
    }

    public String getYear1() {
        return Year1;
    }

    public void setYear1(String year1) {
        Year1 = year1;
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
