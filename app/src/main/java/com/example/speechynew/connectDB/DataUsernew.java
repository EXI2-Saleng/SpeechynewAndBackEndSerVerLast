package com.example.speechynew.connectDB;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataUsernew {

    String USER_ID;
    String device;
    String email;
    String name;

    @Expose
    @SerializedName("success")
    private boolean success;

    @Expose
    @SerializedName("messages")
    private String messages;

    @Expose
    @SerializedName("User")
    private String User;

    @Expose
    @SerializedName("Device")
    private String Device;

    public  DataUsernew(){}

    public DataUsernew(String USER_ID, String device) {
        this.USER_ID = USER_ID;
        this.device = device;
    }

    public DataUsernew(String USER_ID, String device, String email, String name) {
        this.USER_ID = USER_ID;
        this.device = device;
        this.email = email;
        this.name = name;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public String getDevice() {
        return Device;
    }

    public void setDevice(String device) {
        this.Device = Device;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public String getDevice1() {
        return Device;
    }

    public void setDevice1(String Device) {
        this.Device = Device;
    }
}
