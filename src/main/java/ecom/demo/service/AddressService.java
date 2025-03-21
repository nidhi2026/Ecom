package ecom.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ecom.demo.models.Address;
import ecom.demo.repository.AddressRepo;
import ecom.demo.repository.CartRepo;
import ecom.demo.repository.ProductRepo;

@Service
public class AddressService {
    
    private final CartRepo cartRepo;
    private final ProductRepo productRepo;
    private final AddressRepo addressRepo;
    
    @Autowired
    public AddressService(CartRepo cartRepo,  ProductRepo productRepo, AddressRepo addressRepo) {
        this.cartRepo = cartRepo;
        this.productRepo =  productRepo;
        this.addressRepo =  addressRepo;
    }


    public int addAddress(Address address) {
        address.setAddressID(UUID.randomUUID()); // Generate a unique address ID
        return addressRepo.addAddress(address);
    }

    public Optional<Address> getAddressByID(UUID addressID) {
        return addressRepo.getAddressByID(addressID);
    }

    public Optional<Address> getAddressByUserID(String personID) {
        return addressRepo.getAddressByUserID(personID);
    }

    public List<Address> getAllAddresses() {
        return addressRepo.getAllAddresses();
    }

    public int updateAddress(Address address) {
        return addressRepo.updateAddress(address);
    }

   

    public List<Address> getAddressesByPersonID(String personID) {
        return addressRepo.getAddressesByPersonID(personID); // Delegate to repository
    }

    
    public List<Address> getAddresses(UUID userID) {
        return addressRepo.getAllAddresses(userID);
    }

    public int removeFromAddress(UUID userID, UUID addressID) {
        // Fetch the address by ID
        Optional<Address> address = addressRepo.getAddressByID(addressID);
    
        if (address.isEmpty()) {
            System.out.println("Address not found!");
            return 0; // If address is not found, return 0
        }
    
        // Check if the address belongs to the user
        if (!address.get().getPersonID().equals(userID.toString())) {
            System.out.println("User does not own this address!");
            return 0; // If the address does not belong to the user, return 0
        }
    
        // Proceed to remove the address
        return addressRepo.removeAddress(addressID);  // Correct method call
    }
}    