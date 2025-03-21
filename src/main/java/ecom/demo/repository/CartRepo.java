package ecom.demo.repository;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ecom.demo.models.Cart;
import ecom.demo.models.UserCart;

@Repository
public class CartRepo {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CartRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int addCart(Cart cart) {
        String sql = "INSERT INTO Cart (cartID, productID,  quantity, choose) VALUES (?, ?, ?, ?)";
        try {
            return jdbcTemplate.update(sql, cart.getCartID().toString(), cart.getProductID().toString(), cart.getQuantity(), false);
        } catch (Exception e) {
            System.out.println("Error adding cart: " + e.getMessage());
            return 0;
        }
    }

    public Cart getCartById(UUID cartID, UUID productID) {
        String sql = "SELECT * FROM Cart WHERE cartID = ? AND productID = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Cart.class), cartID.toString(), productID.toString());
        } catch (Exception e) {
            System.out.println("Error fetching cart by ID: " + e.getMessage());
            return null;
        }
    }

    public List<Cart> getSelectedCartsById(UUID cartID) {
        String sql = "SELECT * FROM Cart WHERE cartID = ? AND choose = 1"; // Fetch only selected items
        try {
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Cart.class), cartID.toString());
        } catch (Exception e) {
            System.out.println("Error fetching selected carts by ID: " + e.getMessage());
            return Collections.emptyList(); // Return empty list on error
        }
    }

    public List<Cart> getAllCarts() {
        String sql = "SELECT * FROM Cart";
        try {
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Cart.class));
        } catch (Exception e) {
            System.out.println("Error fetching all Cart: " + e.getMessage());
            return null;
        }
    }

    public int updateCart(Cart cart) {
        String sql = "UPDATE Cart SET quantity = ?, choose = ? WHERE cartID = ? AND productID = ?";
        try {
            return jdbcTemplate.update(sql, cart.getQuantity(), cart.isChoose() ,cart.getCartID().toString(), cart.getProductID().toString());
        } catch (Exception e) {
            System.out.println("Error updating cart: " + e.getMessage());
            return 0;
        }
    }

    public int deleteCart(UUID cartID) {
        String sql = "DELETE FROM Cart WHERE cartID = ?";
        try {
            return jdbcTemplate.update(sql, cartID.toString());
        } catch (Exception e) {
            System.out.println("Error deleting cart: " + e.getMessage());
            return 0;
        }
    }

    public UserCart getUserCartById(UUID userID) {
        String sql = "SELECT * FROM UserCart WHERE userID = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(UserCart.class), userID.toString());
        } catch (Exception e) {
            System.out.println("Error fetching cart by ID: " + e.getMessage());
            return null;
        }
    }

    public int addUserCart(UserCart userCart) {
        String sql = "INSERT INTO UserCart (userID, cartID) VALUES (?, ?)";
        try {
            return jdbcTemplate.update(sql, userCart.getUserID().toString(), userCart.getCartID().toString());
        } catch (Exception e) {
            System.out.println("Error adding cart: " + e.getMessage());
            return 0;
        }
    }

    public List<Cart> getCartEntriesById(UUID cartID) {
        String sql = "SELECT * FROM Cart WHERE cartID = ?";
        try {
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Cart.class), cartID.toString());
        } catch (Exception e) {
            System.out.println("Error fetching Cart by ID: " + e.getMessage());
            return null;
        }
    }

    public int removeFromCart(UUID cartID, UUID productID) {
        String sql = "DELETE FROM Cart WHERE cartID = ? AND productID = ?";
        System.out.println("entered the repo to delete product from Cart");
        try {
            return jdbcTemplate.update(sql, cartID.toString(), productID.toString());
        } catch (Exception e) {
            System.out.println("Error deleting from Cart: " + e.getMessage());
            return 0;
        }
    }

    public int removeOrderedProductsFromCart(UUID cartID) {
        String sql = "DELETE FROM Cart WHERE cartID = ? AND choose = true";
        System.out.println("entered the repo to delete ordered product from Cart");
        try {
            return jdbcTemplate.update(sql, cartID.toString());
        } catch (Exception e) {
            System.out.println("Error deleting ordered products from Cart: " + e.getMessage());
            return 0;
        }
    }

}
