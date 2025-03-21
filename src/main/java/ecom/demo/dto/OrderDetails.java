package ecom.demo.dto;

import lombok.Getter;
import lombok.Setter;

import ecom.demo.models.Orders;
import ecom.demo.models.Product;

@Getter
@Setter
public class OrderDetails {
    private Orders order;
    private Product product;
    private Float cost;
    private boolean isRefunded;

    public OrderDetails(Orders order, Product product,Float cost, boolean isRefunded) {
        this.order = order;
        this.product = product;
        this.cost = cost;
        this.isRefunded = isRefunded;
    }
}
