package ecom.demo.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Address {
    
    private String addressID;

    private String personID;

    private String streetName;

    private String cityName;

    private String districtName;

    private String pincode;

    private String countryName;


}
