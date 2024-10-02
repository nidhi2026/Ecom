package ecom.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ecom.demo.models.CustomerCare;

@Repository
public class CustomerCareRepo {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CustomerCareRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int addCustomerCare(CustomerCare customerCare) {
        String sql = "INSERT INTO CustomerCares (customerCareID, userID, priority, issue, description, status, filedTime) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            return jdbcTemplate.update(sql, customerCare.getCustomerCareID().toString(), customerCare.getUserID().toString(), customerCare.getPriority().name(), customerCare.getIssue(), customerCare.getDescription(), customerCare.getStatus().name(), customerCare.getFiledTime());
        } catch (Exception e) {
            System.out.println("Error adding customer care: " + e.getMessage());
            return 0;
        }
    }

    public CustomerCare getCustomerCareById(UUID customerCareID) {
        String sql = "SELECT * FROM CustomerCares WHERE customerCareID = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(CustomerCare.class), customerCareID.toString());
        } catch (Exception e) {
            System.out.println("Error fetching customer care by ID: " + e.getMessage());
            return null;
        }
    }

    public List<CustomerCare> getAllCustomerCares() {
        String sql = "SELECT * FROM CustomerCares";
        try {
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CustomerCare.class));
        } catch (Exception e) {
            System.out.println("Error fetching all customer cares: " + e.getMessage());
            return null;
        }
    }

    public int updateCustomerCare(CustomerCare customerCare) {
        String sql = "UPDATE CustomerCares SET userID = ?, priority = ?, issue = ?, description = ?, status = ?, filedTime = ? WHERE customerCareID = ?";
        try {
            return jdbcTemplate.update(sql, customerCare.getUserID().toString(), customerCare.getPriority().name(), customerCare.getIssue(), customerCare.getDescription(), customerCare.getStatus().name(), customerCare.getFiledTime(), customerCare.getCustomerCareID().toString());
        } catch (Exception e) {
            System.out.println("Error updating customer care: " + e.getMessage());
            return 0;
        }
    }

    public int deleteCustomerCare(UUID customerCareID) {
        String sql = "DELETE FROM CustomerCares WHERE customerCareID = ?";
        try {
            return jdbcTemplate.update(sql, customerCareID.toString());
        } catch (Exception e) {
            System.out.println("Error deleting customer care: " + e.getMessage());
            return 0;
        }
    }
}
