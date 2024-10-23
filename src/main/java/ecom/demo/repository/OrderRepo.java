package ecom.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ecom.demo.models.Orders;

@Repository
public class OrderRepo {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public OrderRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int addOrder(Orders order) {
        String sql = "INSERT INTO Orders (orderID, productID, userID, orderDate) VALUES (?, ?, ?, ?)";
        try {
            return jdbcTemplate.update(sql, order.getOrderID().toString(), order.getProductID().toString(), order.getUserID().toString(), order.getOrderDate());
        } catch (Exception e) {
            System.out.println("Error adding order: " + e.getMessage());
            return 0;
        }
    }

    public Orders getOrderById(UUID orderID) {
        String sql = "SELECT * FROM Orders WHERE orderID = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Orders.class), orderID.toString());
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
        String sql = "UPDATE Orders SET productID = ?, userID = ?, orderDate = ? WHERE orderID = ?";
        try {
            return jdbcTemplate.update(sql, order.getProductID().toString(), order.getUserID().toString(), order.getOrderDate(), order.getOrderID().toString());
        } catch (Exception e) {
            System.out.println("Error updating order: " + e.getMessage());
            return 0;
        }
    }

    public int deleteOrder(UUID orderID) {
        String sql = "DELETE FROM Orders WHERE orderID = ?";
        try {
            return jdbcTemplate.update(sql, orderID.toString());
        } catch (Exception e) {
            System.out.println("Error deleting order: " + e.getMessage());
            return 0;
        }
    }
}
