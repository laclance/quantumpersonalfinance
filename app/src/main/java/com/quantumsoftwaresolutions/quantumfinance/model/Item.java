package com.quantumsoftwaresolutions.quantumfinance.model;

public class Item {
    private long id;
    private BasicInfo basicInfo;
    private String date;
    private String frequency;
    private double amount;

    public Item() {
    }

    public Item(String type, String description, String date, String freq, double amount) {
        this.basicInfo = new BasicInfo(type, description);
        this.date = date;
        this.frequency = freq;
        this.amount = amount;
    }

    public void editItem(String type, String description, String date, String freq, double amount) {
        this.basicInfo = new BasicInfo(type, description);
        this.date = date;
        this.frequency = freq;
        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BasicInfo getBasicInfo() {
        return basicInfo;
    }

    public void setBasicInfo(String type, String description) {
        this.basicInfo = new BasicInfo(type, description);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}

