package org.example.Model.Products;

public class Loan implements FinancialProduct {
    private final String name;
    private final ProductType type;
    private final double interestRate;
    private final int period;
    private double principle;
    public Loan(String name, ProductType type, double interestRate, int period) {
        this.name = name;
        this.type = type;
        this.interestRate = interestRate;
        this.period = period;
    }
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public ProductType getType() {
        return this.type;
    }

    @Override
    public double getInterestRate() {
        return this.interestRate;
    }

    @Override
    public int getPeriod() {
        return this.period;
    }

    @Override
    public double getPrinciple() {
        return this.principle;
    }

    @Override
    public void setPrinciple(double principle) {
        this.principle = principle;
    }
}
