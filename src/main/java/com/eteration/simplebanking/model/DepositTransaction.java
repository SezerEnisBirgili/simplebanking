package com.eteration.simplebanking.model;

import jakarta.persistence.Entity;

// This class is a place holder you can change the complete implementation
@Entity
public class DepositTransaction  extends Transaction {

    public DepositTransaction(double amount) {
        super(amount);
    }

    protected DepositTransaction() {
        super();
    }

    @Override
    public void apply(Account account) {
        account.deposit(amount);
    }

}
