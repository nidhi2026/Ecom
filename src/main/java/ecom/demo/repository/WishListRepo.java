package ecom.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ecom.demo.models.WishList;

@Repository
public class WishListRepo {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public WishListRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int addWishList(WishList wishList) {
        String sql = "INSERT INTO WishList (wishlistID, productID, addTime, userID) VALUES (?, ?, ?, ?)";
        try {
            return jdbcTemplate.update(sql, wishList.getWishlistID().toString(), wishList.getProductID().toString(), wishList.getAddTime(), wishList.getUserID().toString());
        } catch (Exception e) {
            System.out.println("Error adding wishlist: " + e.getMessage());
            return 0;
        }
    }

    public WishList getWishListById(UUID wishlistID) {
        String sql = "SELECT * FROM WishList WHERE wishlistID = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(WishList.class), wishlistID.toString());
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
        String sql = "UPDATE WishList SET productID = ?, addTime = ?, userID = ? WHERE wishlistID = ?";
        try {
            return jdbcTemplate.update(sql, wishList.getProductID().toString(), wishList.getAddTime(), wishList.getUserID().toString(), wishList.getWishlistID().toString());
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
}
