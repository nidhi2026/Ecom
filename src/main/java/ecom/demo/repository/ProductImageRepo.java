package ecom.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ecom.demo.models.ProductImage;

@Repository
public class ProductImageRepo {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductImageRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int addProductImage(ProductImage productImage) {
        String sql = "INSERT INTO ProductImage (productID, imageLink) VALUES (?, ?)";
        try {
            return jdbcTemplate.update(sql, productImage.getProductID().toString(), productImage.getImageLink());
        } catch (Exception e) {
            System.out.println("Error adding product image: " + e.getMessage());
            return 0;
        }
    }

    public List<ProductImage>  getProductImageById(UUID productID) {
        String sql = "SELECT * FROM ProductImage WHERE productID = ?";
        try {
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ProductImage.class), productID.toString());
        } catch (Exception e) {
            System.out.println("Error fetching product image by ID: " + e.getMessage());
            return null;
        }
    }

    public List<ProductImage> getAllProductImages() {
        String sql = "SELECT * FROM ProductImage";
        try {
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ProductImage.class));
        } catch (Exception e) {
            System.out.println("Error fetching all product images: " + e.getMessage());
            return null;
        }
    }

    public int updateProductImage(ProductImage productImage) {
        String sql = "UPDATE ProductImage SET imageLink = ? WHERE productID = ?";
        try {
            return jdbcTemplate.update(sql, productImage.getImageLink(), productImage.getProductID().toString());
        } catch (Exception e) {
            System.out.println("Error updating product image: " + e.getMessage());
            return 0;
        }
    }

    public int deleteProductImage(UUID productID) {
        String sql = "DELETE FROM ProductImage WHERE productID = ?";
        try {
            return jdbcTemplate.update(sql, productID.toString());
        } catch (Exception e) {
            System.out.println("Error deleting product image: " + e.getMessage());
            return 0;
        }
    }
}
