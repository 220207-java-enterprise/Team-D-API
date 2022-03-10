package com.revature.technology.models;

import javax.persistence.*;


@Entity
@Table(name = "ers_reimbursement_types")
public class ReimbursementType {
    //It needs to be called type_id for Reimbursement
    @Id
    private String typeId;

    @Column(unique = true)
    private String type;

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
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
                "typeId='" + typeId + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
