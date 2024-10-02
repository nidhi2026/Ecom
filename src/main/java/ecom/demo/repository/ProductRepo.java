package ecom.demo.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ecom.demo.models.Product;

@Repository
public class ProductRepo {
    
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Method to add a new product
    public int addProduct(Product product) {
        String sql = "INSERT INTO Product (productID, productName, description, country, rating, discount, price, stock, categoryID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            return jdbcTemplate.update(sql, 
                product.getProductID().toString(), 
                product.getProductName(), 
                product.getDescription(), 
                product.getCountry(), 
                product.getRating(), 
                product.getDiscount(), 
                product.getPrice(), 
                product.getStock(), 
                product.getCategoryID().toString());
        } catch (Exception e) {
            System.out.println("Error adding product: " + e.getMessage());
            return 0;
        }
    }

    // Method to get a product by ID
    public Optional<Product> getProductByID(UUID productID) {
        String sql = "SELECT * FROM Product WHERE productID = ?";
        try {
            return Optional.of(jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Product.class), productID.toString()));
        } catch (Exception e) {
            System.out.println("Error fetching product by ID: " + e.getMessage());
            return Optional.empty();
        }
    }

    // Method to get all products
    public List<Product> getAllProducts() {
        String sql = "SELECT * FROM Product";
        try {
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Product.class));
        } catch (Exception e) {
            System.out.println("Error fetching all products: " + e.getMessage());
            return List.of(); // Return empty list if an error occurs
        }
    }

    // Method to update a product
    public int updateProduct(Product product) {
        String sql = "UPDATE Product SET productName = ?, description = ?, country = ?, rating = ?, discount = ?, price = ?, stock = ?, categoryID = ? WHERE productID = ?";
        try {
            return jdbcTemplate.update(sql, 
                product.getProductName(), 
                product.getDescription(), 
                product.getCountry(), 
                product.getRating(), 
                product.getDiscount(), 
                product.getPrice(), 
                product.getStock(), 
                product.getCategoryID().toString(), 
                product.getProductID().toString());
        } catch (Exception e) {
            System.out.println("Error updating product: " + e.getMessage());
            return 0;
        }
    }

    // Method to delete a product by ID
    public int deleteProduct(UUID productID) {
        String sql = "DELETE FROM Product WHERE productID = ?";
        try {
            return jdbcTemplate.update(sql, productID.toString());
        } catch (Exception e) {
            System.out.println("Error deleting product: " + e.getMessage());
            return 0;
        }
    }
}
