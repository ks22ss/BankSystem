package org.example.Model;

import java.util.Date;
import java.util.UUID;

public class Transaction {
    private final UUID id;
    private final Date transactionDate;

    private final TransactionType transactionType;
    private final String from;
    private final String to;
    private final double amount;

    public Transaction(String from, String to, double amount, TransactionType transactionType) {
        this.id = UUID.randomUUID();
        this.transactionDate = new Date();
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.transactionType = transactionType;
    }

    @Override
    public String toString() {
        return "ID="+this.id+" "+"transactionDate="+this.transactionDate+" "+"from="+this.from+" "+"to="+this.to+" "+"amount="+this.amount+" "+"transactionType="+this.transactionType;
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

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public UUID getId() {
        return id;
    }
}
