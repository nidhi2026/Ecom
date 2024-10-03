package ecom.demo.models;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Payment {

    private String paymentID;

    private String orderID;

    private LocalDateTime paymentTime;

    public enum PaymentMode {
        CASH_ON_DELIVERY, UPI, NET_BANKING
    }

    private PaymentMode paymentMode;

}
