package org.example.Services;

import org.example.Model.Account;

import java.util.ArrayList;

public class Bank {
    private static Bank instance;
    private ArrayList<Account> accounts;

    private Bank() {
        this.accounts = new ArrayList<Account>();
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
        this.accounts.add(account);
    }

    public void closeAccount(String userName) {
        Account account = this.searchAccount(userName);
        account.close();
    }

    public Account searchAccount(String userName) {
        Account foundAccount = null;
        for (Account acc : this.accounts){
            if(acc.getUserName().equals(userName)){
                foundAccount = acc;
                break;
            }
        }
        return foundAccount;
    }
}
