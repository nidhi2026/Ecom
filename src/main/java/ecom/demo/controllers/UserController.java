package ecom.demo.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

import ecom.demo.models.User;
import ecom.demo.service.UserService;

@Controller
@RequestMapping("/base")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    

    // public String requestMethodName(@RequestParam String param) {
    //     return new String();
    // }

    // @GetMapping("/") // Root endpoint
    // public String basePage() {
    //     return "base"; // This should return base.html
    // }
    
    @GetMapping("/")
    public String basePage(HttpSession session, Authentication authentication, Model model) {
        User currentUser = userService.getUserByEmail(authentication.getName());
        // if (currentUser == null) {
        //     return "redirect:/users/login";  // Redirect to login if user is not in session
        // }
        model.addAttribute("user", currentUser); // Use "loginUser" as the key
        System.out.println(currentUser.getFName());
        return "base"; // Ensure "base.html" exists
    }

    // @GetMapping("/base")
    // public String basePage(HttpSession session) {
    // User user = (User) session.getAttribute("user");
    // if (user == null) {
    //     return "redirect:/login";  // Redirect to login if user is not in session
    // }

    // // Access user properties only if not null
    // String firstName = user.getFName();
    // model.addAttribute("firstName", firstName);
    // return "base";
// }


    @GetMapping("/profile")
    public String profile() {
        return "login"; 
    }

    @GetMapping("/wishlist")
    public String wishlist() {
        return "base"; // returns wishlist.html
    }
}