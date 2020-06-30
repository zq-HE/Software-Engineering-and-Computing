package com.example.hotel.po;

import java.util.Date;

public class CreditRecord {
    private int id;
    private int userId;
    private double changeValue;
    private double restValue;
    private Date changeTime;
    private String reason;

    public void setRestValue(double restValue) {
        this.restValue = restValue;
    }

    public double getRestValue() {
        return restValue;
    }

    public Date getChangeTime() {
        return changeTime;
    }

    public double getChangeValue() {
        return changeValue;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getReason() {
        return reason;
    }

    public void setChangeTime(Date changeTime) {
        this.changeTime = changeTime;
    }

    public void setChangeValue(double changeValue) {
        this.changeValue = changeValue;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
