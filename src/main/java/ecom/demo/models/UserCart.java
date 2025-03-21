package ecom.demo.models;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCart {
    
    private UUID userID; // pk

    private UUID cartID;

}
