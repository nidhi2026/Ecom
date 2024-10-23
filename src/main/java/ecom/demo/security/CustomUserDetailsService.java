package ecom.demo.security;

import ecom.demo.models.User;
import ecom.demo.repository.UserRepo;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.ArrayList;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepo userRepository;

    public CustomUserDetailsService(UserRepo userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.getUserByEmail(email);

        if (user != null) {
            // Return the UserDetails without roles
            return new org.springframework.security.core.userdetails.User(user.getEmail(),
                    user.getPassword(),
                    new ArrayList<>()); // Empty authorities list
        } else {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
    }

   
}