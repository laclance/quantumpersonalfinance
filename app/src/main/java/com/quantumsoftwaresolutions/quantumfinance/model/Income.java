package com.quantumsoftwaresolutions.quantumfinance.model;

public class Income extends Item {
    public Income() {
    }

    public Income(String type, String description, String date, String freq, double amount) {
        super(type, description, date, freq, amount);
    }
}
