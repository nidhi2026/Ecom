package ecom.demo.service;

import ecom.demo.models.WishList;
import ecom.demo.models.Product;
import ecom.demo.models.UserWishList;
import ecom.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.time.LocalDateTime;

@Service
public class WishListService {

    private final WishListRepo wishListRepo;
    private final ProductRepo productRepo;
    @Autowired
    public WishListService(WishListRepo wishListRepo, ProductRepo producttRepo) {
        this.wishListRepo = wishListRepo;
        this.productRepo =  producttRepo;
    }

    public int addWishList(WishList wishList) {
        return wishListRepo.addWishList(wishList);
    }

    public List<WishList> getAllWishLists() {
        return wishListRepo.getAllWishLists();
    }

    // public WishList getWishListById(UUID wishlistID) {
    //     return wishListRepo.getWishListById(wishlistID);
    // }

    public int deleteWishList(UUID wishlistID) {
        return wishListRepo.deleteWishList(wishlistID);
    }

    public UserWishList getUserWishListById(UUID userID){
        return wishListRepo.getUserWishListById(userID);
    }

    public int addUserWishList(UserWishList userWishList){
        return wishListRepo.addUserWishList( userWishList);
    }

    public boolean isProductInWishlist(UUID userID, UUID productID) {
        // Fetch the user's wishlist based on userID
        System.out.println("wait a min, checking whether the product is in wishlist?!");
        UserWishList userWishList = wishListRepo.getUserWishListById(userID);
        
        if (userWishList == null) {
            System.out.println("uh oh, user hasnt made a wishlist yet!");
            return false; // If user has no wishlist, return false
        }

        UUID wishlistID = userWishList.getWishlistID(); // Get the wishlistID
        System.out.println("trying to get the wishlistID........");
        WishList wishList = wishListRepo.getWishListByproductId(wishlistID, productID);
        // Check if the product exists in the wishlist

        if(wishList != null) {
            System.out.println("woo, found the product in wishlist :) ");
            return true;
        }else{
            System.out.println("not in wishlist!");
            return false;
        }
        
    }

    public int removeFromWishList(UUID userID, UUID productID){
        UserWishList userWishList = wishListRepo.getUserWishListById(userID);
        
        if (userWishList == null) {
            System.out.println("no wishlist made!111");
            return 0; // If user has no wishlist, return false
        }

        UUID wishlistID = userWishList.getWishlistID(); // Get the wishlistID
        System.out.println("trying to remove the product from wishlist");
        return wishListRepo.removeFromWishList(wishlistID, productID);
    }

    public List<Product> getWishlistProductsByUserId(UUID userID) {
        UserWishList userWishList = wishListRepo.getUserWishListById(userID);
    
        if (userWishList == null) {
            return Collections.emptyList();
        }
    
        UUID wishlistID = userWishList.getWishlistID(); // Get the wishlistID
        List<WishList> wishlistEntries = wishListRepo.getWishListEntriesById(wishlistID);
    
        List<Product> wishlistProducts = new ArrayList<>();
        for (WishList entry : wishlistEntries) {
            Optional<Product> optionalProduct = productRepo.getProductByID(entry.getProductID());
            
            
    System.out.println("Product ID: " + entry.getProductID());
    
    // If the product is present, add it to the list
    optionalProduct.ifPresent(product -> {
        wishlistProducts.add(product);
        // Print product details
        System.out.println("Product Details:");
        System.out.println("Name: " + product.getProductName());
        System.out.println("Images: " + product.getImages());
        // Add any other product details you want to display
    });
        }
    
        return wishlistProducts;
    }


    public void addProductToUserWishList(UUID userId, UUID productId) {
        // Retrieve the user's existing wishlist or create a new one
        UserWishList userWishList =  getUserWishListById(userId);
        
        if (userWishList == null) {
            // Create a new wishlist for the user if it doesn't exist
            userWishList = new UserWishList();
            userWishList.setUserID(userId);
            userWishList.setWishlistID(UUID.randomUUID());
            addUserWishList(userWishList);
        }

        // Create and save a new wishlist entry for the product
        WishList wishList = new WishList();
        wishList.setWishlistID(userWishList.getWishlistID());
        wishList.setProductID(productId);
        wishList.setAddTime(LocalDateTime.now());

        addWishList(wishList);
    }
    
}
