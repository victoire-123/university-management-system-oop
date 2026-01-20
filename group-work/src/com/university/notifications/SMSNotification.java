package com.university.notifications;

public class SMSNotification extends Notification {
    public SMSNotification(String notificationId, String recipient, String subject, String message, String timestamp) {
        super(notificationId, recipient, subject, message, timestamp);
    }

    @Override
    public boolean send() {
        System.out.println("[SMS] To: " + recipient + " | " + message);
        return true;
    }

    @Override
    public String getNotificationType() {
        return "SMS";
    }

    @Override
    public String formatMessage() {
        return "SMS to " + recipient + ": " + message;
    }
}