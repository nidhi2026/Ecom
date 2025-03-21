package ecom.demo.service;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ecom.demo.dto.ReviewDTO;
import ecom.demo.models.Review;
import ecom.demo.models.User;
import ecom.demo.repository.ReviewRepo;

@Service
public class ReviewService {

    private final ReviewRepo reviewRepo;

    // @Value("${image.upload.directory}")
    // private String uploadDirectory;

    @Autowired
    public ReviewService(ReviewRepo reviewRepo) {
        this.reviewRepo = reviewRepo;
    }

    // Method to create and add a review
    public int addReview(ReviewDTO reviewDTO, User currentUser) {
        // Create a new Review and populate it with the DTO and User information
        Review review = new Review();
        review.setReviewID(UUID.randomUUID());
        review.setProductID(reviewDTO.getReview().getProductID());
        review.setComment(reviewDTO.getReview().getComment());
        review.setRating(reviewDTO.getReview().getRating());
        review.setReviewTime(LocalDateTime.now());
        review.setUserID(currentUser.getUserID());
        // review.setImage(reviewDTO.getImage());
        // String imageFile = reviewDTO.getImage();
        // review.setImage(imageFile); 
        
        // Save the review through the repository
        return reviewRepo.addReview(review);
    }

    public List<Review> getReviewsByProduct(UUID productID) {
        return reviewRepo.getReviewsByProduct(productID);
    }


    public boolean deleteReview(UUID reviewID, User currentUser) {
        // Check if the review exists in the database
        Review review = reviewRepo.getReviewByID(reviewID);
        if (review == null) {
            // If the review doesn't exist, return false (or handle it accordingly)
            return false;
        }
    
        // Check if the current user is authorized to delete this review
        // For example, check if the user is the one who created the review
        if (!review.getUserID().equals(currentUser.getUserID())) {
            // If the user is not authorized, return false (or handle authorization failure)
            return false;
        }
    
        // Proceed to delete the review if it exists and the user is authorized
        return reviewRepo.deleteReview(reviewID) > 0; // Returns true if the deletion is successful
    }
    
}
