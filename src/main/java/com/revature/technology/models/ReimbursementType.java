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


    public ReimbursementType() {
        super();
    }

    public ReimbursementType(String type_id, String type) {
        this.type_id = type_id;
        this.type = type;
    }

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
