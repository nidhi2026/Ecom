package ecom.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ecom.demo.models.ReviewImage;

@Repository
public class ReviewImageRepo {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ReviewImageRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int addReviewImage(ReviewImage reviewImage) {
        String sql = "INSERT INTO ReviewImages (reviewID, image) VALUES (?, ?)";
        try {
            return jdbcTemplate.update(sql, reviewImage.getReviewID().toString(), reviewImage.getImage());
        } catch (Exception e) {
            System.out.println("Error adding review image: " + e.getMessage());
            return 0;
        }
    }

    public ReviewImage getReviewImageById(UUID reviewID) {
        String sql = "SELECT * FROM ReviewImages WHERE reviewID = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(ReviewImage.class), reviewID.toString());
        } catch (Exception e) {
            System.out.println("Error fetching review image by ID: " + e.getMessage());
            return null;
        }
    }

    public List<ReviewImage> getAllReviewImages() {
        String sql = "SELECT * FROM ReviewImages";
        try {
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ReviewImage.class));
        } catch (Exception e) {
            System.out.println("Error fetching all review images: " + e.getMessage());
            return null;
        }
    }

    public int updateReviewImage(ReviewImage reviewImage) {
        String sql = "UPDATE ReviewImages SET image = ? WHERE reviewID = ?";
        try {
            return jdbcTemplate.update(sql, reviewImage.getImage(), reviewImage.getReviewID().toString());
        } catch (Exception e) {
            System.out.println("Error updating review image: " + e.getMessage());
            return 0;
        }
    }

    public int deleteReviewImage(UUID reviewID) {
        String sql = "DELETE FROM ReviewImages WHERE reviewID = ?";
        try {
            return jdbcTemplate.update(sql, reviewID.toString());
        } catch (Exception e) {
            System.out.println("Error deleting review image: " + e.getMessage());
            return 0;
        }
    }
}
