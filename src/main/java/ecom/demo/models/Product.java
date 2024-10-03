package ecom.demo.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
    
    private String productID;

    private String productName;

    private String description;

    private String country;

    private Float rating;

    private Integer discount;

    private Float price;

    private Integer stock;

    private String categoryID;
    

}
