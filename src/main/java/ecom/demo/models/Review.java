package ecom.demo.models;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Review {

    private UUID reviewID;

    private UUID productID;

    private String comment;

    private Integer rating;

    private LocalDateTime reviewTime;

    private UUID userID;

    public Review(UUID reviewID, UUID productID, String comment, Integer rating, LocalDateTime reviewTime,
            UUID userID) {
        this.reviewID = reviewID;
        this.productID = productID;
        this.comment = comment;
        this.rating = rating;
        this.reviewTime = reviewTime;
        this.userID = userID;
    }

    
    
}
