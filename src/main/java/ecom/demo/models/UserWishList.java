package ecom.demo.models;

import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
public class UserWishList {
    
    private UUID userID; // pk

    private UUID wishlistID;

}
