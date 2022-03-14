package com.revature.technology.dtos;

import com.revature.technology.models.User;
import com.revature.technology.models.UserRole;

import javax.persistence.Column;
import javax.persistence.Embedded;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class NewUserRequest {

    private String username;
    private String email;
    private String password;
    private String givenName;
    private String surname;
    private String role;

    public NewUserRequest() {
        super();
    }

    public NewUserRequest(String username, String email, String password, String givenName, String surname, String role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.givenName = givenName;
        this.surname = surname;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NewUserRequest)) return false;
        NewUserRequest that = (NewUserRequest) o;
        return Objects.equals(getUsername(), that.getUsername()) && Objects.equals(getEmail(), that.getEmail()) && Objects.equals(getPassword(), that.getPassword()) && Objects.equals(getGivenName(), that.getGivenName()) && Objects.equals(getSurname(), that.getSurname()) && Objects.equals(getRole(), that.getRole());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getEmail(), getPassword(), getGivenName(), getSurname(), getRole());
    }

    @Override
    public String toString() {
        return "NewUserRequest{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", givenName='" + givenName + '\'' +
                ", surname='" + surname + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    //In the database, the role ID and role names are the same. So ADMIN ADMIN would be an entry for example.
    public User extractUser() {
        String user_id = UUID.randomUUID().toString();
        UserRole aRole = new UserRole(this.role, this.role);
        return new User(user_id, username, email, password, givenName, surname, false, aRole);
    }
}
