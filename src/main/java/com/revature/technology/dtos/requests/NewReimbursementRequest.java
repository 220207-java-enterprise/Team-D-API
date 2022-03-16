package com.revature.technology.dtos.requests;

import java.util.Arrays;

public class NewReimbursementRequest {
    private double amount;
    private String description;
    private byte[] receipt;
    private String payment_id;
    private String reimbursementType;

    public NewReimbursementRequest() {
        super();
    }

    public NewReimbursementRequest(double amount, String description, byte[] receipt, String payment_id, String reimbursementType) {
        this.amount = amount;
        this.description = description;
        this.receipt = receipt;
        this.payment_id = payment_id;
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

    public String getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
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
                ", payment_id='" + payment_id + '\'' +
                ", reimbursementType='" + reimbursementType + '\'' +
                '}';
    }
}
