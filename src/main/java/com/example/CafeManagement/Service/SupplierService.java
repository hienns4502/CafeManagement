package com.example.CafeManagement.Service;

import com.example.CafeManagement.Entity.Equipment;
import com.example.CafeManagement.Entity.Supplier;
import com.example.CafeManagement.Repository.SupplierRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,  makeFinal = true)
@Service
public class SupplierService {

    SupplierRepository supplierRepository;

    public List<Supplier> getSupplierList() {
        return supplierRepository.findAll();
    }

    public void createSupplier(Supplier supplier) {
        supplierRepository.save(supplier);
    }

    public void deleteSupplier(String supplierId) {
        Supplier foundSupplier = supplierRepository.findById(supplierId).orElseThrow(()->new RuntimeException("Supplier not found"));
        supplierRepository.delete(foundSupplier);
    }

    public Supplier getSupplier(String supplierId) {
        Supplier foundSupplier = supplierRepository.findById(supplierId).orElseThrow(()->new RuntimeException("Supplier not found"));
        return foundSupplier;
    }

    public void updateSupplier(Supplier supplier) {
        String id = supplier.getId();
        Supplier foundSupplier =  supplierRepository.findById(id).orElseThrow(()->new RuntimeException("Supplier not found"));
        supplierRepository.save(supplier);
    }

    public List<Supplier> getSupplierByName(String key) {
        List<Supplier> list =  supplierRepository.findByTenNhaCungCapContainingIgnoreCase(key);
        return list;
    }

}
