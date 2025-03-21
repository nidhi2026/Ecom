package ecom.demo.dto;

import ecom.demo.models.Review;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDTO {
   
    private Review review;

    public ReviewDTO(Review review) {
        this.review = review;
    }

}
