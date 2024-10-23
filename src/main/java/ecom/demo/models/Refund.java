package ecom.demo.models;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Refund {

    private String refundID;

    private LocalDateTime refundTime;

    private String reason;

    private Float amount;

    private String paymentID;

    
    
}
