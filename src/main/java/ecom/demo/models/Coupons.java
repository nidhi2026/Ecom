package ecom.demo.models;

import java.time.LocalDate;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Coupons {

    private UUID couponID;

    private Integer usageLimit;

    private String couponCode;

    private LocalDate validFrom;
    
    private LocalDate validTo;

    private Integer discount;

    public Coupons(UUID couponID, Integer usageLimit, String couponCode, LocalDate validFrom, LocalDate validTo,
            Integer discount) {
        this.couponID = couponID;
        this.usageLimit = usageLimit;
        this.couponCode = couponCode;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.discount = discount;
    }

    
    
}
