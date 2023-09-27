package org.example.Services;

import org.example.Exception.AccountClosedException;
import org.example.Exception.AccountNotExceedException;
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

    public double readBalance(String userName) throws AccountClosedException, AccountNotExceedException {
        Account account = accounts.get(userName);
        if (account == null) {
            throw new AccountNotExceedException();
        }
        if (!account.isActive()) {
            throw  new AccountClosedException();
        }
        return account.getBalance();
    }

    public void deposit(String userName, double amount) throws AccountClosedException, AccountNotExceedException {
        Account account = accounts.get(userName);
        if (account == null) {
            throw new AccountNotExceedException(userName);
        }
        if (!account.isActive()) {
            throw new AccountClosedException(userName);
        }
        account.addToBalance(amount);
        Transaction depositTransaction = new Transaction(null, userName, amount, TransactionType.DEPOSIT );
        account.addTransaction(depositTransaction);
    }
    public void withdraw(String userName, double amount) throws AccountClosedException, AccountNotExceedException {
        Account account = accounts.get(userName);
        if (account == null) {
            throw new AccountNotExceedException(userName);
        }
        if (!account.isActive()) {
            throw new AccountClosedException(userName);
        }
        account.subtractBalance(amount);
        Transaction withdrawTransaction = new Transaction(userName, null, amount, TransactionType.WITHDRAW );
        account.addTransaction(withdrawTransaction);
    }

    public void transfer(String fromUserName, String toUserName, double amount) throws AccountClosedException, AccountNotExceedException {
        Account fromAccount = accounts.get(fromUserName);
        Account toAccount = accounts.get(toUserName);
        if (fromAccount == null) {
            throw new AccountNotExceedException(fromUserName);
        }
        if (toAccount == null) {
            throw new AccountNotExceedException(toUserName);
        }
        if (!fromAccount.isActive()) {
            throw new AccountClosedException(fromUserName);
        }
        if (!toAccount.isActive()) {
            throw new AccountClosedException(toUserName);
        }

        fromAccount.subtractBalance(amount);
        toAccount.addToBalance(amount);
        Transaction transferTransaction = new Transaction(fromUserName, toUserName, amount, TransactionType.TRANSFER );
        fromAccount.addTransaction(transferTransaction);
        toAccount.addTransaction(transferTransaction);

    }
}
