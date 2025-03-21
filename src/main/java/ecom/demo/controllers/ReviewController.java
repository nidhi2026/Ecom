package ecom.demo.controllers;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ecom.demo.dto.ReviewDTO;
import ecom.demo.models.Review;
import ecom.demo.models.User;
import ecom.demo.service.ReviewService;
import ecom.demo.service.UserService;

@Controller  // Change from @RestController to @Controller
@RequestMapping("/reviews")
public class ReviewController {
 
    private final ReviewService reviewService;
    private final UserService userService;

    @Autowired
    public ReviewController(ReviewService reviewService, UserService userService) {
        this.reviewService = reviewService;
        this.userService = userService;
    }
    
 
    @GetMapping("/add/{id}")
    public String showAddReviewForm(@PathVariable("id") UUID productID, Model model) {
        Review review = new Review();
        review.setProductID(productID);
        ReviewDTO reviewDTO = new ReviewDTO(review);

        model.addAttribute("reviewDTO", reviewDTO); 
        return "addReview"; // Renders review.html
    }

    @PostMapping("/add")
    public String addReview(
            @Valid @ModelAttribute("reviewDTO") ReviewDTO reviewDTO,
            Authentication authentication,
            Model model) {
                System.out.println("add review post map");
        User currentUser = userService.getUserByEmail(authentication.getName());
        int result = reviewService.addReview(reviewDTO, currentUser);

        if (result > 0) {
            model.addAttribute("message", "Review added successfully!");
        } else {
            model.addAttribute("error", "Failed to add review. Please try again.");
        }

        return "redirect:/products/"+reviewDTO.getReview().getProductID();
    }

    // New method to handle review deletion
    @PostMapping("/delete")
    public String deleteReview(
            @RequestParam("reviewID") UUID reviewID,
            Authentication authentication,
            Model model) {
        // Retrieve the current logged-in user
        User currentUser = userService.getUserByEmail(authentication.getName());
        
        // Call the service to delete the review
        boolean isDeleted = reviewService.deleteReview(reviewID, currentUser);

        if (isDeleted) {
            model.addAttribute("message", "Review deleted successfully!");
        } else {
            model.addAttribute("error", "Failed to delete the review. You might not have permission or the review doesn't exist.");
        }

        return "index";  // Redirect to a list of reviews or another page
    }
}
