package ecom.demo.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ecom.demo.dto.ProductCartDto;
import ecom.demo.models.Cart;
import ecom.demo.models.Coupons;
import ecom.demo.models.User;
import ecom.demo.models.UserCart;
import ecom.demo.repository.CartRepo;
import ecom.demo.service.CartService;
import ecom.demo.service.CouponService;
import ecom.demo.service.UserService;
import ecom.demo.service.WishListService;


@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final UserService userService;
    private final WishListService wishListService;
    private final CartRepo cartRepo;
    private final CouponService couponService;
    @Autowired
    public CartController(CartService cartService, UserService userService, WishListService wishListService, CartRepo cartRepo, CouponService couponService) {
        this.cartService = cartService;
        this.userService = userService;
        this.wishListService = wishListService;
        this.cartRepo = cartRepo;
        this.couponService = couponService;
    }
    @PostMapping("/add")
    public String addToCart(@RequestParam("productID") UUID productID,
                            @RequestParam(value = "quantity", required = false) Integer quantity,
                            Authentication authentication,
                            Model model) {

        // Set default quantity to 1 if not provided
        if (quantity == null) {
            quantity = 1; // Default value
        }

        // Retrieve the current authenticated user
        User currentUser = userService.getUserByEmail(authentication.getName());
        
        // Retrieve the user's existing Cart or create a new one
        UserCart userCart = cartService.getUserCartById(currentUser.getUserID());
        
        if (userCart == null) {
            // Create a new Cart for the user if it doesn't exist
            userCart = new UserCart();
            userCart.setUserID(currentUser.getUserID());
            userCart.setCartID(UUID.randomUUID());
            cartService.addUserCart(userCart);
        }

        // Create a new Cart entry for the product
        Cart cart = new Cart();
        cart.setCartID(userCart.getCartID());
        cart.setProductID(productID);
        cart.setQuantity(quantity);

        // Add the product to the Cart
        cartService.addCart(cart);

        // Remove the product from the wishlist if its in wishlist
        if(wishListService.isProductInWishlist(currentUser.getUserID(), productID)){

            wishListService.removeFromWishList(currentUser.getUserID(), productID);
            System.out.println("REMOVED FROM WISHLIST");}
        
        model.addAttribute("message", "Product added to your Cart!");

        System.out.println("ADDED TO CART");
        return "redirect:/products";
    }

    // @GetMapping("/{userId}")
    // public String viewCart(@PathVariable("userId") UUID userId, Model model) {
    //     List<ProductCartDto> cartProducts = cartService.getCartProductsByUserId(userId); // Assume this returns a list of products in the cart
    //     Float totalAmount = cartService.calculateTotalAmount(cartProducts);

    //     for(ProductCartDto cartProduct : cartProducts) {
    //         System.out.println("Chosen: " + cartProduct.isSelected());
    //     }

    //     model.addAttribute("cartProducts", cartProducts);
    //     model.addAttribute("totalAmount", totalAmount); 
    //     model.addAttribute("userId", userId); 
    //     return "cart";  // Name of the view template
    // }
    @GetMapping("/{userId}")
    public String viewCart(@PathVariable("userId") UUID userId, Model model) {
        List<ProductCartDto> cartProducts = cartService.getCartProductsByUserId(userId); // Assume this returns a list of products in the cart
        Float totalAmount = cartService.calculateTotalAmount(cartProducts);

        for(ProductCartDto cartProduct : cartProducts) {
            System.out.println("Chosen: " + cartProduct.isSelected());
        }
        Coupons coupon = couponService.getDiscount(totalAmount);
        model.addAttribute("cartProducts", cartProducts);
        model.addAttribute("totalAmount", totalAmount); 
        model.addAttribute("userId", userId); 
        model.addAttribute("coupon" , coupon); 
        return "cart";  // Name of the view template
    }

    // @PostMapping("/{userId}/placeOrder")
    // public String placeOrder(@PathVariable("userId") UUID userId, @RequestParam("selectedItems") List<UUID> selectedItems, Model model) {
    //     List<Product> productsToOrder = cartService.getProductsByIds(selectedItems); // Fetch only selected products
    //     BigDecimal totalAmount = calculateTotal(productsToOrder);
    //     orderService.createOrder(userId, productsToOrder, totalAmount); // Implement as needed
    //     cartService.removeItemsFromCart(userId, selectedItems); // Clean up cart items
    //     return "orderConfirmation"; // Redirect to order confirmation page
    // }

    @PostMapping("/removeFromCart")
    public String removeFromCart(@RequestParam("productID") UUID productID,
    Authentication authentication) {
        if (authentication != null) {
        User currentUser = userService.getUserByEmail(authentication.getName());
        
        cartService.removeFromCart(currentUser.getUserID(), productID);
        System.out.println("REMOVED FROM CART!!!!!!!!");
        return "redirect:/cart/" + currentUser.getUserID();
        }
        return "404";
    }


    @PostMapping("/moveToWishList")
    public String moveToWishList(@RequestParam("productID") UUID productID, Authentication authentication) {
        if (authentication != null) {
            User currentUser = userService.getUserByEmail(authentication.getName());
            UUID userID = currentUser.getUserID();
            cartService.removeFromCart(userID, productID);
            wishListService.addProductToUserWishList(userID, productID);
            cartService.removeFromCart(userID, productID);
            return "redirect:/cart/" + userID;
        }        
        // Redirect back to the cart page or any other relevant page
        return "redirect:/users/home";
    }

    @PostMapping("/updateSelect")
    public String updateSelect(@RequestParam("productID") UUID productID, @RequestParam("userID") UUID userID, @RequestParam("isChecked") boolean selected) {
        // Call your service to update the cart with the new selection state
        System.out.println(productID + " selected=" + selected + " userID=" + userID);
        cartService.updateProductSelection(userID, productID, selected);
        return "redirect:/cart/" + userID;
    }

    @PostMapping("/updateQuantity")
    public String updateQuantity(@RequestParam("productID") UUID productID, @RequestParam("userID") UUID userID,  @RequestParam("quantity") int quantity) {
        // Call your service to update the cart with the new selection state
        System.out.println(productID + " userID=" + userID);
        cartService.updateProductQuantity(userID, productID, quantity);
        return "redirect:/cart/" + userID;
    }

}
