package org.example.Services;

import org.example.Exception.*;
import org.example.Model.Products.FinancialProduct;
import org.example.Model.Products.FixDeposit;
import org.example.Model.Products.Loan;
import org.example.Model.Products.ProductType;
import org.example.Model.Transaction;
import org.example.Model.TransactionType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Bank {
    private Map<String, Account> accounts;
    private Map<String, FinancialProduct> financialProductMap;

    public Bank() {
        this.accounts = new HashMap<String, Account>();
        this.financialProductMap = new HashMap<String, FinancialProduct>();
        FinancialProduct productA = new FixDeposit("FIX_6_00001A", ProductType.FIXED_DEPOSIT, 0.045, 12);
        FinancialProduct productB = new FixDeposit("FIX_6_00002B", ProductType.FIXED_DEPOSIT, 0.03, 6);
        FinancialProduct productC = new FixDeposit("FIX_6_00003C", ProductType.FIXED_DEPOSIT, 0.01, 3);
        this.financialProductMap.put(productA.getName(), productA);
        this.financialProductMap.put(productB.getName(), productB);
        this.financialProductMap.put(productC.getName(), productC);

        FinancialProduct productD = new Loan("LOAN_12_00001D", ProductType.FIXED_DEPOSIT, 0.12, 24);
        FinancialProduct productE = new Loan("LOAN_12_00002E", ProductType.FIXED_DEPOSIT, 0.10, 36);
        FinancialProduct productF = new Loan("LOAN_12_00003F", ProductType.FIXED_DEPOSIT, 0.9, 48);
        this.financialProductMap.put(productD.getName(), productD);
        this.financialProductMap.put(productE.getName(), productE);
        this.financialProductMap.put(productF.getName(), productF);
    }


    public Account openAccount(String userName, String password) throws AccountAlreadyExist {
        Map<String, Account> accounts = this.queryAllAccount();
        if(accounts.containsKey(userName)){
            throw new AccountAlreadyExist(userName);
        }
        Account account = new Account(userName, password);
        this.accounts.put(userName, account);
        return account;
    }

    public boolean closeAccount(String userName) throws AccountNotExistException {
        Account account = accounts.get(userName);
        if (account == null) {
            throw new AccountNotExistException(userName);
        }
        accounts.get(userName).close();
        return true;
    }

    public double readBalance(String userName) throws AccountClosedException, AccountNotExistException {
        Account account = accounts.get(userName);
        if (account == null) {
            throw new AccountNotExistException(userName);
        }
        if (!account.isActive()) {
            throw  new AccountClosedException(userName);
        }
        return account.getBalance();
    }

    public ArrayList<Transaction> readTransaction(String userName) throws AccountClosedException, AccountNotExistException {
        Account account = accounts.get(userName);
        if (account == null) {
            throw new AccountNotExistException(userName);
        }
        if (!account.isActive()) {
            throw  new AccountClosedException(userName);
        }
        return account.getTransactions();
    }

    public Transaction deposit(String userName, double amount) throws AccountClosedException, AccountNotExistException {
        Account account = accounts.get(userName);
        if (account == null) {
            throw new AccountNotExistException(userName);
        }
        if (!account.isActive()) {
            throw new AccountClosedException(userName);
        }
        account.addToBalance(amount);
        Transaction depositTransaction = new Transaction(null, userName, amount, TransactionType.DEPOSIT );
        account.addTransaction(depositTransaction);
        return depositTransaction;
    }
    public Transaction withdraw(String userName, double amount) throws AccountClosedException, AccountNotExistException {
        Account account = accounts.get(userName);
        if (account == null) {
            throw new AccountNotExistException(userName);
        }
        if (!account.isActive()) {
            throw new AccountClosedException(userName);
        }
        account.subtractBalance(amount);
        Transaction withdrawTransaction = new Transaction(userName, null, amount, TransactionType.WITHDRAW );
        account.addTransaction(withdrawTransaction);
        return withdrawTransaction;
    }

    public Transaction transfer(String fromUserName, String toUserName, double amount) throws AccountClosedException, AccountNotExistException {
        Account fromAccount = accounts.get(fromUserName);
        Account toAccount = accounts.get(toUserName);
        if (fromAccount == null) {
            throw new AccountNotExistException(fromUserName);
        }
        if (toAccount == null) {
            throw new AccountNotExistException(toUserName);
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
        return transferTransaction;
    }

    public Map<String, Account> queryAllAccount(){
        for (Map.Entry<String, Account> entry: accounts.entrySet()) {
            String name = entry.getKey();
            Account account = entry.getValue();
            System.out.println("Name: "+ name +" Account: "+ account);
        }
        return accounts;
    }

    public Map<String, FinancialProduct> queryAllProduct(){
        for (Map.Entry<String, FinancialProduct> entry : financialProductMap.entrySet()) {
            String name = entry.getKey();
            FinancialProduct product = entry.getValue();
            System.out.println("Name: " + name + ", Product Type: " + product.getType()+ ", Rate: "+ product.getInterestRate() + ", Period: "+ product.getPeriod());
        }
        return financialProductMap;
    }

    public void queryMySubscription(String userName) throws AccountClosedException, AccountNotExistException {
        Account account = accounts.get(userName);
        if (account == null) {
            throw new AccountNotExistException(userName);
        }
        if (!account.isActive()) {
            throw new AccountClosedException(userName);
        }
        ArrayList<FinancialProduct> productsSubscribed = account.getSubscriptions();
        System.out.println("Your subscribed products:");
        for (FinancialProduct product : productsSubscribed) {
            System.out.println(product.getName());
        }
    }


    public void subscribeProduct(String userName, String productName, double principle) throws AccountClosedException, AccountNotExistException, ProductNotExistException, BalanceNotEnoughException{
        Account account = accounts.get(userName);
        if (account == null) {
            throw new AccountNotExistException(userName);
        }
        if (!account.isActive()) {
            throw new AccountClosedException(userName);
        }
        //Find the product in the bank
        FinancialProduct product = this.financialProductMap.get(productName);
        if (product == null) {
            throw new ProductNotExistException(productName);
        }

        if (principle >= account.getBalance()){
            throw new BalanceNotEnoughException();
        }
        product.setPrinciple(principle);
        account.subscribe(product);

        account.subtractBalance(principle);
    }
}
