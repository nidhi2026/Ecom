package ecom.demo.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ecom.demo.models.User;

@Repository
public class UserRepo {
    private final JdbcTemplate jdbcTemplate;
    // private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Autowired
    public UserRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int addUser(User user) {
        String sql = "INSERT INTO User (userID, FName, MName, LName, dob, email, password, gender, phone) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            return jdbcTemplate.update(sql, user.getUserID(), user.getFName(), user.getMName(), user.getLName(), user.getDob().toString(), user.getEmail(), user.getPassword(), user.getGender(), user.getPhone());
        } catch (Exception e) {
            System.out.println("Error adding user: " + e.getMessage());
            return 0;
        }

}

    public User getUserByID(String userID) {
        String sql = "SELECT * FROM User WHERE userID = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), userID);
        } catch (Exception e) {
            System.out.println("Error fetching user by ID: " + e.getMessage());
            return null;
        }
    }

    public List<User> getAllUsers() {
        String sql = "SELECT * FROM User";
        try {
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
        } catch (Exception e) {
            System.out.println("Error fetching all users: " + e.getMessage());
            return null;
        }
    }

    public int updateUser(User user) {
        String sql = "UPDATE User SET email = ?, phone = ? WHERE userID = ?";
        try {
            return jdbcTemplate.update(sql, user.getEmail(), user.getPhone(), user.getUserID());
        } catch (Exception e) {
            System.out.println("Error updating user: " + e.getMessage());
            return 0;
        }
    }

    public int deleteUser(String userID) {
        String sql = "DELETE FROM User WHERE userID = ?";
        try {
            return jdbcTemplate.update(sql, userID);
        } catch (Exception e) {
            System.out.println("Error deleting user: " + e.getMessage());
            return 0;
        }
    }

    public  User getUserByEmail(String email) {
        String sql = "SELECT * FROM User WHERE email = ?";
        User customer = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), new Object[]{email});
        return customer;
    }

    public boolean userExist(String email) {
        String sql = "SELECT * FROM User WHERE email = ?";
        try {
            User customer = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), email);
            return customer != null;
        } catch (Exception e) {
            System.out.println("Error checking if customer exists: " + e.getMessage());
            return false;
        }
    }
}