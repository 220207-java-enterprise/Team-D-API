package com.revature.technology.dtos.responses;

import com.revature.technology.models.User;

public class Principal {

    private String id;
    private String username;

    private String role;

    public Principal(){
    }

    public Principal(User authUser) {
        this.id = authUser.getUserId();
        this.username = authUser.getUsername();
        this.role = authUser.getRole().getRole();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Principal{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
