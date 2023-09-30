package org.example.Model.Product;

import jakarta.persistence.*;

@Entity
@Table(name = "bank_loan_products")
public class LoanProduct implements FinancialProduct{

    @Id
    private String productCode;
    @Column
    private double principal;
    @Column
    private int repayPeriodMonths;
    @Column
    private double annualInterestRate;


    public LoanProduct() {}

    public LoanProduct(String productCode, int repayPeriodMonths,  double annualInterestRate, double principal ) {
        this.productCode = productCode;
        this.principal = principal;
        this.repayPeriodMonths = repayPeriodMonths;
        this.annualInterestRate = annualInterestRate;
    }
    public double getMonthlyRepaymentAmount() {
        double monthlyInterestRate = annualInterestRate / 12.0;
        double r = Math.pow(1 + monthlyInterestRate, repayPeriodMonths);
        return (principal * monthlyInterestRate * r) / (r - 1);
    }

    public double getPrincipal() {
        return principal;
    }

    public int getRepayPeriodMonths() {
        return repayPeriodMonths;
    }

    public double getAnnualInterestRate() {
        return annualInterestRate;
    }

    @Override
    public String toString() {
        return "LoanProduct{" +
                "productCode='" + productCode + '\'' +
                ", principal=" + principal +
                ", repayPeriodMonths=" + repayPeriodMonths +
                ", annualInterestRate=" + annualInterestRate +
                '}';
    }

    public String getProductCode() {
        return productCode;
    }
}
