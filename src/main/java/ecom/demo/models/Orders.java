package ecom.demo.models;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Orders {
    
    private String orderID;

    private String productID;

    private String userID;

    private LocalDate orderDate;    

}
