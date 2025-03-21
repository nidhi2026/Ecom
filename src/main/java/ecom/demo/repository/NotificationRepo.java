package ecom.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ecom.demo.models.Notification;

@Repository
public class NotificationRepo {
    private final JdbcTemplate jdbcTemplate;

    public NotificationRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Add a notification
    public int addNotification(Notification notification) {
        String sql = "INSERT INTO notifications (notificationsID, message, timestamp) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, notification.getNotificationsID().toString(), notification.getMessage(), notification.getTimestamp());
    }

    // Get all notifications
    public List<Notification> getAllNotifications() {
        String sql = "SELECT * FROM notifications";
        return jdbcTemplate.query(sql, new NotificationRowMapper());
    }

    // Update notification
    public int updateNotification(String notificationsID, String message) {
        String sql = "UPDATE notifications SET message = ? WHERE notificationsID = ?";
        return jdbcTemplate.update(sql, message, notificationsID);
    }

    // Delete notification by ID
    public int deleteNotification(UUID notificationsID) {
        String sql = "DELETE FROM notifications WHERE notificationsID = ?";
        try {
            // Ensure that the notificationsID is being passed as a string correctly
            return jdbcTemplate.update(sql, notificationsID.toString());
        } catch (Exception e) {
            System.out.println("Error deleting notification: " + e.getMessage());
            return 0;
        }
    }    
}