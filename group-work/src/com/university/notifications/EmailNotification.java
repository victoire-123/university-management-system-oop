package com.university.notifications;

public class EmailNotification extends Notification {
    public EmailNotification(String notificationId, String recipient, String subject, String message, String timestamp) {
        super(notificationId, recipient, subject, message, timestamp);
    }

    @Override
    public boolean send() {
        System.out.println("[EMAIL] To: " + recipient + " | Subject: " + subject + " | " + message);
        return true;
    }

    @Override
    public String getNotificationType() {
        return "EMAIL";
    }

    @Override
    public String formatMessage() {
        return "Email to " + recipient + ": " + subject + "\n" + message;
    }
}