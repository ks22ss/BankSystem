package org.example;


import org.example.Model.Account;
import org.example.Model.Transaction;
import org.example.Services.Bank;


import java.sql.*;

public class Main {
    public static void main(String[] args) throws Exception{
        try {
            Bank myBank = new Bank("ABC Bank");


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}