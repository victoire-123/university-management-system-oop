package com.university.models;

public class Admin extends User {
    private String adminId;
    private String department;
    private String[] permissions;

    public Admin(String userId, String username, String password, String email, String fullName,
                 String adminId, String department, String[] permissions) {
        super(userId, username, password, email, fullName);
        this.adminId = adminId;
        this.department = department;
        this.permissions = permissions == null ? new String[0] : permissions;
    }

    public String getAdminId() {
        return adminId;
    }

    public String getDepartment() {
        return department;
    }

    public String[] getPermissions() {
        return permissions.clone();
    }

    @Override
    public void displayInfo() {
        System.out.println("\n====== ADMIN ======");
        System.out.println("Admin ID: " + adminId);
        System.out.println("Name: " + getFullName());
        System.out.println("Email: " + getEmail());
        System.out.println("Department: " + department);
        System.out.println("===================\n");
    }

    @Override
    public String getRole() {
        return "ADMIN";
    }

    @Override
    public String toString() {
        return "Admin{" +
                "adminId='" + adminId + '\'' +
                ", fullName='" + getFullName() + '\'' +
                '}';
    }
}