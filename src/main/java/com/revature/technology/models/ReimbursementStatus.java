package com.revature.technology.models;

import javax.persistence.*;

@Entity
@Table(name = "ers_reimbursement_statuses")
public class ReimbursementStatus {
    //It needs to be called status_id for Reimbursement

    @Id
    private String statusId;

    @Column(unique = true)
    private String status;

    public ReimbursementStatus() {
        super();
    }

    public ReimbursementStatus(String statusId, String status) {
        this.statusId = statusId;
        this.status = status;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ErsReimbStatus{" +
                "statusId='" + statusId + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
