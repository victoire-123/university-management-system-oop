package com.university.notifications;

public class InAppNotification extends Notification {

    public InAppNotification(String notificationId, String recipientId, String subject, String message, String timestamp) {
        super(notificationId, recipientId, subject, message, timestamp);
    }

    @Override
    public boolean send() {
        System.out.println("[In-App] To: " + getRecipient() + " | " + getSubject() + ": " + getMessage());
        return true;
    }

    @Override
    public String getNotificationType() {
        return "IN_APP";
    }

    @Override
    public String formatMessage() {
        return getSubject() + ": " + getMessage();
    }
}