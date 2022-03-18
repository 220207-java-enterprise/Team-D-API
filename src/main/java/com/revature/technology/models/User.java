package com.revature.technology.models;

import javax.persistence.*;


@Entity
@Table(name="ers_users")
public class User {


    @Id
    @Column(name="user_id")
    private String userId;

    @Column(name="username", nullable = false)
    private String username;

    @Column(name="email", nullable = false)
    private String email;

    @Column(name="password", nullable = false)
    private String password;

    @Column(name="given_name", nullable = false)
    private String givenName;

    @Column(name="surname", nullable = false)
    private String surname;

    @Column(name="is_active", nullable = false)
    private boolean isActive;

    @ManyToOne
    @JoinColumn(name="role_id", nullable = false)
    private UserRole role;

      public User() {
        super();
    }

    public User(String userId, String username, String email, String password, String givenName, String surname, boolean isActive, UserRole role) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.givenName = givenName;
        this.surname = surname;
        this.isActive = isActive;
        this.role = role;
    }
  //I am not sure what the purpose of this constructor is.
  /*  public User(String username, String email, String password, String givenName, String surname) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.givenName = givenName;
        this.surname = surname;
    }*/

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", givenName='" + givenName + '\'' +
                ", surname='" + surname + '\'' +
                ", isActive=" + isActive +
                ", role=" + role +
                '}';
    }


}
