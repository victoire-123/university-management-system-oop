package com.university.auth;

import com.university.models.User;
import com.university.repositories.DataRepository;

public class AuthenticationService {
    private User currentUser;
    private DataRepository repository;

    public AuthenticationService(DataRepository repository) {
        this.repository = repository;
        this.currentUser = null;
    }

    public User login(String username, String password) {
        if (username == null || password == null) return null;
        User[] users = repository.getAllUsers();
        for (User u : users) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                this.currentUser = u;
                return u;
            }
        }
        return null;
    }

    public void logout() {
        this.currentUser = null;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public boolean hasRole(String role) {
        if (currentUser == null) return false;
        return currentUser.getRole().equals(role);
    }

    public boolean canPerformAction(String action) {
        if (!isLoggedIn()) return false;
        String role = currentUser.getRole();
        switch (action) {
            case "ENROLL_STUDENT":
                return role.equals("STUDENT") || role.equals("ADMIN");
            case "ASSIGN_GRADE":
                return role.equals("INSTRUCTOR") || role.equals("ADMIN");
            case "MANAGE_USERS":
                return role.equals("ADMIN");
            case "VIEW_TRANSCRIPT":
                return true;
            default:
                return false;
        }
    }
}