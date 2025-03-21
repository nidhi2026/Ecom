package ecom.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ecom.demo.models.Review;

@Repository
public class ReviewRepo {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ReviewRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Method to fetch Review by productID
    public List<Review> getReviewByProductID(UUID productID) {
        String sql = "SELECT * FROM Review WHERE productID = ?";
        try {
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Review.class), productID.toString());
        } catch (Exception e) {
            System.out.println("Error fetching Review by product ID: " + e.getMessage());
            return null;
        }
    }

    // Methods to add, update, and delete Review

    // public int addReview(Review review) {
    //     String sql = "INSERT INTO Review (reviewID, productID, comment, rating, reviewTime, userID) VALUES (?, ?, ?, ?, ?, ?)";
    //     return jdbcTemplate.update(sql, review.getReviewID().toString(), review.getProductID().toString(), review.getComment(), review.getRating(), review.getReviewTime(), review.getUserID().toString());
    // }
    public int addReview(Review review) {
        String sql = "INSERT INTO Review (reviewID, productID, comment, rating, reviewTime, userID) VALUES (?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, 
            review.getReviewID().toString(), 
            review.getProductID().toString(), 
            review.getComment(), 
            review.getRating(), 
            review.getReviewTime(), 
            review.getUserID().toString());
            // review.getImage());  // Store the Base64-encoded image string
    }
    

    public int updateReview(Review review) {
        String sql = "UPDATE Review SET productID = ?, comment = ?, rating = ?, reviewTime = ?, userID = ? WHERE reviewID = ?";
        return jdbcTemplate.update(sql, review.getProductID(), review.getComment(), review.getRating(), review.getReviewTime(), review.getUserID(), review.getReviewID());
    }

    public int deleteReview(UUID reviewID) {
        String sql = "DELETE FROM Review WHERE reviewID = ?";
        return jdbcTemplate.update(sql, reviewID.toString());
    }

    public Review getReviewByID(UUID reviewID) {
        String sql = "SELECT * FROM Review WHERE reviewID = ?";
        try {
            // Query for a single review using the reviewID
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Review.class), reviewID.toString());
        } catch (Exception e) {
            System.out.println("Error fetching review by ID: " + e.getMessage());
            return null;  // If no review is found or any error occurs, return null
        }
    }

    public List<Review> getReviewsByProduct(UUID productID) {
        String sql = "SELECT * FROM Review WHERE productID = ?";
        try {
            // Query for a single review using the reviewID
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Review.class), productID.toString());
        } catch (Exception e) {
            System.out.println("Error fetching review by ID: " + e.getMessage());
            return null;  // If no review is found or any error occurs, return null
        }
    }
    
}
