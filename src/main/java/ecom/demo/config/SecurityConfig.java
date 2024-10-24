// package ecom.demo.config;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


// import ecom.demo.security.CustomUserDetailsService;

// @Configuration
// @EnableWebSecurity
// public class SecurityConfig {

//     private final UserDetailsService customUserDetailsService;

//     public SecurityConfig(UserDetailsService customUserDetailsService) {
//         this.customUserDetailsService = customUserDetailsService;
//     }

//     @Bean
//     public PasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder();
//     }

//     @Bean
//     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//         http
//             .authorizeHttpRequests((requests) -> requests
//                 .requestMatchers("/users/register", "/users/login", "/css/**", "/js/**").permitAll() // Allow public access to these endpoints
//                 .anyRequest().authenticated()  // All other requests require authentication
//             )
//             .formLogin((form) -> form
//                 .loginPage("/users/login")  // Custom login page
//                 .loginProcessingUrl("/users/login")  // The URL to submit the login form
//                 .usernameParameter("email")  // Use "email" instead of "username"
//                 .passwordParameter("password")  // Ensure the password parameter is named correctly
//                 .defaultSuccessUrl("/users/home", true)  // Redirect to home after successful login
//                 .permitAll()
//             )
//             .logout((logout) -> logout
//                 .logoutRequestMatcher(new AntPathRequestMatcher("/users/logout"))
//                 .logoutSuccessUrl("/users/login")
//                 .permitAll()
//             );

//         return http.build();
//     }
// }

package ecom.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService customUserDetailsService;

    public SecurityConfig(UserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF protection
            .authorizeHttpRequests(requests -> requests
                .requestMatchers("/users/register/**", "/users/login", "/css/**", "/js/**", "/products/**").permitAll() // Allow public access to these endpoints
                .requestMatchers("/wishlist/**").authenticated() // Require authentication for wishlist
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/users/login")  // Custom login page
                .loginProcessingUrl("/users/login")  // The URL to submit the login form
                .usernameParameter("email")  // Use "email" instead of "username"
                .passwordParameter("password")  // Ensure the password parameter is named correctly
                .defaultSuccessUrl("/users/home", true)  // Redirect to home after successful login
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/users/logout")
                .logoutSuccessUrl("/users/login")
                .permitAll()
            );

        return http.build();
    }
}

