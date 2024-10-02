package ecom.demo.models;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Category {
    
    private UUID categoryID;

    private String categoryName;

    public Category(UUID categoryID, String categoryName) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
    }

}
