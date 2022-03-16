package com.revature.technology.dtos.requests;

import java.util.Arrays;

public class NewReimbursementRequest {
    private double amount;
    private String description;
    private byte[] receipt;
    private String reimbursementType;

    public NewReimbursementRequest() {
        super();
    }

    public NewReimbursementRequest(double amount, String description, byte[] receipt, String reimbursementType) {
        this.amount = amount;
        this.description = description;
        this.receipt = receipt;
        this.reimbursementType = reimbursementType;
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
        return "NewReimbursementRequest{" +
                "amount=" + amount +
                ", description='" + description + '\'' +
                ", receipt=" + Arrays.toString(receipt) +
                ", reimbursementType='" + reimbursementType + '\'' +
                '}';
    }
}
