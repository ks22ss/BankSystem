import org.example.DBConnection;
import org.example.Model.Account;
import org.example.Model.Product.LoanProduct;
import org.example.Model.Product.SavingProduct;
import org.example.Model.Transaction;
import org.example.Services.Bank;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class BankTest {

    private Bank bank;
    private String testUser1 = "testuser1";
    private String testUser2 = "testuser2";
    private String testPassword = "1234";



    @BeforeEach
    public void setUp() {
        System.out.println("Clean Up.");
        SessionFactory sessionFactory = DBConnection.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createNativeQuery("TRUNCATE TABLE bank_accounts, bank_transactions, " +
                "bank_loan_subscriptions, bank_saving_subscriptions, " +
                "bank_loan_products, bank_saving_products" +
                " CASCADE").executeUpdate();
        session.getTransaction().commit();
    }

    @Test
    public void testOpenAccount() {
        bank = new Bank("ABC Bank");
        Account account = bank.openAccount(testUser1, testPassword);
        Assertions.assertNotNull(account);
        Assertions.assertEquals(testUser1, account.getUserName());
        Assertions.assertEquals(testPassword, account.getPassword());
    }

    @Test
    public void testOpenAccountAlreadyOpen() {
        bank = new Bank("ABC Bank");
        Account account = bank.openAccount(testUser1, testPassword);
        Assertions.assertNotNull(account);
        Assertions.assertEquals(testUser1, account.getUserName());
        Assertions.assertEquals(testPassword, account.getPassword());

        Account accountRepeated = bank.openAccount(testUser1, testPassword);
        Assertions.assertEquals(null, accountRepeated);
    }


    @Test
    public void testCloseAccount() {
        bank = new Bank("ABC Bank");
        Account account = bank.openAccount(testUser1, testPassword);
        Assertions.assertNotNull(account);
        boolean result = bank.closeAccount(testUser1);
        Assertions.assertTrue(result);
        Assertions.assertFalse(bank.readAccountStatus(testUser1));
    }
    @Test
    public void testCloseAccountThatNotExist() {
        bank = new Bank("ABC Bank");
        Account account = bank.openAccount(testUser1, testPassword);
        Assertions.assertNotNull(account);
        boolean result = bank.closeAccount(testUser2);
        Assertions.assertFalse(result);
    }
    @Test
    public void testDeposit() {
        bank = new Bank("ABC Bank");
        Account account = bank.openAccount(testUser1, testPassword);
        Assertions.assertNotNull(account);
        Transaction deposit = bank.deposit(testUser1, 10000);
        Assertions.assertNotNull(deposit);
        Assertions.assertEquals(10000, deposit.getAmount());
        Assertions.assertEquals(bank.getNullAccount().getUserName(),deposit.getFromAccount().getUserName());
        Assertions.assertEquals(account.getUserName(),deposit.getToAccount().getUserName());
        Assertions.assertEquals(10000, bank.readBalance(testUser1));
    }

    @Test
    public void testWithdrawal() {
        bank = new Bank("ABC Bank");
        Account account = bank.openAccount(testUser1, testPassword);
        Assertions.assertNotNull(account);
        Transaction deposit = bank.deposit(testUser1, 10000);
        Transaction withdraw = bank.withdraw(testUser1, 6000);
        Assertions.assertNotNull(withdraw);
        Assertions.assertEquals(6000, withdraw.getAmount());
        Assertions.assertEquals(account.getUserName(), withdraw.getFromAccount().getUserName());
        Assertions.assertEquals(bank.getNullAccount().getUserName(), withdraw.getToAccount().getUserName());
        Assertions.assertEquals(4000, bank.readBalance(testUser1));
    }

    @Test
    public void testTransfer() {
        bank = new Bank("ABC Bank");

        Account account1 = bank.openAccount(testUser1, testPassword);
        Account account2 = bank.openAccount(testUser2, testPassword);

        Assertions.assertNotNull(account1);
        Assertions.assertNotNull(account2);

        Transaction deposit1 = bank.deposit(testUser1, 10000);
        Transaction deposit2 = bank.deposit(testUser2, 20000);
        Transaction transfer = bank.transfer(testUser1, testUser2, 8000);
        Assertions.assertNotNull(transfer);
        Assertions.assertEquals(8000, transfer.getAmount());
        Assertions.assertEquals(account1.getUserName(), transfer.getFromAccount().getUserName());
        Assertions.assertEquals(account2.getUserName(), transfer.getToAccount().getUserName());
        Assertions.assertEquals(2000, bank.readBalance(testUser1));
        Assertions.assertEquals(28000, bank.readBalance(testUser2));

    }
    @Test
    public void testAddGetSubscribeSavingProduct() {
        bank = new Bank("ABC Bank");
        Account account1 = bank.openAccount(testUser1, testPassword);
        Transaction deposit1 = bank.deposit(testUser1, 10000);
        bank.addSavingProduct(new SavingProduct("SAVE_U82_0001",36,0.01,10000));
        SavingProduct savingProduct = bank.getSavingProduct("SAVE_U82_0001");
        Assertions.assertEquals("SAVE_U82_0001", savingProduct.getProductCode());
        boolean success = bank.subscribeSaving(testUser1, "SAVE_U82_0001");
        Assertions.assertTrue(success);
    }

    @Test
    public void testAddGetSubscribeLoanProduct() {
        bank = new Bank("ABC Bank");
        Account account1 = bank.openAccount(testUser1, testPassword);
        Transaction deposit1 = bank.deposit(testUser1, 10000);
        bank.addLoanProduct(new LoanProduct("LOAN_U82_0001",36,0.01,10000));
        LoanProduct loanProduct = bank.getLoanProduct("LOAN_U82_0001");
        Assertions.assertEquals("LOAN_U82_0001", loanProduct.getProductCode());
        boolean success = bank.subscribeLoan(testUser1, "LOAN_U82_0001");
        Assertions.assertTrue(success);
    }





}
