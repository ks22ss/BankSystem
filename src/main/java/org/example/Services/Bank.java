package org.example.Services;

import org.example.Model.Account;
import org.example.Model.Transaction;
import org.example.Model.TransactionType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Bank {
    private static Bank instance;
    private Map<String, Account> accounts;

    private Bank() {
        this.accounts = new HashMap<String, Account>();
    }


    public static Bank getInstance() {
        if (instance == null) {
            synchronized (Bank.class) {
                if (instance == null) {
                    instance = new Bank();
                }
            }
        }
        return instance;
    }

    public void openAccount(String userName, String password) {
        Account account = new Account(userName, password);
        this.accounts.put(userName, account);
    }

    public void closeAccount(String userName) {
        accounts.get(userName).close();
    }

    public void deposit(String userName, double amount) {
        accounts.get(userName).addToBalance(amount);
        Transaction depositTransaction = new Transaction(null, userName, amount, TransactionType.DEPOSIT );
        accounts.get(userName).addTransaction(depositTransaction);
    }
    public void withdraw(String userName, double amount) {
        accounts.get(userName).subtractBalance(amount);
        Transaction withdrawTransaction = new Transaction(userName, null, amount, TransactionType.WITHDRAW );
        accounts.get(userName).addTransaction(withdrawTransaction);
    }

    public void transfer(String fromUserName, String toUserName, double amount){
        accounts.get(fromUserName).subtractBalance(amount);
        accounts.get(toUserName).addToBalance(amount);
        Transaction transferTransaction = new Transaction(fromUserName, toUserName, amount, TransactionType.TRANSFER );
        accounts.get(fromUserName).addTransaction(transferTransaction);
        accounts.get(toUserName).addTransaction(transferTransaction);

    }
}
