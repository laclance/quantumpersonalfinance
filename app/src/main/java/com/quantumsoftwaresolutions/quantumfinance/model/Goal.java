package com.quantumsoftwaresolutions.quantumfinance.model;

public class Goal extends Item {
    public Goal() {
    }

    public Goal(String type, String description, String date, String freq, double amount) {
        super(type, description, date, freq, amount);
    }
}
