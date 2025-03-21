package ecom.demo.repository;

import java.time.LocalDate;
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
    public Coupons getCouponByID(UUID couponID) {
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
            return List.of();
        }
    }

    public Coupons getDiscount(Float totalAmount) {
        System.out.println("amount "+ totalAmount);
        String sql = "SELECT * FROM Coupons WHERE validFrom <= CURDATE() AND validTo >= CURDATE() AND low <= ? AND high >= ? AND usageLimit > 0 ORDER BY discount DESC LIMIT 1";
        try {
            // Use queryForObject to get the coupon with the highest discount
            Coupons coupon = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Coupons.class), totalAmount, totalAmount);
            
            // Return the coupon if found
            return coupon;
        } catch (Exception e) {
            System.out.println("Error fetching coupon by amount: " + e.getMessage());
            return null;
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

    // Method to get coupons associated with specific product IDs
    public List<Coupons> findCouponsByProductIds(List<UUID> productIds) {
        String sql = "SELECT DISTINCT c.* FROM Coupons c " +
                    "JOIN CouponUsability cu ON c.couponID = cu.couponID " +
                    "WHERE cu.productID IN (" +
                    String.join(",", productIds.stream().map(id -> "?").toArray(String[]::new)) + ") " +
                    "AND c.validFrom <= ? AND c.validTo >= ? " +
                    "AND c.usageLimit > 0"; // Usable coupon check

        try {
            LocalDate currentDate = LocalDate.now();

            Object[] params = new Object[productIds.size() + 2];
            for (int i = 0; i < productIds.size(); i++) {
                params[i] = productIds.get(i).toString();
            }
            params[productIds.size()] = currentDate;
            params[productIds.size() + 1] = currentDate;

            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Coupons.class), params);
        } catch (Exception e) {
            System.out.println("Error fetching valid and usable coupons by product IDs: " + e.getMessage());
            return List.of();
        }
    }

}