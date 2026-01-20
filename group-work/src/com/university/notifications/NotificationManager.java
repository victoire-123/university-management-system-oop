package com.university.notifications;

import java.time.Instant;
import java.util.UUID;

public class NotificationManager implements NotificationService {
    @Override
    public boolean sendNotification(Notification notification) {
        if (notification == null) return false;
        return notification.send();
    }

    @Override
    public void notifyEnrollment(String studentId, String courseCode) {
        String id = UUID.randomUUID().toString();
        String time = Instant.now().toString();
        Notification n = new InAppNotification(id, studentId, "Enrollment Confirmed",
                "You have been enrolled in " + courseCode, time);
        sendNotification(n);
    }

    @Override
    public void notifyGradePosted(String studentId, String courseCode, String grade) {
        String id = UUID.randomUUID().toString();
        String time = Instant.now().toString();
        Notification n = new InAppNotification(id, studentId, "Grade Posted",
                "Your grade for " + courseCode + " is " + grade, time);
        sendNotification(n);
    }

    @Override
    public void notifyCourseDropped(String studentId, String courseCode) {
        String id = UUID.randomUUID().toString();
        String time = Instant.now().toString();
        Notification n = new InAppNotification(id, studentId, "Course Dropped",
                "You have been dropped from " + courseCode, time);
        sendNotification(n);
    }

    @Override
    public void sendAnnouncement(String[] recipientIds, String subject, String message) {
        String time = Instant.now().toString();
        for (String r : recipientIds) {
            String id = UUID.randomUUID().toString();
            Notification n = new InAppNotification(id, r, subject, message, time);
            sendNotification(n);
        }
    }
}