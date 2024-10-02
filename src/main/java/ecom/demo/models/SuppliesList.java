package ecom.demo.models;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SuppliesList {
    
    private UUID supplierID;

    private UUID productID;

    public SuppliesList(UUID supplierID, UUID productID) {
        this.supplierID = supplierID;
        this.productID = productID;
    }

    

}
