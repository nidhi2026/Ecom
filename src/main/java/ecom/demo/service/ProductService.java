package ecom.demo.service;

import ecom.demo.models.Product;
import ecom.demo.models.ProductImage;
import ecom.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    
    private final ProductRepo productRepo;
    private final ProductImageRepo productImageRepo;

    @Autowired
    public ProductService(ProductRepo productRepo, ProductImageRepo productImageRepo) {
        this.productRepo = productRepo;
        this.productImageRepo = productImageRepo;
    }

    public List<Product> getAllProducts() {
        return productRepo.getAllProducts();
    }

    public List<ProductImage> getProductImages(UUID productID) {
        return productImageRepo.getProductImageById(productID);
    }

    public Product getProductByID(UUID productID) {
        return productRepo.getProductByID(productID).orElse(null);
    }
}
