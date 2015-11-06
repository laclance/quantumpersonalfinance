package com.quantumsoftwaresolutions.quantumfinance.model;

public class BasicInfo {
    private String type;
    private String description;

    public BasicInfo(String type, String description) {
        this.type = type;
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return " type: " + type +
                " description: " + description;
    }
}
