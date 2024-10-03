package ecom.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ecom.demo.models.CustomerCare;
import ecom.demo.models.User;
import ecom.demo.service.CustomerCareService;
import ecom.demo.service.UserService;



@RestController
@RequestMapping("/api")
public class check {
    @Autowired
    private UserService userService;

    @Autowired
    private CustomerCareService customerCareService;

    // get
    @GetMapping(path="/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // add
    @PostMapping(path="/users/add")
    public String addUsers(@RequestBody User user) {
        userService.addUser(user);
        return "added user";
    }

    @PutMapping("/update")
    public String updateUser(@RequestBody User user) {
        System.out.println(user.getEmail());
        System.out.println(user.getPhone());
        userService.updateUser(user);
        return "User updated successfully!";
    }

    // Delete a user by ID
    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return "User deleted successfully!";
    }

    // get by email
    @GetMapping("/getByEmail/{email}")
    public User getUserByEmail(@PathVariable String email) {
        User x = userService.getUserByEmail(email);
        if(x!=null) {
            return x;
        }
        return null;
    }

    @GetMapping(path="/customerCare")
    public List<CustomerCare> getAllCustomerCare() {
        return customerCareService.getAllCustomerCare();
    }

    @PostMapping(path="/customerCare/add")
    public String addCustomerCare(@RequestBody CustomerCare customerCare) {
        customerCareService.addCustomerCare(customerCare);
        return "Added customerCare request successfuly!";
    }
    
    
}
