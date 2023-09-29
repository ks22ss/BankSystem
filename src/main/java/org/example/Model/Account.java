package org.example.Model;

import jakarta.persistence.*;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "bank_accounts")
public class Account {

    @Id
    @Column
    private String userName;

    @Column
    private String password;

    @Column(nullable = false)
    private double balance = 0.0;

    @Column(nullable = false)
    private Date createDate = new Date();

    @Column(nullable = false)
    private boolean isActive = true;

    @OneToMany(mappedBy = "fromAccount")
    private List<Transaction> outgoingTransactions;

    @OneToMany(mappedBy = "toAccount")
    private List<Transaction> incomingTransactions;

    public Account() {

    }
    public Account(String userName, String password) {
        this.userName = userName;
        this.password = password;
        this.createDate = new Date();
        this.balance = 0;
        this.isActive = true;
    }
    public double getBalance() {
        return balance;
    }

    public void addToBalance(double amount) {
        this.balance += amount;
    }
    public void subtractBalance(double amount) {
        this.balance -= amount;
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

    public boolean isActive() {
        return isActive;
    }

    public boolean close() {
        isActive = false;
        return isActive;
    }


    @Override
    public String toString() {
        return "Account{" +
                ", balance=" + balance +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", createDate=" + createDate +
                ", isActive=" + isActive +
                '}';
    }
}
