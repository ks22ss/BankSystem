package org.example.Exception;

public class BalanceNotEnoughException extends Exception{
    public BalanceNotEnoughException() {
        super("Error: Balance not enough.");
    }
}
