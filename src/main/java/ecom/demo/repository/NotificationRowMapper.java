package ecom.demo.repository;

import org.springframework.jdbc.core.RowMapper;
import ecom.demo.models.Notification;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.UUID;

public class NotificationRowMapper implements RowMapper<Notification> {
    @Override
    public Notification mapRow(ResultSet rs, int rowNum) throws SQLException {
        Notification notification = new Notification();
        notification.setNotificationsID(UUID.fromString(rs.getString("notificationsID"))); // Convert String to UUID
        notification.setMessage(rs.getString("message"));
        notification.setTimestamp(rs.getTimestamp("timestamp").toLocalDateTime());
        return notification;
    }
}