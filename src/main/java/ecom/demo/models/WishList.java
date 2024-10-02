package ecom.demo.models;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WishList {
    
    private UUID wishlistID;

    private UUID productID;

    private LocalDateTime addTime;

    private UUID userID;

    public WishList(UUID wishlistID, UUID productID, LocalDateTime addTime, UUID userID) {
        this.wishlistID = wishlistID;
        this.productID = productID;
        this.addTime = addTime;
        this.userID = userID;
    }

}
