package ecom.demo.controllers;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ecom.demo.models.Address;
import ecom.demo.models.User;
import ecom.demo.service.AddressService;
import ecom.demo.service.UserService;

@Controller
@RequestMapping("/address")
public class AddressController {

    private final AddressService addressService;
      private final UserService userService;

    @Autowired
    public AddressController( UserService userService, AddressService addressService) {
        this.addressService = addressService;
        this.userService=userService;
    }

    @GetMapping("/add/{personID}")
    public String showAddAddressForm(@PathVariable UUID personID, Model model) {
        Address address = new Address();
        address.setPersonID(personID); // Set the personID from the URL
        model.addAttribute("address", address);
        return "add-address";
    }

    @PostMapping("/add/{personID}")
    public String addAddress(@PathVariable UUID personID, @ModelAttribute Address address, Model model) {
        address.setPersonID(personID); // Ensure personID is set from the URL
        int result = addressService.addAddress(address);
        if (result > 0) {
            model.addAttribute("successMessage", "Address added successfully!");
        } else {
            model.addAttribute("errorMessage", "Failed to add address.");
        }
        return "redirect:/order/submit";
    }

    @GetMapping("/all/{personID}")
    public String showAllAddresses(@PathVariable String personID, Model model) {
        List<Address> addresses = addressService.getAddressesByPersonID(personID);
        model.addAttribute("addresses", addresses);
        return "address-list";
    }

 
    @PostMapping("/removeFromAddress/{personID}")
    public String removeFromAddress(@RequestParam("selectedAddress") UUID addressID, Authentication authentication) {
        if (authentication != null) {
            User currentUser = userService.getUserByEmail(authentication.getName());
            
            addressService.removeFromAddress(currentUser.getUserID(), addressID); // Remove address
            System.out.println("REMOVED FROM ADDRESSES!!!!!!!!");
            return "redirect:/address/all/" + currentUser.getUserID(); // Redirect to user's address page
        }
        return "404"; // Return 404 if the user is not authenticated
    }

@GetMapping("/edit/{addressID}/{personID}")
public String showEditForm(@PathVariable UUID addressID, @PathVariable UUID personID, Model model) {
    Optional<Address> addressOpt = addressService.getAddressByID(addressID);
    if (addressOpt.isPresent()) {
        model.addAttribute("address", addressOpt.get()); // Add the existing address to the model
        model.addAttribute("personID", personID);
        return "edit-address"; // Return the edit address page
    }
    return "redirect:/address/all/{personID}"; // Redirect if address not found
}


@PostMapping("/edit/{addressID}/{personID}")
public String updateAddress(
        @PathVariable UUID addressID, 
        @PathVariable String personID,
        @ModelAttribute("address") Address address,
        Model model) {

    // Fetch the existing address based on addressID
    Optional<Address> addressOpt = addressService.getAddressByID(addressID);
    System.out.println("Post mapping edit address");
    
    if (addressOpt.isPresent()) {
        Address existingAddress = addressOpt.get();
        
        // Update the fields of the existing address
        existingAddress.setStreetName(address.getStreetName());
        existingAddress.setCityName(address.getCityName());
        existingAddress.setDistrictName(address.getDistrictName());
        existingAddress.setPincode(address.getPincode());
        existingAddress.setCountryName(address.getCountryName());

        // Save the updated address to the database
        addressService.updateAddress(existingAddress);
        
        // Redirect to the list of addresses for the given person
        return "redirect:/address/all/" + personID;
    }
    
    // If address is not found, redirect back to the list of addresses
    return "redirect:/address/all/" + personID;
}


}