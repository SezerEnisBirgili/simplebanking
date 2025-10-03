package com.eteration.simplebanking.model;

import javax.persistence.Entity;

@Entity
public class BillPaymentTransaction extends WithdrawalTransaction {
    private String payee;

    public BillPaymentTransaction(String payee, double amount) {
        super(amount);
        this.payee = payee;
    }

    protected BillPaymentTransaction() {
        super();
    }

    @Override
    public String toString() {
        return "BillPaymentTransaction [payee=" + payee + ", date=" + date + ", amount=" + amount + "]";
    }

    
}
