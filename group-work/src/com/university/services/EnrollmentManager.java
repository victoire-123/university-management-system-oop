package com.university.services;

import com.university.models.*;
import com.university.repositories.DataRepository;
import com.university.notifications.NotificationService;
import com.university.notifications.InAppNotification;
import java.time.Instant;
import java.util.UUID;

/**
 * Basic EnrollmentManager implementing EnrollmentService with essential checks.
 */
public class EnrollmentManager implements EnrollmentService {
    private DataRepository repository;
    private NotificationService notifier;
    private static final int MAX_CREDITS = 18;

    public EnrollmentManager(DataRepository repo, NotificationService notifier) {
        this.repository = repo;
        this.notifier = notifier;
    }

    @Override
    public Enrollment enrollStudent(String studentId, String courseCode, String semester) {
        Course course = repository.getCourseByCode(courseCode);
        User user = repository.getUserById(studentId);
        if (course == null || user == null || !(user instanceof Student)) return null;
        Student student = (Student) user;

        if (course.isFull()) return null;
        // check prerequisites
        for (String pre : course.getPrerequisites()) {
            if (!student.hasCompletedCourse(pre)) return null;
        }
        // credit limit (simple)
        int additionalCredits = course.getCredits();
        if (!checkCreditLimit(studentId, additionalCredits)) return null;

        // enroll
        String enrollmentId = UUID.randomUUID().toString();
        Enrollment e = new Enrollment(enrollmentId, studentId, courseCode, semester);
        if (!repository.saveEnrollment(e)) return null;
        course.addStudent(studentId);
        repository.updateCourse(course);

        // notify
        if (notifier != null) {
            notifier.notifyEnrollment(studentId, courseCode);
        }
        return e;
    }

    @Override
    public boolean dropCourse(String studentId, String courseCode) {
        Enrollment[] enrollments = repository.getEnrollmentsByStudent(studentId);
        for (Enrollment e : enrollments) {
            if (e.getCourseCode().equals(courseCode) && e.getStatus().equals("ACTIVE")) {
                e.setStatus("DROPPED");
                repository.updateEnrollment(e);
                Course course = repository.getCourseByCode(courseCode);
                if (course != null) {
                    course.removeStudent(studentId);
                    repository.updateCourse(course);
                }
                if (notifier != null) notifier.notifyCourseDropped(studentId, courseCode);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkPrerequisites(String studentId, String courseCode) {
        Course course = repository.getCourseByCode(courseCode);
        User user = repository.getUserById(studentId);
        if (course == null || user == null || !(user instanceof Student)) return false;
        Student s = (Student) user;
        for (String pre : course.getPrerequisites()) {
            if (!s.hasCompletedCourse(pre)) return false;
        }
        return true;
    }

    @Override
    public boolean checkCreditLimit(String studentId, int additionalCredits) {
        // naive: count credits from ACTIVE enrollments
        Enrollment[] enrollments = repository.getEnrollmentsByStudent(studentId);
        int credits = 0;
        for (Enrollment e : enrollments) {
            if ("ACTIVE".equals(e.getStatus())) {
                Course c = repository.getCourseByCode(e.getCourseCode());
                if (c != null) credits += c.getCredits();
            }
        }
        return (credits + additionalCredits) <= MAX_CREDITS;
    }

    @Override
    public Enrollment[] getStudentEnrollments(String studentId) {
        return repository.getEnrollmentsByStudent(studentId);
    }

    @Override
    public Enrollment[] getCourseEnrollments(String courseCode) {
        return repository.getEnrollmentsByCourse(courseCode);
    }
}