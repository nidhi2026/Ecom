package ecom.demo.dto;

import java.time.LocalDate;
import java.util.UUID;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty; // Change to javax.validation
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

    private UUID userID;  // UUID for user ID
    
    @NotEmpty(message = "First Name is required")
    private String FName;

    private String MName;  // Optional Middle Name

    @NotEmpty(message = "Last Name is required")
    private String LName;

    @NotEmpty(message = "Date of Birth is required")
    private LocalDate dob;

    @Email
    @NotEmpty(message = "Email is required")
    private String email;

    @NotEmpty(message = "Password is required")
    private String password;

    private String gender;

    @NotEmpty(message = "Phone number is required")
    private String phone;
}
