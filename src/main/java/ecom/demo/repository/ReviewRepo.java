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

    public int addReview(Review review) {
        String sql = "INSERT INTO Reviews (reviewID, productID, comment, rating, reviewTime, userID) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            return jdbcTemplate.update(sql, review.getReviewID().toString(), review.getProductID().toString(), review.getComment(), review.getRating(), review.getReviewTime(), review.getUserID().toString());
        } catch (Exception e) {
            System.out.println("Error adding review: " + e.getMessage());
            return 0;
        }
    }

    public Review getReviewById(UUID reviewID) {
        String sql = "SELECT * FROM Reviews WHERE reviewID = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Review.class), reviewID.toString());
        } catch (Exception e) {
            System.out.println("Error fetching review by ID: " + e.getMessage());
            return null;
        }
    }

    public List<Review> getAllReviews() {
        String sql = "SELECT * FROM Reviews";
        try {
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Review.class));
        } catch (Exception e) {
            System.out.println("Error fetching all reviews: " + e.getMessage());
            return null;
        }
    }

    public int updateReview(Review review) {
        String sql = "UPDATE Reviews SET productID = ?, comment = ?, rating = ?, reviewTime = ?, userID = ? WHERE reviewID = ?";
        try {
            return jdbcTemplate.update(sql, review.getProductID().toString(), review.getComment(), review.getRating(), review.getReviewTime(), review.getUserID().toString(), review.getReviewID().toString());
        } catch (Exception e) {
            System.out.println("Error updating review: " + e.getMessage());
            return 0;
        }
    }

    public int deleteReview(UUID reviewID) {
        String sql = "DELETE FROM Reviews WHERE reviewID = ?";
        try {
            return jdbcTemplate.update(sql, reviewID.toString());
        } catch (Exception e) {
            System.out.println("Error deleting review: " + e.getMessage());
            return 0;
        }
    }
}
