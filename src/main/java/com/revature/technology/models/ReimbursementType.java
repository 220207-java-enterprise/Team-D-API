package com.revature.technology.models;

import javax.persistence.*;


@Entity
@Table(name = "ers_reimbursement_types")
public class ReimbursementType {
    //It needs to be called type_id for Reimbursement
    @Id
    private String type_id;

    @Column(unique = true)
    private String type;

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ErsReimbType{" +
                "type_id='" + type_id + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
