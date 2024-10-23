package ecom.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ecom.demo.models.Cart;

@Repository
public class CartRepo {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CartRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int addCart(Cart cart) {
        String sql = "INSERT INTO Carts (cartID, productID, addTime, userID, quantity) VALUES (?, ?, ?, ?, ?)";
        try {
            return jdbcTemplate.update(sql, cart.getCartID().toString(), cart.getProductID().toString(), cart.getAddTime(), cart.getUserID().toString(), cart.getQuantity());
        } catch (Exception e) {
            System.out.println("Error adding cart: " + e.getMessage());
            return 0;
        }
    }

    public Cart getCartById(UUID cartID) {
        String sql = "SELECT * FROM Carts WHERE cartID = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Cart.class), cartID.toString());
        } catch (Exception e) {
            System.out.println("Error fetching cart by ID: " + e.getMessage());
            return null;
        }
    }

    public List<Cart> getAllCarts() {
        String sql = "SELECT * FROM Carts";
        try {
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Cart.class));
        } catch (Exception e) {
            System.out.println("Error fetching all carts: " + e.getMessage());
            return null;
        }
    }

    public int updateCart(Cart cart) {
        String sql = "UPDATE Carts SET productID = ?, addTime = ?, userID = ?, quantity = ? WHERE cartID = ?";
        try {
            return jdbcTemplate.update(sql, cart.getProductID().toString(), cart.getAddTime(), cart.getUserID().toString(), cart.getQuantity(), cart.getCartID().toString());
        } catch (Exception e) {
            System.out.println("Error updating cart: " + e.getMessage());
            return 0;
        }
    }

    public int deleteCart(UUID cartID) {
        String sql = "DELETE FROM Carts WHERE cartID = ?";
        try {
            return jdbcTemplate.update(sql, cartID.toString());
        } catch (Exception e) {
            System.out.println("Error deleting cart: " + e.getMessage());
            return 0;
        }
    }
}
