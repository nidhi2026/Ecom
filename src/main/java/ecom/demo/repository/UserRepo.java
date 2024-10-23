package ecom.demo.repository;

import ecom.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class UserRepo {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int addUser(User user) {
        String sql = "INSERT INTO User (userID, FName, MName, LName, dob, email, password, gender, phone) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            System.out.println("yaoiiii , Attempting to add user: " + user);
            return jdbcTemplate.update(sql, user.getUserID().toString(), user.getFName(), user.getMName(), user.getLName(), 
                                       user.getDob() != null ? user.getDob().toString() : null, 
                                       user.getEmail(), user.getPassword(), user.getGender(), user.getPhone());
        } catch (Exception e) {
            System.out.println("Error adding user: " + e.getMessage());
            return 0;
        }
    }
    

    public User getUserByID(String userID) {
        String sql = "SELECT * FROM User WHERE userID = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), userID);
    }

    public List<User> getAllUsers() {
        String sql = "SELECT * FROM User";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
    }

    public int updateUser(User user) {
        String sql = "UPDATE User SET email = ?, phone = ? WHERE userID = ?";
        return jdbcTemplate.update(sql, user.getEmail(), user.getPhone(), user.getUserID());
    }

    public int deleteUser(String userID) {
        String sql = "DELETE FROM User WHERE userID = ?";
        return jdbcTemplate.update(sql, userID);
    }

    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM User WHERE email = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), email);
    }

    public boolean userExist(String email) {
        String sql = "SELECT COUNT(*) FROM User WHERE email = ?"; // Use COUNT to avoid fetching the entire User object
        try {
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
            return count != null && count > 0; // Return true if count is greater than 0
        } catch (Exception e) {
            return false; // Return false if there's an exception (user does not exist)
        }
    }
    
}
