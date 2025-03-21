package ecom.demo.models;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Refund {

    private UUID refundID;

    private LocalDateTime refundTime;

    private String reason;

    private Float amount;

    private UUID orderID;

    private UUID productID;
    
}
