package org.example.Model;

import jakarta.persistence.*;
import org.example.Model.Product.LoanProduct;

import java.util.Date;

@Entity
@Table(name = "bank_loan_subscriptions")
public class LoanSubscription {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private Date subscriptionDate = new Date();

    @ManyToOne
    @JoinColumn(name = "subscribed_account")
    private Account subscribedAccount;

    @ManyToOne
    @JoinColumn(name = "subscribed_product", referencedColumnName = "productCode")
    private LoanProduct subscribedLoanProduct;


    public LoanSubscription(Account subscribedAccount, LoanProduct subscribedLoanProduct) {
        this.subscribedAccount = subscribedAccount;
        this.subscribedLoanProduct = subscribedLoanProduct;
    }

    public Long getId() {
        return id;
    }

    public Date getSubscriptionDate() {
        return subscriptionDate;
    }

    public Account getSubscribedAccount() {
        return subscribedAccount;
    }

    public LoanProduct getSubscribedLoanProduct() {
        return subscribedLoanProduct;
    }


}
