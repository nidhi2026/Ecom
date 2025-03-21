package ecom.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ecom.demo.models.Product;
import ecom.demo.models.Supplier;

@Repository
public class SupplierRepo {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SupplierRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int addSupplier(Supplier supplier) {
        String sql = "INSERT INTO Supplier (supplierID, FName, MName, LName, phone, email) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            return jdbcTemplate.update(sql, supplier.getSupplierID().toString(), supplier.getFName(), supplier.getMName(), supplier.getLName(), supplier.getPhone(), supplier.getEmail());
        } catch (Exception e) {
            System.out.println("Error adding supplier: " + e.getMessage());
            return 0;
        }
    }

    public Supplier getSupplierById(UUID supplierID) {
        String sql = "SELECT * FROM Supplier WHERE supplierID = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Supplier.class), supplierID.toString());
        } catch (Exception e) {
            System.out.println("Error fetching supplier by ID: " + e.getMessage());
            return null;
        }
    }

    public List<Supplier> getAllSuppliers() {
        String sql = "SELECT * FROM Supplier";
        try {
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Supplier.class));
        } catch (Exception e) {
            System.out.println("Error fetching all suppliers: " + e.getMessage());
            return null;
        }
    }

    public int updateSupplier(Supplier supplier) {
        String sql = "UPDATE Suppliers SET FName = ?, MName = ?, LName = ?, phone = ?, email = ?, addressID = ? WHERE supplierID = ?";
        try {
            return jdbcTemplate.update(sql, supplier.getFName(), supplier.getMName(), supplier.getLName(), supplier.getPhone(), supplier.getEmail(), supplier.getAddressID().toString(), supplier.getSupplierID().toString());
        } catch (Exception e) {
            System.out.println("Error updating supplier: " + e.getMessage());
            return 0;
        }
    }

    public int deleteSupplier(UUID supplierID) {
        String sql = "DELETE FROM Supplier WHERE supplierID = ?";
        try {
            return jdbcTemplate.update(sql, supplierID.toString());
        } catch (Exception e) {
            System.out.println("Error deleting supplier: " + e.getMessage());
            return 0;
        }
    }

      public List<Product> getProductsBySupplierId(UUID supplierID) {
        String sql = "SELECT * FROM Product p WHERE p.supplierID =?"; // Adjust SQL according to your schema
        try {
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Product.class), supplierID.toString());
        } catch (Exception e) {
            System.out.println("Error fetching products by supplier ID: " + e.getMessage());
            return null;
        }
      
        
        
        
    }
}
