package com.quantumsoftwaresolutions.quantumfinance.model;

public class Expense extends Item {
    public Expense() {
        super();
    }

    public Expense(String type, String description, String date, String freq, double amount) {
        super(type, description, date, freq, amount);
    }
}
