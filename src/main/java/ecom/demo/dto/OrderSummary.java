package ecom.demo.dto;

import java.util.List;
import java.util.UUID;
import ecom.demo.models.Tracking;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderSummary {
    private UUID orderID;
    private List<OrderDetails> orderDetails;
    private Float totalCost;
    private Tracking tracking;

    public OrderSummary(UUID orderID, List<OrderDetails> orderDetails, Float totalCost, Tracking tracking) {
        this.orderID = orderID;
        this.orderDetails = orderDetails;
        this.totalCost = totalCost;
        this.tracking = tracking;
    }
}

