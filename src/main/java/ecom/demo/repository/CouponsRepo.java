package ecom.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ecom.demo.models.Coupons;

@Repository
public class CouponsRepo {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CouponsRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Method to add a new coupon
    public int addCoupon(Coupons coupon) {
        String sql = "INSERT INTO Coupons (couponID, usageLimit, couponCode, validFrom, validTo, discount) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            return jdbcTemplate.update(sql,
                coupon.getCouponID().toString(),
                coupon.getUsageLimit(),
                coupon.getCouponCode(),
                coupon.getValidFrom(),
                coupon.getValidTo(),
                coupon.getDiscount());
        } catch (Exception e) {
            System.out.println("Error adding coupon: " + e.getMessage());
            return 0;
        }
    }

    // Method to get a coupon by ID
    public Coupons getCouponById(UUID couponID) {
        String sql = "SELECT * FROM Coupons WHERE couponID = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Coupons.class), couponID.toString());
        } catch (Exception e) {
            System.out.println("Error fetching coupon by ID: " + e.getMessage());
            return null;
        }
    }

    // Method to get all coupons
    public List<Coupons> getAllCoupons() {
        String sql = "SELECT * FROM Coupons";
        try {
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Coupons.class));
        } catch (Exception e) {
            System.out.println("Error fetching all coupons: " + e.getMessage());
            return List.of(); // Return empty list in case of error
        }
    }

    // Method to update a coupon
    public int updateCoupon(Coupons coupon) {
        String sql = "UPDATE Coupons SET usageLimit = ?, couponCode = ?, validFrom = ?, validTo = ?, discount = ? WHERE couponID = ?";
        try {
            return jdbcTemplate.update(sql,
                coupon.getUsageLimit(),
                coupon.getCouponCode(),
                coupon.getValidFrom(),
                coupon.getValidTo(),
                coupon.getDiscount(),
                coupon.getCouponID().toString());
        } catch (Exception e) {
            System.out.println("Error updating coupon: " + e.getMessage());
            return 0;
        }
    }

    // Method to delete a coupon by ID
    public int deleteCoupon(UUID couponID) {
        String sql = "DELETE FROM Coupons WHERE couponID = ?";
        try {
            return jdbcTemplate.update(sql, couponID.toString());
        } catch (Exception e) {
            System.out.println("Error deleting coupon: " + e.getMessage());
            return 0;
        }
    }
}
