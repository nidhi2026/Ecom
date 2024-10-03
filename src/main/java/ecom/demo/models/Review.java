package ecom.demo.models;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Review {

    private String reviewID;

    private String productID;

    private String comment;

    private Integer rating;

    private LocalDateTime reviewTime;

    private String userID;

    
}
