package ecom.demo.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ecom.demo.dto.OrderDetails;
import ecom.demo.dto.OrderSummary;
import ecom.demo.models.Cart;
import ecom.demo.models.Orders;
import ecom.demo.models.Product;
import ecom.demo.models.Tracking;
import ecom.demo.models.UserCart;
import ecom.demo.models.UserOrder;
import ecom.demo.repository.AddressRepo;
import ecom.demo.repository.CartRepo;
import ecom.demo.repository.OrderRepo;
import ecom.demo.repository.ProductRepo;
import ecom.demo.repository.RefundRepo;

@Service
public class OrderService {

    private final CartRepo cartRepo;
    private final ProductRepo productRepo;
    private final AddressRepo addressRepo;
    private final OrderRepo orderRepo;
    private final CartService cartService;
    private final ProductService productService;
    private final RefundRepo refundRepo;
    @Autowired
    public OrderService(CartRepo cartRepo,  ProductRepo productRepo, AddressRepo addressRepo, CartService cartService,OrderRepo orderRepo, ProductService productService, RefundRepo refundRepo) {
        this.cartRepo = cartRepo;
        this.productRepo =  productRepo;
        this.addressRepo =  addressRepo;
        this.cartService = cartService;
        this.orderRepo = orderRepo;
        this.productService = productService;
        this.refundRepo = refundRepo;
    }

    public int placeOrder(UUID cartID) {
        return 1;
    }

    public List<List<Orders>> getAllOrders(UUID userID) {
        List<UserOrder> userOrder = orderRepo.getUserOrderById(userID);
        List<List<Orders>> orders = new ArrayList<>();
        for (UserOrder entry : userOrder) {
            List<Orders> orderList = orderRepo.getOrdersById(entry.getOrderID());
            orders.add(orderList);
        }
        return orders;
    }


    public int addUserOrder(UserOrder userOrder) {
        return orderRepo.addUserOrder(userOrder);
    }

    public UserOrder generateOrder(UUID userID, UUID addressID){
        UserCart userCart = cartService.getUserCartById(userID);
        UUID cartID = userCart.getCartID();
        UserOrder userOrder = new UserOrder();
        userOrder.setUserID(userID);
        userOrder.setOrderID(UUID.randomUUID());
        userOrder.setDate(LocalDate.now());
        addUserOrder(userOrder);

        List<Cart> cart = cartRepo.getSelectedCartsById(cartID);
        for (Cart entry : cart) {
            Orders order = new Orders();
            order.setOrderID(userOrder.getOrderID());
            order.setProductID(entry.getProductID());
            order.setQuantity(entry.getQuantity());
            order.setAddressID(addressID);

            orderRepo.addOrder(order);
        }

        Tracking tracking = new Tracking();
        tracking.setOrderID(userOrder.getOrderID());
        tracking.setTrackingID(UUID.randomUUID());
        tracking.setTrackingStatus(Tracking.TrackingStatus.SHIPPED);
        return userOrder;
    }

    public List<OrderSummary> getOrderSummaries(UUID userID) {
        List<UserOrder> userOrders = orderRepo.getUserOrderById(userID);
        List<OrderSummary> orderSummaries = new ArrayList<>();

        for (UserOrder userOrder : userOrders) {
            List<OrderDetails> orderDetails = new ArrayList<>();
            Float totalCost = 0f;

            List<Orders> orders = orderRepo.getOrdersById(userOrder.getOrderID());
            for (Orders order : orders) {
                Product product = productRepo.getProductById(order.getProductID());
                product.setImages(productService.getProductImages(order.getProductID()));
                Float cost = product.getPrice() * order.getQuantity();
                totalCost += cost;

                boolean isRefunded = refundRepo.isProductRefunded(userOrder.getOrderID(), order.getProductID());
                System.out.println(isRefunded);
                orderDetails.add(new OrderDetails(order, product, cost, isRefunded));
            }

            Tracking tracking = orderRepo.getTrackingByOrderID(userOrder.getOrderID());
            if (tracking == null) {
                tracking = new Tracking();
                tracking.setTrackingID(UUID.randomUUID());  // Set a new tracking ID if needed
                tracking.setTrackingStatus(Tracking.TrackingStatus.SHIPPED);      // Set default status to "Shipped"
            }
            orderSummaries.add(new OrderSummary(userOrder.getOrderID(), orderDetails, totalCost, tracking));
        }

        return orderSummaries;
    }

    public int removeFromOrder(UUID userID, UUID orderID, UUID productID) {        
        System.out.println("trying to remove the order from cart");
        int status = orderRepo.removeOrder(orderID, productID);
        if(orderRepo.isOrderEmpty(orderID)) {
            return orderRepo.removeUserOrder(userID, orderID);
        }
        return status;
    }

    public Orders getOrderByProductID(UUID orderID, UUID productID) {
        return orderRepo.getOrderByProductID(orderID, productID);
    }

}
