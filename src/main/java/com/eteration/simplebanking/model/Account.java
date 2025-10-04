package com.eteration.simplebanking.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;

// This class is a place holder you can change the complete implementation

@Entity
public class Account {

    @Id
    private String accountNumber;
    
    @NotNull
    private String owner;

    private double balance;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions = new ArrayList<>();

    public Account() {
    }

    public Account(String owner, String accountNumber) {
        this.accountNumber = accountNumber;
        this.owner = owner;
        this.balance = 0;
    }

    public void post(Transaction t) throws InsufficientBalanceException {
        t.setAccount(this);
        t.apply(this);
        transactions.add(t);    
    }

    public void deposit(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount must be non-negative");
        }
        balance += amount;
    }

    public void withdraw(double amount) throws InsufficientBalanceException {
        if (amount < 0) {
        throw new IllegalArgumentException("Amount must be non-negative");
        }
        if (amount > balance) {
            throw new InsufficientBalanceException();
        }
        balance -= amount;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwner() {
        return owner;
    }

    public double getBalance() {
        return balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }


}
