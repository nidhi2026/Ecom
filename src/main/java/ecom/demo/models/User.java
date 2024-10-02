package ecom.demo.models;

import java.time.LocalDate;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

    private UUID userID;

    private String FName;

    private String MName;

    private String LName;

    private LocalDate dob;

    private String email;

    private String password;

    private String gender;

    private String phone;

    public User(UUID userID, String fName, String mName, String lName, LocalDate dob, String email, String gender,
            String phone) {
        this.userID = userID;
        this.FName = fName;
        this.MName = mName;
        this.LName = lName;
        this.dob = dob;
        this.email = email;
        this.gender = gender;
        this.phone = phone;
    }

    

}
