package ecom.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ecom.demo.models.SuppliesList;

@Repository
public class SuppliesListRepo {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SuppliesListRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int addSuppliesList(SuppliesList suppliesList) {
        String sql = "INSERT INTO SuppliesLists (supplierID, productID) VALUES (?, ?)";
        try {
            return jdbcTemplate.update(sql, suppliesList.getSupplierID().toString(), suppliesList.getProductID().toString());
        } catch (Exception e) {
            System.out.println("Error adding supplies list: " + e.getMessage());
            return 0;
        }
    }

    public SuppliesList getSuppliesListById(UUID supplierID, UUID productID) {
        String sql = "SELECT * FROM SuppliesLists WHERE supplierID = ? AND productID = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(SuppliesList.class), supplierID.toString(), productID.toString());
        } catch (Exception e) {
            System.out.println("Error fetching supplies list by ID: " + e.getMessage());
            return null;
        }
    }

    public List<SuppliesList> getAllSuppliesLists() {
        String sql = "SELECT * FROM SuppliesLists";
        try {
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(SuppliesList.class));
        } catch (Exception e) {
            System.out.println("Error fetching all supplies lists: " + e.getMessage());
            return null;
        }
    }

    public int updateSuppliesList(SuppliesList suppliesList) {
        String sql = "UPDATE SuppliesLists SET productID = ? WHERE supplierID = ?";
        try {
            return jdbcTemplate.update(sql, suppliesList.getProductID().toString(), suppliesList.getSupplierID().toString());
        } catch (Exception e) {
            System.out.println("Error updating supplies list: " + e.getMessage());
            return 0;
        }
    }

    public int deleteSuppliesList(UUID supplierID, UUID productID) {
        String sql = "DELETE FROM SuppliesLists WHERE supplierID = ? AND productID = ?";
        try {
            return jdbcTemplate.update(sql, supplierID.toString(), productID.toString());
        } catch (Exception e) {
            System.out.println("Error deleting supplies list: " + e.getMessage());
            return 0;
        }
    }
}
