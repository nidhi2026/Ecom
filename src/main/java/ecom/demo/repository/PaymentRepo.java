package ecom.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ecom.demo.models.Payment;

@Repository
public class PaymentRepo {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PaymentRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int addPayment(Payment payment) {
        String sql = "INSERT INTO Payment (paymentID, orderID, paymentTime, paymentMode, amount) VALUES (?, ?, ?, ?, ?)";
        try {
            return jdbcTemplate.update(sql, payment.getPaymentID().toString(), payment.getOrderID().toString(), payment.getPaymentTime(), payment.getPaymentMode().name(), payment.getAmount());
        } catch (Exception e) {
            System.out.println("Error adding payment: " + e.getMessage());
            return 0;
        }
    }

    public Payment getPaymentById(UUID paymentID) {
        String sql = "SELECT * FROM Payment WHERE paymentID = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Payment.class), paymentID.toString());
        } catch (Exception e) {
            System.out.println("Error fetching payment by ID: " + e.getMessage());
            return null;
        }
    }

    public List<Payment> getAllPayments() {
        String sql = "SELECT * FROM Payment";
        try {
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Payment.class));
        } catch (Exception e) {
            System.out.println("Error fetching all payments: " + e.getMessage());
            return null;
        }
    }

    public int updatePayment(Payment payment) {
        String sql = "UPDATE Payment SET orderID = ?, paymentTime = ?, paymentMode = ? WHERE paymentID = ?";
        try {
            return jdbcTemplate.update(sql, payment.getOrderID().toString(), payment.getPaymentTime(), payment.getPaymentMode().name(), payment.getPaymentID().toString());
        } catch (Exception e) {
            System.out.println("Error updating payment: " + e.getMessage());
            return 0;
        }
    }

    public int deletePayment(UUID paymentID) {
        String sql = "DELETE FROM Payment WHERE paymentID = ?";
        try {
            return jdbcTemplate.update(sql, paymentID.toString());
        } catch (Exception e) {
            System.out.println("Error deleting payment: " + e.getMessage());
            return 0;
        }
    }
}
