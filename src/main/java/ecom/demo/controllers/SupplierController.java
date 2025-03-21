package ecom.demo.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ecom.demo.models.Product;
import ecom.demo.models.Supplier;
import ecom.demo.models.User;
import ecom.demo.service.SupplierService;
import ecom.demo.service.ProductService;
import ecom.demo.service.UserService;

@Controller
@RequestMapping("/supplier")
public class SupplierController {
 
    private final SupplierService supplierService;
    private final UserService userService;
    private final ProductService productService;
    @Autowired
    public SupplierController(SupplierService supplierService, UserService userService, ProductService productService) {
        this.supplierService = supplierService;
        this.userService = userService;
        this.productService = productService;
    }

    // Endpoint to get all suppliers
    @GetMapping("/all")
    public String getAllSuppliers(Model model) {
        List<Supplier> suppliers = supplierService.getAllSuppliers();
        model.addAttribute("suppliers", suppliers);
        return "suppliers"; // Thymeleaf template name for all suppliers
    }

    // Endpoint to get supplier details by ID
    @GetMapping("/{id}")
    public String getSupplierDetails(@PathVariable("id") UUID supplierID, Authentication authentication, Model model) {
        Supplier supplier = supplierService.getSupplierByID(supplierID);
        List<Product> products =supplierService.getProductsBySupplierId(supplierID);
        if (supplier != null) {
            model.addAttribute("supplier", supplier);
            model.addAttribute("products", products);

            // Add user information if authenticated
            if (authentication != null && authentication.isAuthenticated()) {
                 User currentUser = userService.getUserByEmail(authentication.getName());
                 model.addAttribute("user", currentUser); // Add user to model
            }
            return "supplierDetails"; // Thymeleaf template for supplier details
        }
        return "404"; // Return a 404 page if the supplier is not found
    }



    @GetMapping("/{supplierId}/addProduct")
    public String showAddProductForm(@PathVariable UUID supplierId, Model model) {
        model.addAttribute("supplierId", supplierId);
        model.addAttribute("product", new Product());
        return "addProduct"; // Thymeleaf template for adding a product
    }

    @PostMapping("/{supplierId}/addProduct")
    public String addProduct(@PathVariable UUID supplierId, @ModelAttribute Product product, Model model) {
        product.setSupplierID(supplierId); // Set the supplierID
        int result = productService.saveProduct(product); // Call the non-static method on the instance
        
        if (result > 0) {
            model.addAttribute("successMessage", "Product added successfully!");
        } else {
            model.addAttribute("errorMessage", "Failed to add product.");
        }
        return "redirect:/supplier/all"; // Adjust as needed
    }
}
