package ecom.demo.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Tracking {
    
    private String trackingID;

    private String orderID;

    public enum TrackingStatus {
        SHIPPED, IN_TRANSIT, OUT_FOR_DELIVERY, DELIVERED;
    }

    private TrackingStatus trackingStatus;

}
