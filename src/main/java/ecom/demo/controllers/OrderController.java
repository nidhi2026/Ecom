package ecom.demo.controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import ecom.demo.dto.OrderSummary;
import ecom.demo.models.Address;
import ecom.demo.models.Orders;
import ecom.demo.models.Payment;
import ecom.demo.models.Product;
import ecom.demo.models.Refund;
import ecom.demo.models.User;
import ecom.demo.models.UserOrder;
import ecom.demo.repository.PaymentRepo;
import ecom.demo.repository.RefundRepo;
import ecom.demo.service.AddressService;
import ecom.demo.service.CartService;
import ecom.demo.service.OrderService;
import ecom.demo.service.ProductService;
import ecom.demo.service.UserService;



@Controller
@RequestMapping("/order")
public class OrderController {
    private final CartService cartService;
    private final UserService userService;
    private final OrderService orderService;
    private final AddressService addressService;
    private final PaymentRepo paymentRepo;
    private final RefundRepo refundRepo;
    private final ProductService productService;

    public OrderController(CartService cartService, UserService userService, OrderService orderService, AddressService addressService, PaymentRepo paymentRepo, RefundRepo refundRepo, ProductService productService){
        this.cartService = cartService;
        this.userService = userService;
        this.orderService = orderService;
        this.addressService = addressService;
        this.paymentRepo = paymentRepo;
        this.refundRepo = refundRepo;
        this.productService = productService;
    }

    @GetMapping("/{userId}")
    public String getUserOrderSummaries(@PathVariable("userId") UUID userId, Model model) {
        List<OrderSummary> orderSummaries = orderService.getOrderSummaries(userId);
        model.addAttribute("orderSummaries", orderSummaries);
        model.addAttribute("userId", userId);
        return "orders";
    }

    
    @GetMapping("/submit")
    public String order(Authentication authentication, Model model) {
        User currentUser = userService.getUserByEmail(authentication.getName());
        model.addAttribute("user", currentUser);

        List<Address> addresses = addressService.getAddresses(currentUser.getUserID());
        model.addAttribute("addresses", addresses);
        return "selectAddress";
    }

    @PostMapping("/confirm")
    public String confirmOrder(@RequestParam("selectedAddressID") UUID selectedAddressID, Model model, Authentication authentication) {
        model.addAttribute("addressID", selectedAddressID);

        User currentUser = userService.getUserByEmail(authentication.getName());
        model.addAttribute("user", currentUser);

        Float totalAmount = cartService.calculateTotalAmount(cartService.getCartProductsByUserId(currentUser.getUserID()));
        model.addAttribute("totalAmount", totalAmount);
        return "payment";
    }
    
    @PostMapping("/placeOrder")
    public String placeOrder(@RequestParam("addressID") UUID addressID, @RequestParam("totalAmount") Float totalAmount, @RequestParam("paymentMode") String paymentMode, Model model, Authentication authentication) {
        User currentUser = userService.getUserByEmail(authentication.getName());
        model.addAttribute("user", currentUser);
        UUID userID = currentUser.getUserID();

        UserOrder userOrder = orderService.generateOrder(userID, addressID);
        
        Payment payment = new Payment();
        payment.setPaymentID(UUID.randomUUID()); // Generating a new payment ID
        payment.setOrderID(userOrder.getOrderID());
        payment.setPaymentTime(LocalDateTime.now());
        // Convert the String paymentMode to PaymentMode enum
        try {
            payment.setPaymentMode(Payment.PaymentMode.valueOf(paymentMode.toUpperCase()));
        } catch (IllegalArgumentException e) {
            // Handle the case where the payment mode is invalid
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid payment mode");
        }
        payment.setAmount(totalAmount);

        paymentRepo.addPayment(payment);

        // remove ordered products from cart
        cartService.removeOrderedProductsFromCart(userID);

        return "redirect:/cart/" + userID;
    }

    @PostMapping("/removeFromOrder")
    public String removeOrder(@RequestParam("orderID") UUID orderID, @RequestParam("productID") UUID productID, Authentication authentication) {
        if (authentication != null) {
            User currentUser = userService.getUserByEmail(authentication.getName());
            
            orderService.removeFromOrder(currentUser.getUserID(), orderID, productID);
            System.out.println("REMOVED FROM Order!!!!!!!!");
            return "redirect:/order/" + currentUser.getUserID();
        }
        return "404";
    }


    @PostMapping("/refundProduct")
    public String postMethodName(@RequestParam("productID") UUID productID, @RequestParam("orderID") UUID orderID, @RequestParam String reason, Authentication authentication) {
        if (authentication != null) {
            User currentUser = userService.getUserByEmail(authentication.getName());
            Orders order = orderService.getOrderByProductID(orderID, productID); 
            Product product = productService.getProductByID(productID);
            
            Refund refund = new Refund();
            refund.setRefundID(UUID.randomUUID());
            refund.setRefundTime(LocalDateTime.now());
            refund.setReason(reason);
            refund.setAmount(product.getPrice());
            refund.setOrderID(orderID);
            refund.setProductID(productID);

            refundRepo.addRefund(refund);

            System.out.println("Refunded !!!!!!!!");
            return "redirect:/order/" + currentUser.getUserID();
        }
        return "404";
    }
    
    @GetMapping("/refundProduct")
    public String refundProduct(@RequestParam UUID orderID, @RequestParam UUID productID, Authentication authentication, Model model) {
        User currentUser = userService.getUserByEmail(authentication.getName());
        model.addAttribute("user", currentUser);

        model.addAttribute("orderID", orderID);

        Product product = productService.getProductByID(productID);
        model.addAttribute("product", product);
        return "refundForm";
    }
    

}
