package org.example.Exception;

public class AccountClosedException  extends  Exception{
    public  AccountClosedException(String userName) {
        super("Account "+userName+" already closed. Action cannot be perform.");
    }
}
