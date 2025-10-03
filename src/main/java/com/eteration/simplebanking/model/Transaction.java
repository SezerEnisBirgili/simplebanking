package com.eteration.simplebanking.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

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
