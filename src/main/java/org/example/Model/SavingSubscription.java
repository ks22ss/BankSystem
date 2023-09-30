package org.example.Model;

import jakarta.persistence.*;
import org.example.Model.Product.LoanProduct;
import org.example.Model.Product.SavingProduct;

import java.util.Date;

@Entity
@Table(name = "bank_saving_subscriptions")
public class SavingSubscription {
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
    private SavingProduct subscribedSavingProduct;



    public SavingSubscription(Account subscribedAccount, SavingProduct subscribedSavingProduct) {
        this.subscribedAccount = subscribedAccount;
        this.subscribedSavingProduct = subscribedSavingProduct;

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

    public SavingProduct getSubscribedSavingProduct() {
        return subscribedSavingProduct;
    }


}
