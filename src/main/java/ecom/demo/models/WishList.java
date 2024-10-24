package ecom.demo.models;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import java.util.UUID;
@Getter
@Setter
public class WishList {
    
    private UUID wishlistID;

    private UUID productID;

    private LocalDateTime addTime;

    private UUID userID;

}
