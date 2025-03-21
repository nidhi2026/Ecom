package ecom.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ecom.demo.models.Orders;
import ecom.demo.models.Tracking;
import ecom.demo.models.UserOrder;

@Repository
public class OrderRepo {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public OrderRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int addOrder(Orders order) {
        String sql = "INSERT INTO Orders (orderID, productID, quantity, addressID) VALUES (?, ?, ?, ?)";
        try {
            return jdbcTemplate.update(sql, order.getOrderID().toString(), order.getProductID().toString(), order.getQuantity(), order.getAddressID().toString());
        } catch (Exception e) {
            System.out.println("Error adding order: " + e.getMessage());
            return 0;
        }
    }

    public int addUserOrder(UserOrder userOrder) {
        String sql = "INSERT INTO UserOrder VALUES (?, ?, ?)";
        try {
            return jdbcTemplate.update(sql, userOrder.getUserID().toString(), userOrder.getOrderID().toString(), userOrder.getDate());
        } catch (Exception e) {
            System.out.println("Error adding userorder: " + e.getMessage());
            return 0;
        }
    }

    public List<UserOrder> getUserOrderById(UUID userID) {
        String sql = "SELECT * FROM UserOrder WHERE userID = ?";
        try {
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(UserOrder.class), userID.toString());
        } catch (Exception e) {
            System.out.println("Error fetching order by ID: " + e.getMessage());
            return null;
        }
    }

    public List<Orders> getOrdersById(UUID orderID) {
        String sql = "SELECT * FROM Orders WHERE orderID = ?";
        try {
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Orders.class), orderID.toString());
        } catch (Exception e) {
            System.out.println("Error fetching order by ID: " + e.getMessage());
            return null;
        }
    }

    public Orders getOrderByProductID(UUID orderID, UUID productID) {
        String sql = "SELECT * FROM Orders WHERE orderID = ? AND productID = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Orders.class), orderID.toString(), productID.toString());
        } catch (Exception e) {
            System.out.println("Error fetching order by ID: " + e.getMessage());
            return null;
        }
    }

    public List<Orders> getAllOrders() {
        String sql = "SELECT * FROM Orders";
        try {
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Orders.class));
        } catch (Exception e) {
            System.out.println("Error fetching all orders: " + e.getMessage());
            return null;
        }
    }

    public int updateOrder(Orders order) {
        String sql = "UPDATE Orders SET productID = ?, userID = ?, quantity = ?";
        try {
            return jdbcTemplate.update(sql, order.getProductID().toString(), order.getQuantity());
        } catch (Exception e) {
            System.out.println("Error updating order: " + e.getMessage());
            return 0;
        }
    }

    public int removeOrder(UUID orderID, UUID productID) {
        String sql = "DELETE FROM Orders WHERE orderID = ? AND productID = ?";
        try {
            return jdbcTemplate.update(sql, orderID.toString(), productID.toString());
        } catch (Exception e) {
            System.out.println("Error deleting order: " + e.getMessage());
            return 0;
        }
    }

    public int removeUserOrder(UUID userID, UUID orderID) {
        String sql = "DELETE FROM UserOrder WHERE orderID = ? AND userID = ?";
        try {
            return jdbcTemplate.update(sql, orderID.toString(), userID.toString());
        } catch (Exception e) {
            System.out.println("Error deleting user order: " + e.getMessage());
            return 0;
        }
    }

    public boolean isOrderEmpty(UUID orderID) {
        String sql = "SELECT COUNT(*) FROM Orders WHERE orderID = ?";
        try {
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class, orderID.toString());
            // If count is 0, it means the order does not exist or is empty
            return count == null || count == 0;
        } catch (Exception e) {
            System.out.println("Error fetching order status: " + e.getMessage());
            return false; // Consider it empty if there is an error fetching data
        }
    }
    

    public Tracking getTrackingByOrderID(UUID orderID) {
        String sql = "SELECT * from Tracking WHERE orderID = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Tracking.class), orderID.toString());
        }catch (Exception e) {
            System.out.println("Error in tracking: " + e.getMessage());
            return null;
        }
    }
}
