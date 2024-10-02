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

    public Address(UUID addressID, UUID personID, String streetName, String cityName, String districtName,
            String pincode, String countryName) {
        this.addressID = addressID;
        this.personID = personID;
        this.streetName = streetName;
        this.cityName = cityName;
        this.districtName = districtName;
        this.pincode = pincode;
        this.countryName = countryName;
    }


}
