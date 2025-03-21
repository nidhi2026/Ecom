package ecom.demo.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ecom.demo.dto.UserDto;
import ecom.demo.models.Product;
import ecom.demo.models.Supplier;
import ecom.demo.repository.SupplierRepo;

@Service
public class SupplierService {

    private final SupplierRepo supplierRepo;

    @Autowired
    public SupplierService(SupplierRepo supplierRepo) {
        this.supplierRepo = supplierRepo;
    }

    public int addSupplier(UserDto user) {
        Supplier supplier = new Supplier();
        
        supplier.setSupplierID(UUID.randomUUID());
        supplier.setFName(user.getFName());
        supplier.setMName(user.getMName());
        supplier.setLName(user.getLName());
        supplier.setPhone(user.getPhone());
        supplier.setEmail(user.getEmail());
       
        return supplierRepo.addSupplier(supplier);
    }

    // Method to get supplier by ID
    public Supplier getSupplierByID(UUID supplierID) {
        return supplierRepo.getSupplierById(supplierID);
    }

    public List<Supplier> getAllSuppliers() {
        return supplierRepo.getAllSuppliers();
    }

    public int updateSupplier(Supplier supplier) {
        return supplierRepo.updateSupplier(supplier);
    }

    public int deleteSupplier(UUID supplierID) {
        return supplierRepo.deleteSupplier(supplierID);
    }

    public List<Product> getProductsBySupplierId(UUID supplierID) {
        return supplierRepo.getProductsBySupplierId(supplierID);
    }
}
