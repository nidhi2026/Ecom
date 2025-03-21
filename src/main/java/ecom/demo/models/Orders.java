package ecom.demo.models;

// import java.time.LocalDate;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class Orders {
    
    private UUID orderID;

    private UUID productID;

    private Integer quantity;

    private UUID addressID;

}
