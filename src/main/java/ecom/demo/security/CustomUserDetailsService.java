package ecom.demo.security;

import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ecom.demo.models.User;
import ecom.demo.repository.UserRepo;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepo userRepository;

    public CustomUserDetailsService(UserRepo userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.getUserByEmail(email);

        if (user != null) {
            System.out.println("User found: " + user.getFName());

            // Create authority based on the user's role
            GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole()); // Prefix ROLE_ for Spring Security

            return new org.springframework.security.core.userdetails.User(user.getEmail(),
                    user.getPassword(),
                    Collections.singletonList(authority)); // Add the authority to the list
        } else {
            System.out.println("User not found for email: " + email);
            throw new UsernameNotFoundException("Invalid username or password.");
        }
    }
}