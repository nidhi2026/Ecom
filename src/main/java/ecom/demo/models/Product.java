package ecom.demo.models;

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

    public Product(UUID productID, String productName, String description, String country, Float rating,
            Integer discount, Float price, Integer stock, UUID categoryID) {
        this.productID = productID;
        this.productName = productName;
        this.description = description;
        this.country = country;
        this.rating = rating;
        this.discount = discount;
        this.price = price;
        this.stock = stock;
        this.categoryID = categoryID;
    }

    

}
