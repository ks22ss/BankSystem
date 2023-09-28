package org.example.Exception;

public class AccountNotExistException extends Exception {
    public AccountNotExistException(String userName) {
        super("Account "+userName+" Does Not Exceed. Please Try Again.");
    }
}
