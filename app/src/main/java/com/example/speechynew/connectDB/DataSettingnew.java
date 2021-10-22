package com.example.speechynew.connectDB;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataSettingnew {

     String USER_ID;
     String nativelang;
     String percentagenone;
     String chaday;

    @Expose
    @SerializedName("success")
    private boolean success;

    @Expose
    @SerializedName("messages")
    private String messages;

    public DataSettingnew(){}

    public DataSettingnew(String USER_ID, String nativelang, String percentagenone, String chaday) {
        this.USER_ID = USER_ID;
        this.nativelang = nativelang;
        this.percentagenone = percentagenone;
        this.chaday = chaday;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
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
