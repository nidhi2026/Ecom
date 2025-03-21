package ecom.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import ecom.demo.models.CustomerCare;
import ecom.demo.service.CustomerCareService;

@Controller
public class CustomerCareController {
    
    private final CustomerCareService customerCareService;

    @Autowired
    public CustomerCareController(CustomerCareService customerCareService) {
        this.customerCareService = customerCareService;
    }

    @GetMapping("/customerCare")
    public String showCustomerCareForm(Model model) {
        model.addAttribute("customerCare", new CustomerCare());
        System.out.println("herte");
        return "customerCareForm"; // Name of the Thymeleaf template
    }

    @PostMapping("/customerCare")
    public String submitCustomerCareForm(CustomerCare customerCare) {
        System.out.println("here");
        customerCareService.addCustomerCare(customerCare);
        return "customerCareSuccess"; // Redirect to a success page
    }
    
}
