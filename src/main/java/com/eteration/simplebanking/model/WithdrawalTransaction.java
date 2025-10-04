package com.eteration.simplebanking.model;

import jakarta.persistence.Entity;

// This class is a place holder you can change the complete implementation
@Entity
public class WithdrawalTransaction extends Transaction {

    public WithdrawalTransaction(double amount) {
        super(amount);
    }

    protected WithdrawalTransaction() {
        super();
    }

    @Override
    public void apply(Account account) throws InsufficientBalanceException {
        account.withdraw(amount);
    }
    
}


