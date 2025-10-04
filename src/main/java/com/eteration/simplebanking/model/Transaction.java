package com.eteration.simplebanking.model;

import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

// This class is a place holder you can change the complete implementation
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Transaction implements Accountable{

    @Id
    @GeneratedValue
    protected Long id;

	protected Date date;

    @NotNull
    @Positive(message = "Amount must be positive")
    protected Double amount;

    @NotNull
    protected String approvalCode;

    @ManyToOne
    @JoinColumn(name = "account_number")
    private Account account;

    protected Transaction() {
    }

    public Transaction(double amount) {
        this.date = new Date();
        this.amount = amount;
        this.approvalCode = UUID.randomUUID().toString(); 
    }

    @Override
    public String toString() {
        return "Transaction [date=" + date + ", amount=" + amount + "]";
    }

    public Date getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }

    public String getApprovalCode() {
        return approvalCode;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    
}
