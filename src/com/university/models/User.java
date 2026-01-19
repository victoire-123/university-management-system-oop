package com.university.models;

import java.io.Serializable;

public abstract class User implements Serializable {
    private String username;
    private String password;
    private String email;
    private String fullName;

    public User(String username, String password, String email, String fullName) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
    }

    public String getUsername() { return username; }
    public String getFullName() { return fullName; }

    public boolean checkPassword(String password) { return this.password.equals(password); }

    public abstract String getRole();
    public abstract void displayInfo();
}
