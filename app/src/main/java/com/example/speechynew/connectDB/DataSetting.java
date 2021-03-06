package com.example.speechynew.connectDB;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataSetting {
    @Expose
    @SerializedName("SETTING_ID")
    private int SETTING_ID;

    @Expose
    @SerializedName("USER_ID")
    private String USER_ID;

    @Expose
    @SerializedName("setting")
    private String setting;


    @Expose
    @SerializedName("nativelang")
    private String nativelang;

    @Expose
    @SerializedName("percentagenone")
    private String percentagenone;

    @Expose
    @SerializedName("chaday")
    private String chaday;

    @Expose
    @SerializedName("success")
    private boolean success;

    @Expose
    @SerializedName("messages")
    private String messages;

    public DataSetting(){}

    public int getSETTING_ID() {
        return SETTING_ID;
    }

    public void setSETTING_ID(int SETTING_ID) {
        this.SETTING_ID = SETTING_ID;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public String getSetting() {
        return setting;
    }

    public void setSetting(String setting) {
        this.setting = setting;
    }

    public String getNativelang() {
        return nativelang;
    }

    public void setNativelang(String nativelang) {
        this.nativelang = nativelang;
    }

    public String getPercentagenone() {
        return percentagenone;
    }

    public void setPercentagenone(String percentagenone) {
        this.percentagenone = percentagenone;
    }

    public String getChaday() {
        return chaday;
    }

    public void setChaday(String chaday) {
        this.chaday = chaday;
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
