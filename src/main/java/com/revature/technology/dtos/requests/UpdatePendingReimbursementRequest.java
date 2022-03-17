package com.revature.technology.dtos.requests;

import java.util.Arrays;

public class UpdatePendingReimbursementRequest {
    private String reimbursementId;
    private double amount;
    private String description;
    private byte[] receipt;
    private String reimbursementType;

    public UpdatePendingReimbursementRequest() {
        super();
    }

    public UpdatePendingReimbursementRequest(String reimbursementId, double amount, String description, byte[] receipt, String reimbursementType) {
        this.reimbursementId = reimbursementId;
        this.amount = amount;
        this.description = description;
        this.receipt = receipt;
        this.reimbursementType = reimbursementType;
    }

    public String getReimbursementId() {
        return reimbursementId;
    }

    public void setReimbursementId(String reimbursementId) {
        this.reimbursementId = reimbursementId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getReceipt() {
        return receipt;
    }

    public void setReceipt(byte[] receipt) {
        this.receipt = receipt;
    }

    public String getReimbursementType() {
        return reimbursementType;
    }

    public void setReimbursementType(String reimbursementType) {
        this.reimbursementType = reimbursementType;
    }

    @Override
    public String toString() {
        return "UpdatePendingReimbursementRequest{" +
                "reimbursementId='" + reimbursementId + '\'' +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", receipt=" + Arrays.toString(receipt) +
                ", reimbursementType='" + reimbursementType + '\'' +
                '}';
    }
}
