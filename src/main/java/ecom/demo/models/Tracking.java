package ecom.demo.models;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Tracking {
    
    private UUID trackingID;

    private UUID orderID;

    public enum TrackingStatus {
        SHIPPED, IN_TRANSIT, OUT_FOR_DELIVERY, DELIVERED;
    }

    private TrackingStatus trackingStatus;

    public Tracking(UUID trackingID, UUID orderID) {
        this.trackingID = trackingID;
        this.orderID = orderID;
        this.trackingStatus = TrackingStatus.SHIPPED;
    }

}
