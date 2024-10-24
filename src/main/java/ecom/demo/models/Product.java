package ecom.demo.models;

import lombok.Getter;
import lombok.Setter;
import java.util.UUID;
import java.util.List;
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
}
