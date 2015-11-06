package com.quantumsoftwaresolutions.quantumfinance.model;

public class History {
    private long id;
    private String date;
    private double income;
    private double expenses;

    public History() {
    }

    public History(String date, double income, double expenses) {
        this.date = date;
        this.income = income;
        this.expenses = expenses;
    }

    public long getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public double getExpenses() {
        return expenses;
    }

    public void setExpenses(double expenses) {
        this.expenses = expenses;
    }
}
