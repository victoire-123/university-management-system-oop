package com.university.services;

import com.university.models.User;
import com.university.models.Admin;
import com.university.models.Instructor;
import com.university.models.Student;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private final String FILE_PATH = "data/users.ser";
    private List<User> users;

    public UserManager() {
        users = new ArrayList<>();
    }

    public void addUser(User user) {
        users.add(user);
        saveUsers();
    }

    public boolean removeUser(User user) {
        if (user != null && users.contains(user)) {
            users.remove(user);
            saveUsers();
            return true;
        }
        return false;
    }

    public List<User> getAllUsers() { return users; }

    public User findByUsername(String username) {
        return users.stream().filter(u -> u.getUsername().equals(username)).findFirst().orElse(null);
    }

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        for (User u : users) if (u instanceof Student) students.add((Student) u);
        return students;
    }

    public List<Instructor> getAllInstructors() {
        List<Instructor> instructors = new ArrayList<>();
        for (User u : users) if (u instanceof Instructor) instructors.add((Instructor) u);
        return instructors;
    }

    // ===== Persistence =====
    public void saveUsers() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            out.writeObject(users);
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }

    public void loadUsers() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return;

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            users = (List<User>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
        
    }
    public void displayAllUsers() {
    if (users.isEmpty()) {
        System.out.println("No users found.");
        return;
    }
    for (User u : users) {
        System.out.println(u.getRole() + ": " + u.getFullName() + " | Username: " + u.getUsername());
    }
}
}
