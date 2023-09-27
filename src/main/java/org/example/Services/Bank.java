package org.example.Services;

import org.example.Model.Account;

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

}
