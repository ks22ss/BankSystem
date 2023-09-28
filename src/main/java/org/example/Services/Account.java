package org.example.Services;

import org.example.Model.Products.FinancialProduct;
import org.example.Model.Transaction;

import java.util.ArrayList;
import java.util.Date;

public class Account {
    private double balance;
    private String userName;
    private String password;
    private final Date createDate;

    private boolean isActive;

    private ArrayList<Transaction> transactions;
    private ArrayList<FinancialProduct> subscriptions;

    Account(String userName, String password) {
        this.userName = userName;
        this.password = password;
        this.createDate = new Date();
        this.balance = 0;
        this.isActive = true;
        this.transactions = new ArrayList<Transaction>();
        this.subscriptions = new ArrayList<FinancialProduct>();
    }
    double getBalance() {
        return balance;
    }

    void addToBalance(double amount) {
        this.balance += amount;
    }
    void subtractBalance(double amount) {
        this.balance -= amount;
    }

    String getUserName() {
        return userName;
    }

    void setUserName(String userName) {
        this.userName = userName;
    }

    String getPassword() {
        return password;
    }

    void setPassword(String password) {
        this.password = password;
    }

    Date getCreateDate() {
        return createDate;
    }

    ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public boolean isActive() {
        return isActive;
    }

    void close() {
        isActive = false;
    }

    void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
    }

    ArrayList<FinancialProduct> getSubscriptions() {
        return subscriptions;
    }

    void subscribe(FinancialProduct product){
        this.subscriptions.add(product);
    }
}
