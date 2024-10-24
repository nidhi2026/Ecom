package ecom.demo.controllers;

import ecom.demo.models.Product;
import ecom.demo.models.User;
import ecom.demo.models.ProductImage;
import ecom.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@Controller
public class ProductController {

    private final ProductService productService;
    private final UserService userService;
    @Autowired
    public ProductController(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    @GetMapping("/products")
    public String getAllProducts(Model model) {
        List<Product> products = productService.getAllProducts();
        // model.addAttribute("user", userService.getCurrentUser());
        model.addAttribute("products", products);
        return "products"; // Thymeleaf template name
    }

    @GetMapping("/product/{id}")
    public String getProductDetails(@PathVariable("id") UUID productID,Authentication authentication, Model model) {
        Product product = productService.getProductByID(productID);
        if (product != null) {
            List<ProductImage> images = productService.getProductImages(productID); // Fetch images for the specific product
            model.addAttribute("product", product);
            model.addAttribute("images", images);


        // Add user information if authenticated
        if (authentication != null && authentication.isAuthenticated()) {
            User currentUser = userService.getUserByEmail(authentication.getName());
            model.addAttribute("user", currentUser); // Add user to model
        }
            return "productDetails"; // Thymeleaf template for product details
        }
        return "404"; // Return a 404 page if the product is not found
    }
}
