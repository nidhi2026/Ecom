package ecom.demo.service;

import ecom.demo.models.Notification;
import ecom.demo.repository.NotificationRepo;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class NotificationService {
    private final NotificationRepo notificationRepo;

    public NotificationService(NotificationRepo notificationRepo) {
        this.notificationRepo = notificationRepo;
    }

    // Add a new notification
    public void addNotification(Notification notification) {
        notification.setNotificationsID(UUID.randomUUID());
        notificationRepo.addNotification(notification);
    }

    // Get all notifications
    public List<Notification> getAllNotifications() {
        return notificationRepo.getAllNotifications();
    }

    // Update an existing notification
    public void updateNotification(String notificationsID, String message) {
        notificationRepo.updateNotification(notificationsID, message);
    }

    // Delete a notification by ID
    public void deleteNotification(UUID notificationsID) {
        notificationRepo.deleteNotification(notificationsID);
    }
}