package ecom.demo.models;

import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
    
    private UUID productID;

    private String productName;

    private String description;

    private String country;

    private Float rating;

    private Integer discount;

    private Float price;

    private Integer stock;

    private UUID categoryID;
    
    private List<ProductImage> images;

    private UUID supplierID;
}
