package ecom.demo.models;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewImage {
    
    private UUID reviewID;

    private String image;

    public ReviewImage(UUID reviewID, String image) {
        this.reviewID = reviewID;
        this.image = image;
    }

    

}
