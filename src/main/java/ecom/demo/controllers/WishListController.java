
package ecom.demo.controllers;

import ecom.demo.models.WishList;
import ecom.demo.models.User;
import ecom.demo.service.WishListService;
import ecom.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.UUID;

@Controller
public class WishListController {

    private final WishListService wishListService;
    private final UserService userService;

    @Autowired
    public WishListController(WishListService wishListService, UserService userService) {
        this.wishListService = wishListService;
        this.userService = userService;
    }

    @PostMapping("/wishlist/add")
public String addToWishList(@RequestParam("productID") UUID productID, Authentication authentication, Model model) {
    // Spring Security automatically ensures the user is authenticated
    System.out.println("yo! adding to wishlist");
    User currentUser = userService.getUserByEmail(authentication.getName());

    // Create a new wishlist entry
    WishList wishList = new WishList();
    wishList.setWishlistID(UUID.randomUUID());
    wishList.setProductID(productID);
    wishList.setAddTime(LocalDateTime.now());
    wishList.setUserID(currentUser.getUserID());

    // Add the wishlist item
    wishListService.addWishList(wishList);
    System.out.println("yahooo! added to wishlist");
    model.addAttribute("message", "Product added to your wishlist!");
    return "redirect:/products"; // Redirect to the product page after adding
}
}