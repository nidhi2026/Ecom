package ecom.demo.models;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Supplier {
    
    private UUID supplierID;

    private String FName;

    private String MName;

    private String LName;

    private String phone;

    private String email;

    private UUID addressID;

    public Supplier(UUID supplierID, String fName, String mName, String lName, String phone, String email,
            UUID addressID) {
        this.supplierID = supplierID;
        FName = fName;
        MName = mName;
        LName = lName;
        this.phone = phone;
        this.email = email;
        this.addressID = addressID;
    }

    

}
