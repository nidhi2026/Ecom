package ecom.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ecom.demo.models.Coupons;
import ecom.demo.repository.CartRepo;
import ecom.demo.repository.CouponsRepo;

@Service
public class CouponService {

    private final CouponsRepo couponsRepo;
    private final CartRepo cartRepo;
    @Autowired
    public CouponService(CouponsRepo couponsRepo,CartRepo cartRepo) {
        this.couponsRepo = couponsRepo;
        this.cartRepo= cartRepo;

    }

   

    public Coupons getDiscount(Float totalAmount) {
        Coupons coupon = couponsRepo.getDiscount(totalAmount); // Fetch the complete Coupons object
        if(coupon != null){
            Float curAmount = totalAmount - coupon.getDiscount();
            if(curAmount <=0 ) return null;
        }
        return coupon; // Return the Coupons object, can be null if no matching coupon is found
    }
    
    

}