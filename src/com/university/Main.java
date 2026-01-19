package com.university;

import com.university.auth.AuthenticationService;
import com.university.models.Course;
import com.university.models.Grade;
import com.university.services.*;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static AuthenticationService authService = new AuthenticationService();
    private static Scanner scanner = new Scanner(System.in);

    // Managers
    private static UserManager userManager = new UserManager();
    private static CourseManager courseManager = new CourseManager();
    private static EnrollmentManager enrollmentManager = new EnrollmentManager(courseManager);
    private static GradeManager gradeManager = new GradeManager();
    private static GPAService gpaService = new GPAService();

    public static void main(String[] args) {

        // ===== Load saved data =====
        userManager.loadUsers();
        courseManager.loadCourses();
        enrollmentManager.loadEnrollments();
        gradeManager.loadGrades();

        // ===== Preload sample users if none exist =====
        if (userManager.getAllUsers().isEmpty()) {
            userManager.addUser(new com.university.models.Admin(
                    "A001","admin1","admin123","admin@uni.com","Alice Admin","IT",List.of("ALL")));
            userManager.addUser(new com.university.models.Instructor(
                    "I001","instr1","teach123","smith@uni.com","Prof. Smith","CS"));
            userManager.addUser(new com.university.models.Student(
                    "S001","student1","1234","john@uni.com","John Doe","Software Engineering"));
            userManager.saveUsers();
        }

        // ===== Preload sample courses if none exist =====
        if (courseManager.getAllCourses().isEmpty()) {
            courseManager.addCourse(new Course("CS101","Intro to Programming",3,"I001",30,null,"CS"));
            courseManager.addCourse(new Course("MATH101","Calculus I",4,"I001",40,null,"Math"));
            courseManager.addCourse(new Course("CS201","Data Structures",3,"I001",25,List.of("CS101"),"CS"));
        }

        // ===== Main loop =====
        while (true) {
            if (!authService.isLoggedIn()) {
                loginMenu();
            } else {
                roleMenu();
            }
        }
    }

    private static void loginMenu() {
        System.out.println("\n--- Login ---");
        System.out.print("Username: ");
        String user = scanner.nextLine();
        System.out.print("Password: ");
        String pass = scanner.nextLine();

        if (!authService.login(user, pass)) {
            System.out.println("Invalid username or password!");
        }
    }

    private static void roleMenu() {
        String role = authService.getCurrentUser().getRole();
        System.out.println("\nLogged in as: " + role);
        authService.getCurrentUser().displayInfo();

        switch (role) {
            case "STUDENT" -> studentMenu();
            case "INSTRUCTOR" -> instructorMenu();
            case "ADMIN" -> adminMenu();
            default -> System.out.println("Unknown role!");
        }
    }

    // ===================== STUDENT MENU =====================
    private static void studentMenu() {
        while (true) {
            System.out.println("\n--- Student Menu ---");
            System.out.println("1. Browse Courses");
            System.out.println("2. Enroll in Course");
            System.out.println("3. Drop Course");
            System.out.println("4. View Grades / Transcript");
            System.out.println("5. Logout");

            System.out.print("Choice: ");
            String choice = scanner.nextLine();
            String studentId = authService.getCurrentUser().getUsername();

            switch (choice) {
                case "1" -> courseManager.displayAllCourses();
                case "2" -> {
                    System.out.print("Course Code to enroll: ");
                    String code = scanner.nextLine();
                    if (enrollmentManager.enroll(studentId, code)) {
                        enrollmentManager.saveEnrollments();
                        System.out.println("GPA: " + gpaService.calculateGPA(studentId, gradeManager));
                    }
                }
                case "3" -> {
                    System.out.print("Course Code to drop: ");
                    String code = scanner.nextLine();
                    if (enrollmentManager.drop(studentId, code)) {
                        enrollmentManager.saveEnrollments();
                        System.out.println("GPA: " + gpaService.calculateGPA(studentId, gradeManager));
                    }
                }
                case "4" -> gradeManager.displayStudentGrades(studentId);
                case "5" -> {
                    authService.logout();
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    // ===================== INSTRUCTOR MENU =====================
    private static void instructorMenu() {
        while (true) {
            System.out.println("\n--- Instructor Menu ---");
            System.out.println("1. View My Courses");
            System.out.println("2. View Course Roster");
            System.out.println("3. Assign Grade");
            System.out.println("4. Logout");

            System.out.print("Choice: ");
            String choice = scanner.nextLine();
            String instructorId = authService.getCurrentUser().getUsername();

            switch (choice) {
                case "1" -> courseManager.getAllCourses().stream()
                        .filter(c -> c.getInstructorId().equals(instructorId))
                        .forEach(Course::displayCourse);

                case "2" -> {
                    System.out.print("Course Code to view roster: ");
                    String code = scanner.nextLine();
                    Course course = courseManager.findByCode(code);
                    if (course != null && course.getInstructorId().equals(instructorId)) {
                        System.out.println("Enrolled Students: " + course.getEnrolledStudents());
                    } else {
                        System.out.println("Invalid course or you are not the instructor.");
                    }
                }

                case "3" -> {
                    System.out.print("Student ID: ");
                    String studentId = scanner.nextLine();
                    System.out.print("Course Code: ");
                    String courseCode = scanner.nextLine();
                    System.out.print("Score: ");
                    double score = Double.parseDouble(scanner.nextLine());

                    Course course = courseManager.findByCode(courseCode);
                    Grade grade = new Grade(
                            studentId,
                            courseCode,
                            score,
                            course != null ? course.getCredits() : 3
                    );

                    gradeManager.assignGrade(grade);
                    gradeManager.saveGrades();
                    System.out.println("GPA: " + gpaService.calculateGPA(studentId, gradeManager));
                }

                case "4" -> {
                    authService.logout();
                    return;
                }

                default -> System.out.println("Invalid choice!");
            }
        }
    }

    // ===================== ADMIN MENU =====================
    private static void adminMenu() {
        while (true) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. View Users");
            System.out.println("2. Add Course");
            System.out.println("3. Update Course");
            System.out.println("4. Delete Course");
            System.out.println("5. Logout");

            System.out.print("Choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> userManager.displayAllUsers();

                case "2" -> {
                    System.out.print("Course Code: ");
                    String code = scanner.nextLine();
                    System.out.print("Title: ");
                    String title = scanner.nextLine();
                    System.out.print("Credits: ");
                    int credits = Integer.parseInt(scanner.nextLine());
                    System.out.print("Instructor ID: ");
                    String instructorId = scanner.nextLine();
                    System.out.print("Capacity: ");
                    int capacity = Integer.parseInt(scanner.nextLine());
                    System.out.print("Department: ");
                    String department = scanner.nextLine();

                    courseManager.addCourse(new Course(code, title, credits, instructorId, capacity, null, department));
                    courseManager.saveCourses();
                    System.out.println("Course added!");
                }

                case "3" -> {
                    System.out.print("Course Code to update: ");
                    String code = scanner.nextLine();
                    System.out.print("New Title (or leave blank): ");
                    String title = scanner.nextLine();
                    System.out.print("Credits: ");
                    int credits = Integer.parseInt(scanner.nextLine());
                    System.out.print("Instructor ID: ");
                    String instructorId = scanner.nextLine();
                    System.out.print("Capacity: ");
                    int capacity = Integer.parseInt(scanner.nextLine());
                    System.out.print("Department: ");
                    String department = scanner.nextLine();

                    courseManager.updateCourse(
                            code,
                            title.isEmpty() ? null : title,
                            credits,
                            instructorId.isEmpty() ? null : instructorId,
                            capacity,
                            department.isEmpty() ? null : department
                    );
                    courseManager.saveCourses();
                    System.out.println("Course updated!");
                }

                case "4" -> {
                    System.out.print("Course Code to delete: ");
                    String code = scanner.nextLine();
                    Course course = courseManager.findByCode(code);
                    if (course != null && courseManager.removeCourse(course.getCourseCode())) {
                        System.out.println("Course deleted!");
                    } else {
                        System.out.println("Course not found.");
                    }
                }

                case "5" -> {
                    authService.logout();
                    return;
                }

                default -> System.out.println("Invalid choice!");
            }
        }
    }
}
