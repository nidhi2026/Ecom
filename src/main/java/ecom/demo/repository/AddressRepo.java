package ecom.demo.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ecom.demo.models.Address;

@Repository
public class AddressRepo {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AddressRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Method to add a new address
    public int addAddress(Address address) {
        String sql = "INSERT INTO Address (addressID, personID, streetName, cityName, districtName, pincode, countryName) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            return jdbcTemplate.update(sql,
                address.getAddressID().toString(),
                address.getPersonID().toString(),
                address.getStreetName(),
                address.getCityName(),
                address.getDistrictName(),
                address.getPincode(),
                address.getCountryName());
        } catch (Exception e) {
            System.out.println("Error adding address: " + e.getMessage());
            return 0;
        }
    }

    // Method to get an address by ID
    public Optional<Address> getAddressByID(UUID addressID) {
        String sql = "SELECT * FROM Address WHERE addressID = ?";
        try {
            return Optional.of(jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Address.class), addressID.toString()));
        } catch (Exception e) {
            System.out.println("Error fetching address by ID: " + e.getMessage());
            return Optional.empty();
        }
    }

    // Method to get address using user ID
    public Optional<Address> getAddressByUserID(String personID) {
        String sql = "SELECT * FROM Address WHERE personID = ?";
        try {
            return Optional.of(jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Address.class), personID));
        } catch (Exception e) {
            System.out.println("Error fetching address by person ID: " + e.getMessage());
            return Optional.empty();
        }
    }

    // Method to get all addresses
    public List<Address> getAllAddresses() {
        String sql = "SELECT * FROM Address";
        try {
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Address.class));
        } catch (Exception e) {
            System.out.println("Error fetching all addresses: " + e.getMessage());
            return List.of(); // Return empty list if an error occurs
        }
    }

    // Method to update an address
    public int updateAddress(Address address) {
        String sql = "UPDATE Address SET personID = ?, streetName = ?, cityName = ?, districtName = ?, pincode = ?, countryName = ? WHERE addressID = ?";
        try {
            return jdbcTemplate.update(sql,
                address.getPersonID().toString(),
                address.getStreetName(),
                address.getCityName(),
                address.getDistrictName(),
                address.getPincode(),
                address.getCountryName(),
                address.getAddressID().toString());
        } catch (Exception e) {
            System.out.println("Error updating address: " + e.getMessage());
            return 0;
        }
    }

    // Method to delete an address by ID
    public int deleteAddress(UUID addressID) {
        String sql = "DELETE FROM Address WHERE addressID = ?";
        try {
            return jdbcTemplate.update(sql, addressID.toString());
        } catch (Exception e) {
            System.out.println("Error deleting address: " + e.getMessage());
            return 0;
        }
    }
}
