package ecom.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ecom.demo.models.CouponUsability;

@Repository
public class CouponUsabilityRepo {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CouponUsabilityRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Method to add a new CouponUsability entry
    public int addCouponUsability(CouponUsability couponUsability) {
        String sql = "INSERT INTO CouponUsability (couponID, productID) VALUES (?, ?)";
        try {
            return jdbcTemplate.update(sql, 
                couponUsability.getCouponID().toString(), 
                couponUsability.getProductID().toString());
        } catch (Exception e) {
            System.out.println("Error adding coupon usability: " + e.getMessage());
            return 0;
        }
    }

    // Method to get CouponUsability entry by couponID
    public List<CouponUsability> getCouponUsabilityByCouponId(UUID couponID) {
        String sql = "SELECT * FROM CouponUsability WHERE couponID = ?";
        try {
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CouponUsability.class), couponID.toString());
        } catch (Exception e) {
            System.out.println("Error fetching coupon usability by couponID: " + e.getMessage());
            return List.of();
        }
    }

    // Method to get all CouponUsability entries
    public List<CouponUsability> getAllCouponUsability() {
        String sql = "SELECT * FROM CouponUsability";
        try {
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CouponUsability.class));
        } catch (Exception e) {
            System.out.println("Error fetching all coupon usability entries: " + e.getMessage());
            return List.of();
        }
    }

    // Method to delete a CouponUsability entry
    public int deleteCouponUsability(UUID couponID, UUID productID) {
        String sql = "DELETE FROM CouponUsability WHERE couponID = ? AND productID = ?";
        try {
            return jdbcTemplate.update(sql, couponID.toString(), productID.toString());
        } catch (Exception e) {
            System.out.println("Error deleting coupon usability entry: " + e.getMessage());
            return 0;
        }
    }
}
