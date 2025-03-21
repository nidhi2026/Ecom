package ecom.demo.controllers;

import ecom.demo.models.Product;
import ecom.demo.models.User;
import ecom.demo.models.ProductImage;
import ecom.demo.models.Review;
import ecom.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class ProductController {

    private final ProductService productService;
    private final UserService userService;
    private final WishListService wishListService;
    private final ReviewService reviewService;
    @Autowired
    public ProductController(ProductService productService, UserService userService,WishListService wishListService, ReviewService reviewService) {
        this.productService = productService;
        this.userService = userService;
        this.wishListService =  wishListService;
        this.reviewService = reviewService;
    }

    @GetMapping("/products")
    public String getAllProducts(Model model,Authentication authentication) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
           
        System.out.println("Authentication: " + authentication);
        if (authentication != null && authentication.isAuthenticated()) {
            // User is logged in
            User currentUser = userService.getUserByEmail(authentication.getName());
            if (currentUser != null) { // Check if user is not null
                System.out.println("user: " + currentUser.getFName());
            }
        } else {
            // User is not logged in
            System.out.println("User is not authenticated");
        }
        return "products"; // Thymeleaf template name
    }

    @GetMapping("/products/{id}")
    public String getProductDetails(@PathVariable("id") UUID productID,
                                    Authentication authentication,
                                    Model model) {
        Product product = productService.getProductByID(productID);
        if (product != null) {
            List<ProductImage> images = productService.getProductImages(productID); // Fetch images for the specific product
            List<Review> reviews = reviewService.getReviewsByProduct(productID);
            Map<Review, String> reviewUserMap = new HashMap<>();
            for (Review review : reviews) {
                User user = userService.getUserById(review.getUserID());
                String fullName = user.getFName() + " " 
                                + (user.getMName() != null ? user.getMName() + " " : "") 
                                + user.getLName();
                reviewUserMap.put(review, fullName);
            }
            
            model.addAttribute("product", product);
            model.addAttribute("images", images);
            model.addAttribute("reviews", reviews);
            model.addAttribute("reviewUserMap", reviewUserMap);

            if (authentication != null) {
                User currentUser = userService.getUserByEmail(authentication.getName());
                // Check if the product is in the user's wishlist
                boolean isWishlisted = wishListService.isProductInWishlist(currentUser.getUserID(), productID);
                model.addAttribute("isWishlisted", isWishlisted);
            } else {
                model.addAttribute("isWishlisted", false); // User is not logged in
            }
        } else {
            return "404"; // Product not found
        }
        
        return "productDetails"; // Thymeleaf template for product details
    }

}
