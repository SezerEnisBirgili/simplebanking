package com.eteration.simplebanking.dto;


// This class is a place holder you can change the complete implementation

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
