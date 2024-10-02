package ecom.demo.models;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CouponUsability {
    
    private UUID couponID;

    private UUID productID;

    public CouponUsability(UUID couponID, UUID productID) {
        this.couponID = couponID;
        this.productID = productID;
    }

    

}
