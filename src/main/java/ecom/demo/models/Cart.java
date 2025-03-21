package ecom.demo.models;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cart {

    private UUID cartID;

    private UUID productID;

    private Integer quantity;

    private boolean choose;
}
