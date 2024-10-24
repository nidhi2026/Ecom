package ecom.demo.models;

import lombok.Getter;
import lombok.Setter;
import java.util.UUID;
@Getter
@Setter
public class ProductImage {
    
    private UUID productID;

    private String imageLink;

}
