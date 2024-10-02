package ecom.demo.models;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductImage {
    
    private UUID productID;

    private String imageLink;

    public ProductImage(UUID productID, String imageLink) {
        this.productID = productID;
        this.imageLink = imageLink;
    }

    

}
