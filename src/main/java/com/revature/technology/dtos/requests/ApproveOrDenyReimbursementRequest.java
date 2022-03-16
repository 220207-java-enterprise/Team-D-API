package com.revature.technology.dtos.requests;

public class ApproveOrDenyReimbursementRequest {
    private String reimbursementId;
    private boolean approve;

    public ApproveOrDenyReimbursementRequest() {
        super();
    }

    public ApproveOrDenyReimbursementRequest(String reimbursementId, boolean approve) {
        this.reimbursementId = reimbursementId;
        this.approve = approve;
    }

    public String getReimbursementId() {
        return reimbursementId;
    }

    public void setReimbursementId(String reimbursementId) {
        this.reimbursementId = reimbursementId;
    }

    public boolean getApprove() {
        return approve;
    }

    public void setApprove(boolean approve) {
        this.approve = approve;
    }

    @Override
    public String toString() {
        return "ApproveOrDenyReimbursementRequest{" +
                "reimbursementId='" + reimbursementId + '\'' +
                ", approve=" + approve +
                '}';
    }
}
