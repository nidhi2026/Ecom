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

    private UUID paymentID;

    public Refund(UUID refundID, LocalDateTime refundTime, String reason, Float amount, UUID paymentID) {
        this.refundID = refundID;
        this.refundTime = refundTime;
        this.reason = reason;
        this.amount = amount;
        this.paymentID = paymentID;
    }

    
    
}
