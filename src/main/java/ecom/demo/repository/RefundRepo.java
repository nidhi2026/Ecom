package ecom.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ecom.demo.models.Refund;

@Repository
public class RefundRepo {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RefundRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int addRefund(Refund refund) {
        String sql = "INSERT INTO Refund (refundID, refundTime, reason, amount, orderID, productID) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            return jdbcTemplate.update(sql, refund.getRefundID().toString(), refund.getRefundTime(), refund.getReason(), refund.getAmount(), refund.getOrderID().toString(), refund.getProductID().toString());
        } catch (Exception e) {
            System.out.println("Error adding refund: " + e.getMessage());
            return 0;
        }
    }

    public Refund getRefundById(UUID refundID) {
        String sql = "SELECT * FROM Refund WHERE refundID = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Refund.class), refundID.toString());
        } catch (Exception e) {
            System.out.println("Error fetching refund by ID: " + e.getMessage());
            return null;
        }
    }

    public boolean isProductRefunded(UUID orderID, UUID productID) {
        String sql = "SELECT COUNT(*) FROM Refund WHERE orderID = ? AND productID = ?";
        try {
            // Fetch count of refunds for this orderID and productID
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class, orderID.toString(), productID.toString());
            // If count is greater than 0, it means the product has been refunded
            return count != null && count > 0;
        } catch (Exception e) {
            System.out.println("Error fetching refund status: " + e.getMessage());
            return false;
        }
    }
    

    public List<Refund> getAllRefunds() {
        String sql = "SELECT * FROM Refund";
        try {
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Refund.class));
        } catch (Exception e) {
            System.out.println("Error fetching all refunds: " + e.getMessage());
            return null;
        }
    }

    public int updateRefund(Refund refund) {
        String sql = "UPDATE Refund SET refundTime = ?, reason = ?, amount = ?, orderID = ? WHERE refundID = ?";
        try {
            return jdbcTemplate.update(sql, refund.getRefundTime(), refund.getReason(), refund.getAmount(), refund.getOrderID().toString(), refund.getRefundID().toString());
        } catch (Exception e) {
            System.out.println("Error updating refund: " + e.getMessage());
            return 0;
        }
    }

    public int deleteRefund(UUID refundID) {
        String sql = "DELETE FROM Refund WHERE refundID = ?";
        try {
            return jdbcTemplate.update(sql, refundID.toString());
        } catch (Exception e) {
            System.out.println("Error deleting refund: " + e.getMessage());
            return 0;
        }
    }

}
