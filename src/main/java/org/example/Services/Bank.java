package org.example.Services;
import org.example.DBConnection;
import org.example.Model.*;
import org.example.Model.Product.LoanProduct;
import org.example.Model.Product.SavingProduct;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.List;


public class Bank {

    private double interestRateSaving = 0.005;

    private Account nullAccount;

    public Account getNullAccount() {
        return nullAccount;
    }
    public Bank(String name) {
        this.nullAccount = this.openAccount("nulluser", "admin");
    }
    public Bank(String name, double interestRate) {
        nullAccount = this.openAccount("nulluser", "admin");
        interestRateSaving = interestRate;
    }

    public void addLoanProduct(LoanProduct loanProduct) {
        SessionFactory sessionFactory = DBConnection.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.persist(loanProduct);
        session.getTransaction().commit();
        session.close();
    }

    public void addSavingProduct(SavingProduct savingProduct) {
        SessionFactory sessionFactory = DBConnection.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.persist(savingProduct);
        session.getTransaction().commit();
        session.close();
    }

    public LoanProduct getLoanProduct(String loanProductCode) {
        SessionFactory sessionFactory = DBConnection.getSessionFactory();
        Session session = sessionFactory.openSession();
        LoanProduct loanProduct = session.get(LoanProduct.class, loanProductCode);
        session.close();
        return loanProduct;
    }
    public SavingProduct getSavingProduct(String savingProductCode) {
        SessionFactory sessionFactory = DBConnection.getSessionFactory();
        Session session = sessionFactory.openSession();
        SavingProduct savingProduct = session.get(SavingProduct.class, savingProductCode);
        session.close();
        return savingProduct;
    }

    public List<LoanProduct> getAllLoanProduct() {
        SessionFactory sessionFactory = DBConnection.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "FROM LoanProduct";
        List<LoanProduct> resultList = session.createQuery(hql, LoanProduct.class).getResultList();
        session.close();
        return resultList;
    }
    public List<SavingProduct> getAllSavingProduct() {
        SessionFactory sessionFactory = DBConnection.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "FROM SavingProduct";
        List<SavingProduct> resultList = session.createQuery(hql, SavingProduct.class).getResultList();
        session.close();
        return resultList;
    }

    public Boolean subscribeLoan(String userName, String loanProductCode){
        SessionFactory sessionFactory = DBConnection.getSessionFactory();
        Session session = sessionFactory.openSession();
        LoanProduct loanProduct = session.get(LoanProduct.class, loanProductCode);
        Account account = session.get(Account.class, userName);
        if(loanProduct.getPrincipal() > account.getBalance()){return false;}

        session.beginTransaction();
        LoanSubscription loanSub = new LoanSubscription(account, loanProduct);
        session.persist(loanSub);
        account.subtractBalance(loanProduct.getPrincipal());
        session.getTransaction().commit();
        session.close();

        return true;
    }
    public Boolean subscribeSaving(String userName, String savingProductCode){
        SessionFactory sessionFactory = DBConnection.getSessionFactory();
        Session session = sessionFactory.openSession();
        SavingProduct savingProduct = session.get(SavingProduct.class, savingProductCode);
        Account account = session.get(Account.class, userName);
        if(savingProduct.getPrinciple() > account.getBalance()){return false;}

        session.beginTransaction();
        SavingSubscription savingSub = new SavingSubscription(account, savingProduct);
        session.persist(savingSub);
        account.subtractBalance(savingProduct.getPrinciple());
        session.getTransaction().commit();
        session.close();
        return true;
    }
    public void distributeSavingProductInterest() {
        System.out.println("Begin Distribute Interest.");
        SessionFactory sessionFactory = DBConnection.getSessionFactory();
        List<Account> accounts =  this.queryAllAccount();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        for (Account account: accounts) {
            String hql = "FROM SavingSubscription WHERE subscribed_account = :value";
            List<SavingSubscription> userSubSavings = session.createQuery(hql, SavingSubscription.class)
                    .setParameter("value", account.getUserName())
                    .getResultList();
            for (SavingSubscription subSaving: userSubSavings) {
                SavingProduct savingProduct = subSaving.getSubscribedSavingProduct();
                double interestEarn = savingProduct.getPrinciple() * savingProduct.getInterestRate();
                account.addToBalance(interestEarn);
            }
            session.merge(account);
        }
        session.getTransaction().commit();
        session.close();
    }

    public Account openAccount(String userName, String password) {
        System.out.println("Opening Account for user "+userName+"...");
        SessionFactory sessionFactory = DBConnection.getSessionFactory();
        if(this.checkAccountExist(userName)){ return null;}
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Account account = new Account(userName,password);
        session.persist(account);
        session.getTransaction().commit();
        session.close();
        System.out.println("Account Opened for user "+userName);
        return account;
    }

    public Boolean closeAccount(String userName) {
        System.out.println("Closing Account for user "+userName+"...");
        SessionFactory sessionFactory = DBConnection.getSessionFactory();
        if(!this.checkAccountExist(userName)){return false;}
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Account account = session.get(Account.class, userName);
        boolean result = account.close();
        session.getTransaction().commit();
        session.close();
        System.out.println("Account Closed for user "+userName);
        return true;
    }

    public Double readBalance(String userName) {
        System.out.println("Reading Account Balance for user "+userName+"...");
        SessionFactory sessionFactory = DBConnection.getSessionFactory();
        if(!this.checkAccountExist(userName)){return null;}
        Session session = sessionFactory.openSession();

        Account account = session.get(Account.class, userName);
        double balance = account.getBalance();
        session.close();
        System.out.println("Account Balance for user "+userName+" = "+ balance);
        return balance;
    }
    public Boolean readAccountStatus(String userName) {
        System.out.println("Reading Account Status for user "+userName+"...");
        SessionFactory sessionFactory = DBConnection.getSessionFactory();
        if(!this.checkAccountExist(userName)){return null;}
        Session session = sessionFactory.openSession();

        Account account = session.get(Account.class, userName);
        boolean status = account.isActive();
        session.close();
        System.out.println("Account Status for user "+userName+" = "+ status);
        return status;
    }

    public Transaction deposit(String userName, double amount) {
        System.out.println("Deposit initiate for user "+userName+" with amount = "+amount);
        SessionFactory sessionFactory = DBConnection.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Account account = session.get(Account.class, userName);
        account.addToBalance(amount);
        Transaction depositTransaction =
                new Transaction(nullAccount, account, amount, TransactionType.DEPOSIT );

        session.persist(depositTransaction);
        session.getTransaction().commit();
        session.close();
        System.out.println("Deposit Transaction complete for user "+userName+" with amount = "+amount);
        return depositTransaction;
    }
    public Transaction withdraw(String userName, double amount) {
        SessionFactory sessionFactory = DBConnection.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Account account = session.get(Account.class, userName);
        account.subtractBalance(amount);
        Transaction withdrawTransaction =
                new Transaction(account, nullAccount, amount, TransactionType.WITHDRAW );
        session.persist(withdrawTransaction);
        session.getTransaction().commit();
        session.close();
        return withdrawTransaction;
    }

    public Transaction transfer(String fromUserName, String toUserName, double amount) {
        SessionFactory sessionFactory = DBConnection.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Account fromAccount = session.get(Account.class, fromUserName);
        Account toAccount = session.get(Account.class, toUserName);
        fromAccount.subtractBalance(amount);
        toAccount.addToBalance(amount);
        Transaction transferTransaction =
                new Transaction(fromAccount, toAccount, amount, TransactionType.WITHDRAW );
        session.persist(fromAccount);
        session.persist(toAccount);
        session.persist(transferTransaction);
        session.getTransaction().commit();
        session.close();
        return transferTransaction;
    }
    public List<Transaction> readTransaction(String userName) {
        SessionFactory sessionFactory = DBConnection.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "FROM Transaction WHERE fromUserName = :value OR toUserName = :value";
        List<Transaction> resultList = session.createQuery(hql, Transaction.class)
                .setParameter("value", userName)
                .getResultList();
        session.close();
        return resultList;
    }
    public List<Account> queryAllAccount(){
        SessionFactory sessionFactory = DBConnection.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "FROM Account";
        List<Account> resultList = session.createQuery(hql, Account.class).getResultList();
        session.close();
        return resultList;
    }

    public boolean checkAccountExist(String userName) {
        List<Account> accounts = this.queryAllAccount();
        for (Account account: accounts) {
            if(account.getUserName().equals(userName)) {
                System.out.println("Account Already Exist.");
                return true;
            }
        }
        return false;
    }


}
