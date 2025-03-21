package ecom.demo.models;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Address {
    
    private UUID addressID;

    private UUID personID;

    private String streetName;

    private String cityName;

    private String districtName;

    private String pincode;

    private String countryName;


}
