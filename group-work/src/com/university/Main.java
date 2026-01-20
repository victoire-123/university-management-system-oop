package com.university;

import com.university.auth.AuthenticationService;
import com.university.notifications.NotificationManager;
import com.university.notifications.NotificationService;
import com.university.repositories.InMemoryRepository;
import com.university.repositories.DataRepository;
import com.university.services.EnrollmentManager;
import com.university.services.EnrollmentService;
import com.university.services.GradeManager;
import com.university.models.*;

import java.util.Scanner;

/**
 * Main entry point for the University Management System.
 * Bootstraps in-memory data and provides minimal menus for demonstration.
 */
public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static AuthenticationService authService;
    private static DataRepository repo;
    private static EnrollmentService enrollmentManager;
    private static GradeManager gradeManager;
    private static NotificationService notifier;

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("University Course Management System");
        System.out.println("========================================\n");

        // Initialize repository and services
        repo = new InMemoryRepository();
        notifier = new NotificationManager();
        enrollmentManager = new EnrollmentManager(repo, notifier);
        gradeManager = new GradeManager(repo, notifier);

        // Seed sample data
        seedSampleData();

        // Authentication service that uses repository
        authService = new AuthenticationService(repo);

        while (true) {
            if (!authService.isLoggedIn()) {
                showLoginMenu();
            } else {
                String role = authService.getCurrentUser().getRole();
                switch (role) {
                    case "ADMIN":
                        showAdminMenu();
                        break;
                    case "INSTRUCTOR":
                        showInstructorMenu();
                        break;
                    case "STUDENT":
                        showStudentMenu();
                        break;
                    default:
                        System.out.println("Unknown role. Logging out.");
                        authService.logout();
                }
            }
        }
    }

    private static void seedSampleData() {
        Student s = new Student("u-student1","student1","student123","s1@example.com","Student One","s1","CS",1);
        Instructor p = new Instructor("u-prof1","prof1","prof123","p1@example.com","Prof One","i1","CS","Tues 2-4");
        Admin a = new Admin("u-admin","admin","admin123","a@example.com","Admin","adm1","IT", new String[]{"ALL"});

        repo.saveUser(s); repo.saveUser(p); repo.saveUser(a);

        Course c = new Course("CS101","Intro to Programming",3,"i1",30);
        repo.saveCourse(c);

        // Link the course to the instructor so the "View My Courses" feature works.
        p.addCourse(c.getCourseCode());
    }

    private static void showLoginMenu() {
        System.out.println("\n=== Login Menu ===");
        System.out.println("1. Login");
        System.out.println("2. Exit");
        System.out.print("Choose an option: ");

        int choice = getIntInput();

        switch (choice) {
            case 1:
                performLogin();
                break;
            case 2:
                System.out.println("Goodbye!");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    private static void performLogin() {
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();

        User user = authService.login(username, password);
        if (user != null) {
            System.out.println("Welcome, " + user.getFullName() + " (" + user.getRole() + ")");
        } else {
            System.out.println("Invalid credentials. Try again.");
        }
    }

    private static void showAdminMenu() {
        System.out.println("\n=== Admin Menu ===");
        System.out.println("1. View All Users");
        System.out.println("2. Logout");
        System.out.print("Choose an option: ");

        int choice = getIntInput();

        switch (choice) {
            case 1:
                for (User u : repo.getAllUsers()) {
                    System.out.println(u);
                }
                break;
            case 2:
                authService.logout();
                System.out.println("Logged out successfully.");
                break;
            default:
                System.out.println("Feature not yet implemented.");
        }
    }

    private static void showInstructorMenu() {
        System.out.println("\n=== Instructor Menu ===");
        System.out.println("1. View My Courses");
        System.out.println("2. Logout");
        System.out.print("Choose an option: ");

        int choice = getIntInput();

        switch (choice) {
            case 1:
                User cur = authService.getCurrentUser();
                if (cur instanceof Instructor) {
                    Instructor ins = (Instructor) cur;
                    for (String code : ins.getCoursesTaught()) {
                        Course c = repo.getCourseByCode(code);
                        System.out.println(c);
                    }
                } else {
                    System.out.println("No courses assigned.");
                }
                break;
            case 2:
                authService.logout();
                System.out.println("Logged out successfully.");
                break;
            default:
                System.out.println("Feature not yet implemented.");
        }
    }

    private static void showStudentMenu() {
        System.out.println("\n=== Student Menu ===");
        System.out.println("1. View Available Courses");
        System.out.println("2. Enroll in Course");
        System.out.println("3. View My Enrollments");
        System.out.println("4. View My Grades");
        System.out.println("5. Logout");
        System.out.print("Choose an option: ");

        int choice = getIntInput();

        switch (choice) {
            case 1:
                for (Course c : repo.getAllCourses()) {
                    System.out.println(c);
                }
                break;
            case 2:
                System.out.print("Course Code to enroll: ");
                String code = scanner.nextLine().trim();
                User u = authService.getCurrentUser();
                if (u instanceof Student) {
                    Enrollment e = enrollmentManager.enrollStudent(u.getUserId(), code, "Fall2024");
                    if (e != null) {
                        System.out.println("Enrolled successfully: " + e);
                    } else {
                        System.out.println("Enrollment failed (capacity/prereq/limit).");
                    }
                } else {
                    System.out.println("Only students can enroll.");
                }
                break;
            case 3:
                User cur = authService.getCurrentUser();
                if (cur != null) {
                    Enrollment[] es = enrollmentManager.getStudentEnrollments(cur.getUserId());
                    for (Enrollment en : es) System.out.println(en);
                }
                break;
            case 4:
                User cur2 = authService.getCurrentUser();
                if (cur2 != null) {
                    Grade[] grades = gradeManager.getGradesForStudent(cur2.getUserId());
                    for (Grade g : grades) System.out.println(g);
                }
                break;
            case 5:
                authService.logout();
                System.out.println("Logged out successfully.");
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    private static int getIntInput() {
        try {
            int value = Integer.parseInt(scanner.nextLine());
            return value;
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
