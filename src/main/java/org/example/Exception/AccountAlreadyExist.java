package org.example.Exception;

public class AccountAlreadyExist extends Exception{
    public  AccountAlreadyExist(String userName) {
        super("Account "+userName+" already exists. Action cannot be perform.");
    }
}
