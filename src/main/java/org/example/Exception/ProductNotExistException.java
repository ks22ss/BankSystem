package org.example.Exception;

public class ProductNotExistException extends Exception {
    public ProductNotExistException(String productName) {
        super("Error: "+ productName +" does not exist.");
    }
}
