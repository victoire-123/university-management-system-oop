package com.university.repositories;

import com.university.models.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Simple in-memory repository implementing DataRepository.
 * Useful for development/testing before adding file persistence.
 */
public class InMemoryRepository implements DataRepository {
    private Map<String, User> users = new ConcurrentHashMap<>();
    private Map<String, Course> courses = new ConcurrentHashMap<>();
    private Map<String, Enrollment> enrollments = new ConcurrentHashMap<>();
    private Map<String, Grade> grades = new ConcurrentHashMap<>();

    // Users
    @Override
    public boolean saveUser(User user) {
        if (user == null || user.getUserId() == null) return false;
        users.put(user.getUserId(), user);
        return true;
    }

    @Override
    public User getUserById(String userId) {
        return users.get(userId);
    }

    @Override
    public User[] getAllUsers() {
        return users.values().toArray(new User[0]);
    }

    @Override
    public boolean updateUser(User user) {
        if (user == null || user.getUserId() == null) return false;
        if (!users.containsKey(user.getUserId())) return false;
        users.put(user.getUserId(), user);
        return true;
    }

    @Override
    public boolean deleteUser(String userId) {
        return users.remove(userId) != null;
    }

    // Courses
    @Override
    public boolean saveCourse(Course course) {
        if (course == null || course.getCourseCode() == null) return false;
        courses.put(course.getCourseCode(), course);
        return true;
    }

    @Override
    public Course getCourseByCode(String courseCode) {
        return courses.get(courseCode);
    }

    @Override
    public Course[] getAllCourses() {
        return courses.values().toArray(new Course[0]);
    }

    @Override
    public boolean updateCourse(Course course) {
        if (course == null || course.getCourseCode() == null) return false;
        if (!courses.containsKey(course.getCourseCode())) return false;
        courses.put(course.getCourseCode(), course);
        return true;
    }

    @Override
    public boolean deleteCourse(String courseCode) {
        return courses.remove(courseCode) != null;
    }

    // Enrollments
    @Override
    public boolean saveEnrollment(Enrollment enrollment) {
        if (enrollment == null || enrollment.getEnrollmentId() == null) return false;
        enrollments.put(enrollment.getEnrollmentId(), enrollment);
        return true;
    }

    @Override
    public Enrollment getEnrollmentById(String enrollmentId) {
        return enrollments.get(enrollmentId);
    }

    @Override
    public Enrollment[] getEnrollmentsByStudent(String studentId) {
        return enrollments.values().stream()
                .filter(e -> e.getStudentId().equals(studentId))
                .toArray(Enrollment[]::new);
    }

    @Override
    public Enrollment[] getEnrollmentsByCourse(String courseCode) {
        return enrollments.values().stream()
                .filter(e -> e.getCourseCode().equals(courseCode))
                .toArray(Enrollment[]::new);
    }

    @Override
    public boolean updateEnrollment(Enrollment enrollment) {
        if (enrollment == null || enrollment.getEnrollmentId() == null) return false;
        if (!enrollments.containsKey(enrollment.getEnrollmentId())) return false;
        enrollments.put(enrollment.getEnrollmentId(), enrollment);
        return true;
    }

    @Override
    public boolean deleteEnrollment(String enrollmentId) {
        return enrollments.remove(enrollmentId) != null;
    }

    // Grades
    @Override
    public boolean saveGrade(Grade grade) {
        if (grade == null || grade.getGradeId() == null) return false;
        grades.put(grade.getGradeId(), grade);
        return true;
    }

    @Override
    public Grade getGradeById(String gradeId) {
        return grades.get(gradeId);
    }

    @Override
    public Grade[] getGradesByStudent(String studentId) {
        return grades.values().stream()
                .filter(g -> g.getStudentId().equals(studentId))
                .toArray(Grade[]::new);
    }

    @Override
    public Grade[] getGradesByCourse(String courseCode) {
        return grades.values().stream()
                .filter(g -> g.getCourseCode().equals(courseCode))
                .toArray(Grade[]::new);
    }

    @Override
    public boolean updateGrade(Grade grade) {
        if (grade == null || grade.getGradeId() == null) return false;
        if (!grades.containsKey(grade.getGradeId())) return false;
        grades.put(grade.getGradeId(), grade);
        return true;
    }

    @Override
    public boolean deleteGrade(String gradeId) {
        return grades.remove(gradeId) != null;
    }

    // Utility operations (no-op for in-memory)
    @Override
    public boolean loadAllData() {
        return true;
    }

    @Override
    public boolean saveAllData() {
        return true;
    }

    @Override
    public void clearData() {
        users.clear();
        courses.clear();
        enrollments.clear();
        grades.clear();
    }
}