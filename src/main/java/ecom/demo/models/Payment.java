package ecom.demo.models;

import java.time.LocalDateTime;
import java.util.*;
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

    private PaymentMode paymentMode;

    private Float amount;
}
