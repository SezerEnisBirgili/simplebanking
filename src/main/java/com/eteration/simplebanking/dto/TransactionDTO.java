package com.eteration.simplebanking.dto;

import java.util.Date;

public class TransactionDTO {
    private Date date;
    private double amount;
    private String type;
    private String approvalCode;

    public TransactionDTO() {}

    public TransactionDTO(Date date, double amount, String type, String approvalCode) {
        this.date = date;
        this.amount = amount;
        this.type = type;
        this.approvalCode = approvalCode;
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

    public String getType() {
        return type;
    }
}

