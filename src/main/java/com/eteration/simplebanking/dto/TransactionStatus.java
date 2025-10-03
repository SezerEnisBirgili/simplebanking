package com.eteration.simplebanking.dto;


public class TransactionStatus {
    private String status;
    private String approvalCode;

    public TransactionStatus(String status, String approvalCode) {
        this.status = status;
        this.approvalCode = approvalCode;
    }

    public String getStatus() { 
        return status; 
    }
    public String getApprovalCode() { 
        return approvalCode; 
    }
}
