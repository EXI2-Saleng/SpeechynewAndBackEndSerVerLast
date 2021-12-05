package com.example.speechynew.connectDB;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataUsernew {
    int Device_ID;
    String USER_ID;
    String device;
    String email;
    String name;


    String Edevicename;

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

    @Expose
    @SerializedName("devicename")
    private String devicename;

    @Expose
    @SerializedName("ID")
    private int ID;

    @Expose
    @SerializedName("datadevice")
    private List<String> datadevice;

    @Expose
    @SerializedName("datanamedevice")
    private List<String> datanamedevice;

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

    public DataUsernew(int device_ID, String USER_ID, String edevicename) {
        this.Device_ID = device_ID;
        this.USER_ID = USER_ID;
        this.Edevicename = edevicename;
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

    public String getDevicename() {
        return devicename;
    }

    public void setDevicename(String devicename) {
        this.devicename = devicename;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getDevice_ID() {
        return Device_ID;
    }

    public void setDevice_ID(int device_ID) {
        Device_ID = device_ID;
    }

    public String getEdevicename() {
        return Edevicename;
    }

    public void setEdevicename(String Edevicename) {
        Edevicename = Edevicename;
    }

    public List<String> getDatadevice() {
        return datadevice;
    }

    public void setDatadevice(List<String> datadevice) {
        this.datadevice = datadevice;
    }

    public List<String> getDatanamedevice() {
        return datanamedevice;
    }

    public void setDatanamedevice(List<String> datanamedevice) {
        this.datanamedevice = datanamedevice;
    }
}
