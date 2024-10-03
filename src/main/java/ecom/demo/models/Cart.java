package ecom.demo.models;

import java.time.LocalDate;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cart {

    private String cartID;

    private String productID;

    private LocalDate addTime;

    private String userID;

    private Integer quantity;
}
