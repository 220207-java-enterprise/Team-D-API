package com.revature.technology.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ers_user_roles")
public class UserRole {

    @Id
    private String roleId;

    @Column(unique = true)
    private String role;

    public UserRole(String roleId, String role) {
        this.roleId = roleId;
        this.role = role;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }



    @Override
    public String toString() {
        return "UserRole{" +
                "roleId='" + roleId + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
