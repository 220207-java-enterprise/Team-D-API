package com.revature.technology.dtos.prism.requests;

public class PostPaymentRequest {

    private String payeeId;
    private double paymentAmount;

    public PostPaymentRequest(){super();}

    public PostPaymentRequest(String payeeId, double paymentAmount){
        this.payeeId = payeeId;
        this.paymentAmount = paymentAmount;
    }

    public String getPayeeId() {
        return payeeId;
    }

    public void setPayeeId(String payeeId) {
        this.payeeId = payeeId;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    @Override
    public String toString() {
        return "PostPaymentRequest{" +
                "payeeId='" + payeeId + '\'' +
                ", paymentAmount=" + paymentAmount +
                '}';
    }
}
