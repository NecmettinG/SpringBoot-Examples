package com.example.stockmarket.service;

import com.example.stockmarket.entity.Asset;
import com.example.stockmarket.entity.Customer;
import com.example.stockmarket.entity.Employee;
import com.example.stockmarket.repository.AssetRepository;
import com.example.stockmarket.repository.CustomerRepository;
import com.example.stockmarket.repository.EmployeeRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AssetService{

    private final AssetRepository assetRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public AssetService(AssetRepository assetRepository, CustomerRepository customerRepository) {

        this.assetRepository = assetRepository;
        this.customerRepository = customerRepository;
    }

    public List<Asset> getAssets(Long customerId){ //getting assets according to customer id.

        return assetRepository.findAssetByCustomerId(customerId);
    }

    public void addAssets(Asset asset){

        Optional <Asset> assetOptional = assetRepository.findAssetByAssetNameAndCustomerId(asset.getAssetName(),
                asset.getCustomer().getId());

        Optional <Customer> customerOptional = customerRepository.findCustomerById(asset.getCustomer().getId());

        if(assetOptional.isPresent()){

            throw new EntityExistsException("The customer already has that asset.");
        }

        if(customerOptional.isEmpty()){

            throw new EntityNotFoundException("The customer does not exist. Failed to create asset.");
        }

        assetRepository.save(asset);
    }

    //The initial addition of assets will be performed in a config class.
}
