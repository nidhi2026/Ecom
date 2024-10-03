package ecom.demo.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import ecom.demo.models.User;
import ecom.demo.repository.UserRepo;

@Service
public class UserService {

    // private BCryptPasswordEncoder passwordEncoder;

    private final UserRepo userRepository;

    public UserService(UserRepo userRepository) {
        this.userRepository = userRepository;
    }

    public int addUser(User user) {
        // user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.addUser(user);
    }

    public User getUserById(String userID) {
        return userRepository.getUserByID(userID);
    }

    public List<User> getAllUsers() {
        System.out.println(userRepository.getAllUsers());
        return userRepository.getAllUsers();
    }

    public int updateUser(User user) {
        return userRepository.updateUser(user);
    }

    public int deleteUser(String userID) {
        return userRepository.deleteUser(userID);
    }

    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    public boolean userExists(String email) {
        return userRepository.userExist(email);
    }
}
