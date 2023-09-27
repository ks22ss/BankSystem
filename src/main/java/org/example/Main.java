package org.example;

import org.example.Exception.AccountClosedException;
import org.example.Exception.AccountNotExceedException;
import org.example.Services.Bank;

public class Main {
    public static void main(String[] args) throws AccountNotExceedException, AccountClosedException {

        // Start by initialize a bank
        Bank ABCBank = Bank.getInstance();

        ABCBank.openAccount("chan239xx2", "12345");
        ABCBank.openAccount("wong22312", "23456");
        ABCBank.openAccount("henry7482", "34567");

        ABCBank.deposit("chan239xx2", 10000);
        ABCBank.deposit("wong22312", 20000);
        ABCBank.deposit("henry7482", 30000);
        ABCBank.deposit("wong22312", 5000);
        ABCBank.withdraw("henry7482", 12000);
        ABCBank.transfer("chan239xx2", "wong22312", 2500);

        double chanBalance = ABCBank.readBalance("chan239xx2");
        double wongBalance = ABCBank.readBalance("wong22312");
        double henryBalance = ABCBank.readBalance("henry7482");

        System.out.println("Chan Balance: "+ chanBalance);
        System.out.println("Wong Balance: "+ wongBalance);
        System.out.println("Henry Balance: "+ henryBalance);


    }
}