package ecom.demo.models;

import java.time.LocalDate;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserOrder {
    private UUID userID; //pk
    private UUID orderID;
    private LocalDate date;
}
