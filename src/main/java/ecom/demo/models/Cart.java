package ecom.demo.models;

import java.time.LocalDate;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cart {

    private UUID cartID;

    private UUID productID;

    private LocalDate addTime;

    private UUID userID;

    private Integer quantity;

    public Cart(UUID cartID, UUID productID, LocalDate addTime, UUID userID, Integer quantity) {
        this.cartID = cartID;
        this.productID = productID;
        this.addTime = addTime;
        this.userID = userID;
        this.quantity = quantity;
    }

    
    
}
