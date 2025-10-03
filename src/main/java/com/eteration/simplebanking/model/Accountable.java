package com.eteration.simplebanking.model;

public interface Accountable {
    void apply (Account account) throws InsufficientBalanceException;
}
