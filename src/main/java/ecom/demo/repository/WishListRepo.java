package ecom.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ecom.demo.models.UserWishList;
import ecom.demo.models.WishList;
@Repository
public class WishListRepo {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public WishListRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int addWishList(WishList wishList) {
        String sql = "INSERT INTO WishList (wishlistID, productID, addTime) VALUES (?, ?, ?)";
        try {
            return jdbcTemplate.update(sql, wishList.getWishlistID().toString(), wishList.getProductID().toString(), wishList.getAddTime());
        } catch (Exception e) {
            System.out.println("Error adding wishlist: " + e.getMessage());
            return 0;
        }
    }

    public List<WishList> getWishListEntriesById(UUID wishlistID) {
        String sql = "SELECT * FROM WishList WHERE wishlistID = ?";
        try {
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(WishList.class), wishlistID.toString());
        } catch (Exception e) {
            System.out.println("Error fetching wishlist by ID: " + e.getMessage());
            return null;
        }
    }

    public List<WishList> getAllWishLists() {
        String sql = "SELECT * FROM WishList";
        try {
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(WishList.class));
        } catch (Exception e) {
            System.out.println("Error fetching all wishlists: " + e.getMessage());
            return null;
        }
    }

    public int updateWishList(WishList wishList) {
        String sql = "UPDATE WishList SET productID = ?, addTime = ? WHERE wishlistID = ?";
        try {
            return jdbcTemplate.update(sql, wishList.getProductID().toString(), wishList.getAddTime(), wishList.getWishlistID().toString());
        } catch (Exception e) {
            System.out.println("Error updating wishlist: " + e.getMessage());
            return 0;
        }
    }

    public int deleteWishList(UUID wishlistID) {
        String sql = "DELETE FROM WishList WHERE wishlistID = ?";
        try {
            return jdbcTemplate.update(sql, wishlistID.toString());
        } catch (Exception e) {
            System.out.println("Error deleting wishlist: " + e.getMessage());
            return 0;
        }
    }


    public UserWishList getUserWishListById(UUID userID) {
        String sql = "SELECT * FROM UserWishList WHERE userID = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(UserWishList.class), userID.toString());
        } catch (Exception e) {
            System.out.println("Error fetching wishlist by ID: " + e.getMessage());
            return null;
        }
    }

    public int addUserWishList(UserWishList userWishList) {
        System.out.println("adding to userwishlist");
        String sql = "INSERT INTO UserWishList (userID, wishListID) VALUES ( ?, ?)";
        try {
            return jdbcTemplate.update(sql,userWishList.getUserID().toString(), userWishList.getWishlistID().toString());
          

        } catch (Exception e) {
            System.out.println("Error adding Userwishlist: " + e.getMessage());
            return 0;
        }
    }

    public WishList getWishListByproductId(UUID wishlistID, UUID productID) {
        String sql = "SELECT * FROM WishList WHERE wishlistID = ? AND productID = ?";
        System.out.println("entered repo to know whete the product is in the wishlist");
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(WishList.class), wishlistID.toString(), productID.toString());
        } catch (Exception e) {
            System.out.println("Error fetching wishlist by userID and productID: " + e.getMessage());
            return null;
        }
    }


    public int removeFromWishList(UUID wishlistID, UUID productID) {
        String sql = "DELETE FROM WishList WHERE wishlistID = ? AND productID = ?";
        System.out.println("entered the repo to delete product from wishlist");
        try {
            return jdbcTemplate.update(sql, wishlistID.toString(), productID.toString());
        } catch (Exception e) {
            System.out.println("Error deleting from wishlist: " + e.getMessage());
            return 0;
        }
    }

}
