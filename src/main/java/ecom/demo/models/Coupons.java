package ecom.demo.models;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Coupons {

    private String couponID;

    private Integer usageLimit;

    private String couponCode;

    private LocalDate validFrom;
    
    private LocalDate validTo;

    private Integer discount;
    
}
