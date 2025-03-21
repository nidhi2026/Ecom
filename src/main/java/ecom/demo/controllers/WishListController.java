
package ecom.demo.controllers;

// import ecom.demo.models.WishList;
import ecom.demo.models.Product;
import ecom.demo.models.User;
// import ecom.demo.models.UserWishList;
import ecom.demo.service.WishListService;
import ecom.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

// import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/wishlist")
public class WishListController {

    private final WishListService wishListService;
    private final UserService userService;

    @Autowired
    public WishListController(WishListService wishListService, UserService userService) {
        this.wishListService = wishListService;
        this.userService = userService;
    }

    @PostMapping("/add")
    public String addToWishList(@RequestParam("productID") UUID productID, Authentication authentication, Model model) {
        // Ensure the user is authenticated
        User currentUser = userService.getUserByEmail(authentication.getName());

        // Use the service to add the product to the user's wishlist
        wishListService.addProductToUserWishList(currentUser.getUserID(), productID);

        model.addAttribute("message", "Product added to your wishlist!");
        return "redirect:/products/" + productID;
    }


    @PostMapping("/remove")
    public String removeFromWishList(@RequestParam("productID") UUID productID,
                                    Authentication authentication) {
        if (authentication != null) {
            User currentUser = userService.getUserByEmail(authentication.getName());
            System.out.println("trying to remove product, well will fecth the service right away!");
            wishListService.removeFromWishList(currentUser.getUserID(), productID);
        }else{
            return "404";
        }
        System.out.println("REMOVED!!!!!!!!");
        return "redirect:/products/" + productID; // Redirect back to the product details page
    }

    @GetMapping("/{userId}")
    public String getUserWishlist(@PathVariable("userId") UUID userId, Model model) {
        List<Product> wishlistProducts = wishListService.getWishlistProductsByUserId(userId);
        model.addAttribute("wishlistProducts", wishlistProducts);
        return "wishlist";
    }
}