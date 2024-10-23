package ecom.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

// import ecom.demo.dto.LoginDto;
import ecom.demo.dto.UserDto;
import ecom.demo.service.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // Handler method to handle home page request
    @GetMapping("/home")
    public String home() {
        System.out.println("yo! feel at home");
        return "index";
    }

    // Handler method to handle user registration form request
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        UserDto user = new UserDto();
        System.out.println("eeeentering reg")
        ;
        model.addAttribute("user", user);
        System.out.println("calling reg html");
        return "register";
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult result,
                               Model model) {
        // Check if a user with the given email already exists
        System.out.println("reg save");
        if (userService.userExists(userDto.getEmail())) {
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        // If there are validation errors, return to the registration form
        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return "register"; // Corrected to "register" without the leading slash
        }

        // If there are no errors, save the new user
        userService.addUser(userDto);
        return "redirect:/users/register?success"; // Redirect after successful registration
    }

   

    // handler method to handle login request
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    // @GetMapping("/login")
    // public String login(Model model) {
    //     System.out.println("login");
    //     // model.addAttribute("loginUser", new LoginDto()); 
    //     System.out.println("leaving get login");
    //     return "login";
    // }

    // @PostMapping("/login") // Ensure this is the POST mapping for login
    // public String login(@ModelAttribute("loginUser") LoginDto loginDto, Model model) {
    //     boolean isValidUser = userService.validateUser(loginDto.getEmail(), loginDto.getPassword());
    
    //     System.out.println("post login");
    //     if (isValidUser) {
    //         // User is valid, redirect to home
    //         System.out.println("heading to home");
    //         return "redirect:/users/home";
    //     } else {
    //         // User is not valid, return to login with error message
    //         model.addAttribute("error", "Invalid email or password");
    //         System.out.println("uh uh, error somewhere :(");
    //         return "login"; // Return to the login page
    //     }
    // }
}
