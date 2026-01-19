package com.university.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Instructor extends User implements Serializable {
    private String instructorId;
    private String department;

    // ===== Add this field =====
    private List<String> coursesTaught = new ArrayList<>();

    public Instructor(String instructorId, String username, String password, String email, String fullName, String department) {
        super(username, password, email, fullName);
        this.instructorId = instructorId;
        this.department = department;
    }

    public String getInstructorId() { return instructorId; }
    public String getDepartment() { return department; }

    // ===== Add these methods =====
    public List<String> getCoursesTaught() {
        return new ArrayList<>(coursesTaught); // return a copy to prevent external modification
    }

    public void addCourseTaught(String courseCode) {
        if (!coursesTaught.contains(courseCode)) {
            coursesTaught.add(courseCode);
        }
    }

    public void removeCourseTaught(String courseCode) {
        coursesTaught.remove(courseCode);
    }

    @Override
    public String getRole() { return "INSTRUCTOR"; }

    @Override
    public void displayInfo() {
        System.out.println("Instructor: " + getFullName() + " | Department: " + department);
    }
}
