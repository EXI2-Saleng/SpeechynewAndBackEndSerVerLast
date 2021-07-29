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
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("devicename")
    private String devicename;


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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDevicename() {
        return devicename;
    }

    public void setDevicename(String devicename) {
        this.devicename = devicename;
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
