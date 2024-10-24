package ecom.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ecom.demo.models.Category;

@Repository
public class CategoryRepo {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CategoryRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Method to add a new category
    public int addCategory(Category category) {
        String sql = "INSERT INTO Categories (categoryID, categoryName) VALUES (?, ?)";
        try {
            return jdbcTemplate.update(sql,
                category.getCategoryID().toString(),
                category.getCategoryName());
        } catch (Exception e) {
            System.out.println("Error adding category: " + e.getMessage());
            return 0;
        }
    }

    // Method to get a category by ID
    public Category getCategoryById(UUID categoryID) {
        String sql = "SELECT * FROM Categories WHERE categoryID = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Category.class), categoryID.toString());
        } catch (Exception e) {
            System.out.println("Error fetching category by ID: " + e.getMessage());
            return null;
        }
    }

    // Method to get all categories
    public List<Category> getAllCategories() {
        String sql = "SELECT * FROM Categories";
        try {
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Category.class));
        } catch (Exception e) {
            System.out.println("Error fetching all categories: " + e.getMessage());
            return List.of(); // Return empty list in case of error
        }
    }

    // Method to update a category
    public int updateCategory(Category category) {
        String sql = "UPDATE Categories SET categoryName = ? WHERE categoryID = ?";
        try {
            return jdbcTemplate.update(sql,
                category.getCategoryName(),
                category.getCategoryID().toString());
        } catch (Exception e) {
            System.out.println("Error updating category: " + e.getMessage());
            return 0;
        }
    }

    // Method to delete a category by ID
    public int deleteCategory(UUID categoryID) {
        String sql = "DELETE FROM Categories WHERE categoryID = ?";
        try {
            return jdbcTemplate.update(sql, categoryID.toString());
        } catch (Exception e) {
            System.out.println("Error deleting category: " + e.getMessage());
            return 0;
        }
    }
}
