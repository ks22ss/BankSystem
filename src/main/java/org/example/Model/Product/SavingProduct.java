package org.example.Model.Product;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "bank_saving_products")
public class SavingProduct {

    @Id
    private String productCode;
    @Column
    private int period;
    @Column
    private double interestRate;

    @Column
    private double principle;

    public SavingProduct()  {}
    public SavingProduct(String productCode, int period, double interestRate, double principle) {
        this.productCode = productCode;
        this.period = period;
        this.interestRate = interestRate;
        this.principle = principle;
    }

    public String getProductCode() {
        return productCode;
    }

    public int getPeriod() {
        return period;
    }

    public double getInterestRate() {
        return interestRate;
    }


    public double getPrinciple() {
        return principle;
    }
}
