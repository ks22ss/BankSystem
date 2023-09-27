package org.example.Model.Products;

public interface FinancialProduct {
    public String getName();
    public ProductType getType();

    public double getInterestRate();

    public int getPeriod();

    public double getPrinciple();

    public void setPrinciple(double principle);

}
