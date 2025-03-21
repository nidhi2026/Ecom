package ecom.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService customUserDetailsService;

    @Autowired
    public SecurityConfig(UserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth, @Lazy PasswordEncoder passwordEncoder) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            )
            // .authorizeHttpRequests(requests -> requests
            //     .requestMatchers("/users/register/**", "/users/login", "/css/**", "/js/**", "/products/**").permitAll()
            //     .requestMatchers("/wishlist/**").authenticated()
            //     .anyRequest().authenticated()
            // )
            .authorizeHttpRequests(requests -> requests
                .requestMatchers("/users/register/", "/users/login", "/css/", "/js/","/products", "/products/").permitAll()
                .requestMatchers("/wishlist/").authenticated()
                .requestMatchers("/admin/").hasRole("ADMIN")
                .requestMatchers("/supplier/").hasAnyRole("SUPPLIER", "ADMIN")
                .requestMatchers("/user/").hasAnyRole("USER", "SUPPLIER", "ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/users/login")
                .loginProcessingUrl("/users/login")
                .usernameParameter("email")
                .passwordParameter("password")
                // .defaultSuccessUrl("/users/home", true)
                .successHandler(customAuthenticationSuccessHandler()) 
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/users/logout")
                .logoutSuccessUrl("/users/login")
                .invalidateHttpSession(true)
                .clearAuthentication(true)

                .deleteCookies("JSESSIONID") // Delete session cookies
                .permitAll()
            );

        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setDefaultTargetUrl("/base/"); // Redirect here if there's no saved request
        successHandler.setRequestCache(requestCache());
        return successHandler;
    }

    // Request cache to store the original URL before login
    @Bean
    public RequestCache requestCache() {
        return new HttpSessionRequestCache();
    }
}
