package ecom.demo.service;

import ecom.demo.models.WishList;
import ecom.demo.repository.WishListRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class WishListService {

    private final WishListRepo wishListRepo;

    @Autowired
    public WishListService(WishListRepo wishListRepo) {
        this.wishListRepo = wishListRepo;
    }

    public int addWishList(WishList wishList) {
        return wishListRepo.addWishList(wishList);
    }

    public List<WishList> getAllWishLists() {
        return wishListRepo.getAllWishLists();
    }

    public WishList getWishListById(UUID wishlistID) {
        return wishListRepo.getWishListById(wishlistID);
    }

    public int deleteWishList(UUID wishlistID) {
        return wishListRepo.deleteWishList(wishlistID);
    }
}
