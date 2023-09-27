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

}
