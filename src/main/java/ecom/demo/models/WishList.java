package ecom.demo.models;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WishList {
    
    private String wishlistID;

    private String productID;

    private LocalDateTime addTime;

    private String userID;

}
