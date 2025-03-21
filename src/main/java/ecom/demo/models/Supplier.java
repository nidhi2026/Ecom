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


}
