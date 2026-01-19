package com.university.models;


import java.io.Serializable;
import java.util.List;

public class Admin extends User implements Serializable {
    private String adminId;
    private String department;
    private List<String> permissions;

    public Admin(String adminId, String username, String password, String email, String fullName,
                 String department, List<String> permissions) {
        super(username, password, email, fullName);
        this.adminId = adminId;
        this.department = department;
        this.permissions = permissions;
    }

    public String getAdminId() { return adminId; }
    public List<String> getPermissions() { return permissions; }

    @Override
    public String getRole() { return "ADMIN"; }

    @Override
    public void displayInfo() {
        System.out.println("Admin: " + getFullName() + " | Department: " + department);
    }
}
