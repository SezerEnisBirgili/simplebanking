package com.eteration.simplebanking.model;

import javax.persistence.Entity;

@Entity
public class PhoneBillPaymentTransaction extends BillPaymentTransaction{

    private String phoneNumber;

    public PhoneBillPaymentTransaction(String payee, String phoneNumber, double amount) {
        super(payee, amount);
        this.phoneNumber = phoneNumber;
    }

    protected PhoneBillPaymentTransaction() {
        super();
    }

    @Override
    public String toString() {
        return "PhoneBillPaymentTransaction [phoneNumber=" + phoneNumber + ", date=" + date + ", amount=" + amount
                + "]";
    }


}
