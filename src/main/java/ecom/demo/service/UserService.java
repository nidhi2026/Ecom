// package ecom.demo.service;

// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.stereotype.Service;
// import ecom.demo.models.User;
// import ecom.demo.dto.UserDto; // Import the UserDto
// import ecom.demo.repository.UserRepo;
// import java.util.List;
// import java.util.UUID;

// @Service
// public class UserService {

//     private final UserRepo userRepository;
//     private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

//     public UserService(UserRepo userRepository) {
//         this.userRepository = userRepository;
//     }

//     // Register a new user with hashed password
//     public int addUser(UserDto userDto) {
//         System.out.println("Adding user, huh!!");
        
//         // Convert UserDto to User entity
//         User user = new User();
//         user.setUserID(UUID.randomUUID());
//         user.setFName(userDto.getFName());
//         user.setMName(userDto.getMName());
//         user.setLName(userDto.getLName());
//         user.setDob(userDto.getDob());
//         user.setEmail(userDto.getEmail());
//         user.setPassword(passwordEncoder.encode(userDto.getPassword())); // Hash the password
//         user.setGender(userDto.getGender());
//         user.setPhone(userDto.getPhone());

//         System.out.println(user.getUserID());
        
//         return userRepository.addUser(user);
//     }

//     // Validate user login
//     public boolean validateUser(String email, String rawPassword) {
//         User user = userRepository.getUserByEmail(email);
//         System.out.println(email);
//         if (user != null) {
//             // Compare raw password with the hashed password in the database
//             System.out.println(rawPassword);
//             System.out.println(user.getPassword());
//             return passwordEncoder.matches(rawPassword, user.getPassword());
            
//         }
//         return false;
//     }

//     public User getUserById(String userID) {
//         return userRepository.getUserByID(userID);
//     }
//     public User getUserByEmail(String email){
//         return userRepository.getUserByEmail(email);
//     }
//     public List<User> getAllUsers() {
//         return userRepository.getAllUsers();
//     }

//     public int updateUser(UserDto userDto) {
//         // Convert UserDto to User entity
//         User user = new User();
//         user.setUserID(userDto.getUserID());
//         user.setFName(userDto.getFName());
//         user.setMName(userDto.getMName());
//         user.setLName(userDto.getLName());
//         user.setDob(userDto.getDob());
//         user.setEmail(userDto.getEmail());
//         user.setGender(userDto.getGender());
//         user.setPhone(userDto.getPhone());
        
//         return userRepository.updateUser(user);
//     }

//     public int deleteUser(String userID) {
//         return userRepository.deleteUser(userID);
//     }

//     public boolean userExists(String email) {
//         return userRepository.userExist(email);
//     }
  
// }

package ecom.demo.service;

import java.util.List;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ecom.demo.dto.UserDto;
import ecom.demo.models.User;
import ecom.demo.repository.UserRepo;

@Service
public class UserService {

    private final UserRepo userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepo userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Register a new user with hashed password
    public int addUser(UserDto userDto) {
        System.out.println("Adding user, huh!!");

        // Convert UserDto to User entity
        User user = new User();
        user.setUserID(UUID.randomUUID());
        user.setFName(userDto.getFName());
        user.setMName(userDto.getMName());
        user.setLName(userDto.getLName());
        user.setDob(userDto.getDob());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword())); // Hash the password
        user.setGender(userDto.getGender());
        user.setPhone(userDto.getPhone());
        user.setRole(userDto.getRole());

        System.out.println(user.getUserID());

        return userRepository.addUser(user);
    }

    // Validate user login
    public boolean validateUser(String email, String rawPassword) {
        User user = userRepository.getUserByEmail(email);
        System.out.println(email);
        if (user != null) {
            // Compare raw password with the hashed password in the database
            System.out.println(rawPassword);
            System.out.println(user.getPassword());
            return passwordEncoder.matches(rawPassword, user.getPassword());
        }
        return false;
    }

    public User getUserById(UUID userID) {
        return userRepository.getUserByID(userID);
    }

    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public int updateUser(UserDto userDto) {
        // Convert UserDto to User entity
        User user = new User();
        user.setUserID(userDto.getUserID());
        user.setFName(userDto.getFName());
        user.setMName(userDto.getMName());
        user.setLName(userDto.getLName());
        user.setDob(userDto.getDob());
        user.setEmail(userDto.getEmail());
        user.setGender(userDto.getGender());
        user.setPhone(userDto.getPhone());

        return userRepository.updateUser(user);
    }

    public void updateUser(UUID userID, UserDto userDto) {
        userRepository.updateUser(userID, userDto); // Update user details in the database
    }
    

    public int deleteUser(String userID) {
        return userRepository.deleteUser(userID);
    }

    public boolean userExists(String email) {
        return userRepository.userExist(email);
    }
}

