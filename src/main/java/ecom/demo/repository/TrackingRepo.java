package ecom.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ecom.demo.models.Tracking;

@Repository
public class TrackingRepo {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TrackingRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int addTracking(Tracking tracking) {
        String sql = "INSERT INTO Trackings (trackingID, orderID, trackingStatus) VALUES (?, ?, ?)";
        try {
            return jdbcTemplate.update(sql, tracking.getTrackingID().toString(), tracking.getOrderID().toString(), tracking.getTrackingStatus().name());
        } catch (Exception e) {
            System.out.println("Error adding tracking: " + e.getMessage());
            return 0;
        }
    }

    public Tracking getTrackingById(UUID trackingID) {
        String sql = "SELECT * FROM Trackings WHERE trackingID = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Tracking.class), trackingID.toString());
        } catch (Exception e) {
            System.out.println("Error fetching tracking by ID: " + e.getMessage());
            return null;
        }
    }

    public List<Tracking> getAllTrackings() {
        String sql = "SELECT * FROM Trackings";
        try {
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Tracking.class));
        } catch (Exception e) {
            System.out.println("Error fetching all trackings: " + e.getMessage());
            return null;
        }
    }

    public int updateTracking(Tracking tracking) {
        String sql = "UPDATE Trackings SET orderID = ?, trackingStatus = ? WHERE trackingID = ?";
        try {
            return jdbcTemplate.update(sql, tracking.getOrderID().toString(), tracking.getTrackingStatus().toString(), tracking.getTrackingID().toString());
        } catch (Exception e) {
            System.out.println("Error updating tracking: " + e.getMessage());
            return 0;
        }
    }

    public int deleteTracking(UUID trackingID) {
        String sql = "DELETE FROM Trackings WHERE trackingID = ?";
        try {
            return jdbcTemplate.update(sql, trackingID.toString());
        } catch (Exception e) {
            System.out.println("Error deleting tracking: " + e.getMessage());
            return 0;
        }
    }
}
