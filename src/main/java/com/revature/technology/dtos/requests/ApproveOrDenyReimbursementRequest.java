package com.revature.technology.dtos.requests;

public class ApproveOrDenyReimbursementRequest {
    private String reimbursementId;
    private boolean approve;

    public ApproveOrDenyReimbursementRequest() {
        super();
    }

    public ApproveOrDenyReimbursementRequest(boolean approve) {
        this.approve = approve;
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
                "approve=" + approve +
                '}';
    }
}
