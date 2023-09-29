package org.example.Model;

import jakarta.persistence.*;

import java.util.Date;


@Entity
@Table(name = "bank_transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @Column(nullable = false)
    private final Date transactionDate = new Date();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType transactionType;

    @ManyToOne
    @JoinColumn(name = "fromUserName")
    private Account fromAccount;

    @ManyToOne
    @JoinColumn(name = "toUserName")
    private Account toAccount;

    @Column(nullable = false)
    private double amount;



    public Transaction() {

    }
    public Transaction(Account fromAccount, Account toAccount, double amount, TransactionType transactionType) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.transactionType = transactionType;
    }


    public double getAmount() {
        return amount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public Account getFromAccount() {
        return fromAccount;
    }

    public Account getToAccount() {
        return toAccount;
    }

    public long getId() {
        return transactionId;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", transactionDate=" + transactionDate +
                ", transactionType=" + transactionType +
                ", fromAccount=" + fromAccount +
                ", toAccount=" + toAccount +
                ", amount=" + amount +
                '}';
    }
}
