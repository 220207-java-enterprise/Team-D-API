package com.revature.technology.models;

import javax.persistence.*;

@Entity
@Table(name = "ers_reimbursement_statuses")
public class ReimbursementStatus {
    //It needs to be called status_id for Reimbursement

    @Id
    @OneToMany
    private String status_id;

    @Column(unique = true)
    private String status;

    public String getStatus_id() {
        return status_id;
    }

    public void setStatus_id(String status_id) {
        this.status_id = status_id;
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
                "status_id='" + status_id + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
