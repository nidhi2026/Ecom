package ecom.demo.models;

import java.time.LocalDate;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Order {
    
    private UUID orderID;

    private UUID productID;

    private UUID userID;

    private LocalDate orderDate;

    public Order(UUID orderID, UUID productID, UUID userID, LocalDate orderDate) {
        this.orderID = orderID;
        this.productID = productID;
        this.userID = userID;
        this.orderDate = orderDate;
    }

    

}
