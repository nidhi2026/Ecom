package ecom.demo.dto;

import ecom.demo.models.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCartDto {
    private Product product;
    private int quantity;
    private boolean selected;

    // Constructor
    public ProductCartDto(Product product, int quantity, boolean selected) {
        this.product = product;
        this.quantity = quantity;
        this.selected = selected;
    }
}
