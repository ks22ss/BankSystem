package org.example.Exception;

public class AccountNotExceedException extends Exception {
    public AccountNotExceedException(String userName) {
        super("Account "+userName+" Does Not Exceed. Please Try Again.");
    }
}
