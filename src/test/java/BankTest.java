import org.example.Exception.AccountAlreadyExist;
import org.example.Exception.AccountClosedException;
import org.example.Exception.AccountNotExistException;
import org.example.Model.Products.FinancialProduct;
import org.example.Model.Transaction;
import org.example.Services.Account;
import org.example.Services.Bank;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Map;

public class BankTest {

    private Bank myBank;
    @BeforeEach
    public void setUp() {
        myBank = new Bank();
    }

    @Test
    public void testOpenAccount() throws AccountAlreadyExist {
        Account account = myBank.openAccount("testuser1234","12345");
        Map<String, Account> allAccount = myBank.queryAllAccount();
        Assertions.assertTrue(allAccount.containsKey("testuser1234"));
        Assertions.assertTrue(allAccount.containsValue(account));
    }

    @Test
    public void testCloseAccount() throws AccountAlreadyExist, AccountNotExistException {
        Account account = myBank.openAccount("testuser1234","12345");
        boolean success = myBank.closeAccount("testuser1234");
        Assertions.assertTrue(success);
        Map<String, Account> allAccount = myBank.queryAllAccount();
        Assertions.assertTrue(allAccount.containsKey("testuser1234"));
        Assertions.assertTrue(allAccount.containsValue(account));
        Assertions.assertFalse(account.isActive());
    }

    @Test
    public void testReadBalance() throws AccountAlreadyExist, AccountClosedException, AccountNotExistException {
        Account account = myBank.openAccount("testuser1234","12345");
        myBank.deposit("testuser1234", 10000);
        double balance = myBank.readBalance("testuser1234");
        Assertions.assertEquals(balance, 10000);
        myBank.deposit("testuser1234", 5000);
        balance = myBank.readBalance("testuser1234");
        Assertions.assertEquals(balance, 15000);
    }

    @Test
    public void testReadTransaction() throws AccountAlreadyExist, AccountClosedException, AccountNotExistException {
        ArrayList<Transaction> transactions;

        Account account1 = myBank.openAccount("testuser1234","12345");
        Account account2 = myBank.openAccount("testuser5432","12345");

        //Deposit
        Transaction depositTransaction = myBank.deposit("testuser1234", 10000);
        transactions = myBank.readTransaction("testuser1234");
        Assertions.assertTrue(transactions.contains(depositTransaction));

        //Withdraw
        Transaction withdrawTransaction = myBank.withdraw("testuser1234", 5000);
        transactions = myBank.readTransaction("testuser1234");
        Assertions.assertTrue(transactions.contains(withdrawTransaction));


        ArrayList<Transaction> transactions1;
        ArrayList<Transaction> transactions2;
        //Transfer
        Transaction transferTransaction = myBank.transfer("testuser1234", "testuser5432", 2000);
        transactions1 = myBank.readTransaction("testuser1234");
        transactions2 = myBank.readTransaction("testuser5432");

        Assertions.assertTrue(transactions1.contains(transferTransaction));
        Assertions.assertTrue(transactions2.contains(transferTransaction));
    }

    @Test
    public void testDeposit() throws AccountAlreadyExist, AccountClosedException, AccountNotExistException {
        Account account = myBank.openAccount("testuser1234","12345");

        Transaction depositTransaction = myBank.deposit("testuser1234", 10000);
        Assertions.assertEquals(depositTransaction.getAmount(), 10000);
        Assertions.assertEquals(myBank.readBalance("testuser1234"), 10000);

        Transaction depositTransaction2 = myBank.deposit("testuser1234", 4500);
        Assertions.assertEquals(depositTransaction2.getAmount(), 4500);
        Assertions.assertEquals(myBank.readBalance("testuser1234"), 14500);
    }

    @Test
    public void testWithdraw() throws AccountAlreadyExist, AccountClosedException, AccountNotExistException {
        Account account = myBank.openAccount("testuser1234","12345");

        Transaction depositTransaction = myBank.deposit("testuser1234", 10000);

        Transaction withdrawTransaction1 = myBank.withdraw("testuser1234", 6000);
        Assertions.assertEquals(withdrawTransaction1.getAmount(), 6000);
        Assertions.assertEquals(myBank.readBalance("testuser1234"), 4000);

        Transaction withdrawTransaction2 = myBank.withdraw("testuser1234", 2500);
        Assertions.assertEquals(withdrawTransaction2.getAmount(), 2500);
        Assertions.assertEquals(myBank.readBalance("testuser1234"), 1500);
    }

    @Test
    public void testTransfer() throws AccountAlreadyExist, AccountClosedException, AccountNotExistException {
        Account account1 = myBank.openAccount("testuser1234","12345");
        Account account2 = myBank.openAccount("testuser5432","12345");

        Transaction depositTransaction = myBank.deposit("testuser1234", 10000);
        Transaction transferTransaction = myBank.transfer("testuser1234", "testuser5432", 10000);

        Assertions.assertEquals(transferTransaction.getFrom(), "testuser1234");
        Assertions.assertEquals(transferTransaction.getTo(), "testuser5432");
        Assertions.assertEquals(transferTransaction.getAmount(), 10000);

        Assertions.assertEquals(myBank.readBalance("testuser1234"), 0);
        Assertions.assertEquals(myBank.readBalance("testuser5432"), 10000);
    }

    @Test
    public void testQueryAllAccount() throws AccountAlreadyExist{
        Map<String, Account> accounts = myBank.queryAllAccount();
        Assertions.assertTrue(accounts.isEmpty());

        Account account1 = myBank.openAccount("testuser1234","12345");
        Assertions.assertFalse(accounts.isEmpty());
        Assertions.assertTrue(accounts.containsKey("testuser1234"));
        Assertions.assertTrue(accounts.containsValue(account1));
    }

    @Test
    public void testQueryAllProduct() {
        Map<String, FinancialProduct> products = myBank.queryAllProduct();
        Assertions.assertFalse(products.isEmpty());
    }


}
