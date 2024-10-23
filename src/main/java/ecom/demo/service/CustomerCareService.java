package ecom.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ecom.demo.models.CustomerCare;
import ecom.demo.repository.CustomerCareRepo;

@Service
public class CustomerCareService {

    private final CustomerCareRepo customerCareRepo;

    @Autowired
    public CustomerCareService(CustomerCareRepo customerCareRepo) {
        this.customerCareRepo = customerCareRepo;
    }

    public int addCustomerCare(CustomerCare customerCare) {
        return customerCareRepo.addCustomerCare(customerCare);
    }

    public CustomerCare getCustomerCareById(String customerCareID) {
        return customerCareRepo.getCustomerCareById(customerCareID);
    }

    public List<CustomerCare> getAllCustomerCare() {
        return customerCareRepo.getAllCustomerCare();
    }

    public int updateCustomerCare(CustomerCare customerCare) {
        return customerCareRepo.updateCustomerCare(customerCare);
    }

    public int deleteCustomerCare(String customerCareID) {
        return customerCareRepo.deleteCustomerCare(customerCareID);
    }
}
