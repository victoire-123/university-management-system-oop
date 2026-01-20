package com.university.services;

import com.university.models.*;
import com.university.notifications.NotificationService;
import com.university.repositories.DataRepository;
import java.util.UUID;

public class GradeManager {
    private DataRepository repository;
    private NotificationService notifier;

    public GradeManager(DataRepository repo, NotificationService notifier) {
        this.repository = repo;
        this.notifier = notifier;
    }

    public Grade assignGrade(String studentId, String courseCode, double score, GradeCalculator calculator, String semester) {
        if (calculator == null || !calculator.isValidScore(score)) return null;
        String gradeStr = calculator.calculateGrade(score);
        String gradeId = UUID.randomUUID().toString();
        Grade grade = new Grade(gradeId, studentId, courseCode, score, gradeStr, semester);
        if (repository.saveGrade(grade)) {
            if (notifier != null) notifier.notifyGradePosted(studentId, courseCode, gradeStr);
            // Optionally update student's completed courses if passing for Pass/Fail or letter grade >= D
            User u = repository.getUserById(studentId);
            if (u instanceof Student) {
                Student s = (Student) u;
                if (!"FAIL".equalsIgnoreCase(gradeStr) && !"F".equalsIgnoreCase(gradeStr)) {
                    s.addCompletedCourse(courseCode);
                    repository.updateUser(s);
                }
            }
            return grade;
        }
        return null;
    }

    public Grade[] getGradesForStudent(String studentId) {
        return repository.getGradesByStudent(studentId);
    }
}