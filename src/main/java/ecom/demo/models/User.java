package ecom.demo.models;

import java.time.LocalDate;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

    private UUID userID;  // Changed to UUID

    private String FName;

    private String MName;

    private String LName;

    private LocalDate dob;

    private String email;

    private String password;

    private String gender;

    private String phone;

    // You can add a constructor to initialize userID with a random UUID
    // public User() {
    //     this.userID = UUID.randomUUID(); // Generates a random UUID when a new User is created
    // }
}
