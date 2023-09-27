package org.example.Services;

import org.example.Model.Transaction;

import java.util.Date;

public class Account {
    private double balance;
    private String userName;
    private String password;
    private final Date createDate;

    private Transaction[] transactions;

    public Account(String userName, String password) {
        this.userName = userName;
        this.password = password;
        this.createDate = new Date();
    }
    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public Transaction[] getTransactions() {
        return transactions;
    }
}
