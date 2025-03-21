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

    private Integer low;

    private Integer high;

    private Integer discount;
    
}