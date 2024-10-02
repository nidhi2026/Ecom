package ecom.demo.models;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Payment {

    private UUID paymentID;

    private UUID orderID;

    private LocalDateTime paymentTime;

    public enum PaymentMode {
        CASH_ON_DELIVERY, UPI, NET_BANKING
    }

    public PaymentMode paymentMode;

    public Payment(UUID paymentID, UUID orderID, LocalDateTime paymentTime) {
        this.paymentID = paymentID;
        this.orderID = orderID;
        this.paymentTime = paymentTime;
        this.paymentMode = PaymentMode.CASH_ON_DELIVERY;
    }

    

}
