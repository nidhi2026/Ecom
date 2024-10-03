package ecom.demo.models;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

    private String userID;

    private String FName;

    private String MName;

    private String LName;

    private LocalDate dob;

    private String email;

    private String password;

    private String gender;

    private String phone;

}
