package ecom.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ecom.demo.models.Notifications;

@Repository
public class NotificationsRepo {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public NotificationsRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Method to add a new notification
    public int addNotification(Notifications notification) {
        String sql = "INSERT INTO Notifications (notificationsID, userID, message) VALUES (?, ?, ?)";
        try {
            return jdbcTemplate.update(sql, 
                notification.getNotificationsID().toString(), 
                notification.getUserID().toString(), 
                notification.getMessage());
        } catch (Exception e) {
            System.out.println("Error adding notification: " + e.getMessage());
            return 0;
        }
    }

    // Method to get a notification by ID
    public Notifications getNotificationById(UUID notificationsID) {
        String sql = "SELECT * FROM Notifications WHERE notificationsID = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Notifications.class), notificationsID.toString());
        } catch (Exception e) {
            System.out.println("Error fetching notification by ID: " + e.getMessage());
            return null;
        }
    }

    // Method to get all notifications for a specific user
    public List<Notifications> getAllNotificationsByUserId(UUID userID) {
        String sql = "SELECT * FROM Notifications WHERE userID = ?";
        try {
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Notifications.class), userID.toString());
        } catch (Exception e) {
            System.out.println("Error fetching notifications by userID: " + e.getMessage());
            return List.of();
        }
    }

    // Method to update a notification
    public int updateNotification(Notifications notification) {
        String sql = "UPDATE Notifications SET message = ? WHERE notificationsID = ?";
        try {
            return jdbcTemplate.update(sql, 
                notification.getMessage(), 
                notification.getNotificationsID().toString());
        } catch (Exception e) {
            System.out.println("Error updating notification: " + e.getMessage());
            return 0;
        }
    }

    // Method to delete a notification by ID
    public int deleteNotification(UUID notificationsID) {
        String sql = "DELETE FROM Notifications WHERE notificationsID = ?";
        try {
            return jdbcTemplate.update(sql, notificationsID.toString());
        } catch (Exception e) {
            System.out.println("Error deleting notification: " + e.getMessage());
            return 0;
        }
    }
}
