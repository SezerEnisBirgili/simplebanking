package com.eteration.simplebanking.dto;

import java.util.List;

public class AccountDTO {
    private String accountNumber;
    private String owner;
    private double balance;
    private List<TransactionDTO> transactions;

    public AccountDTO() {}

    public AccountDTO(String accountNumber, String owner, double balance, List<TransactionDTO> transactions) {
        this.accountNumber = accountNumber;
        this.owner = owner;
        this.balance = balance;
        this.transactions = transactions;
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

    public List<TransactionDTO> getTransactions() {
        return transactions;
    }
}
