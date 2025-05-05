package com.example.stockmarket.repository;

import com.example.stockmarket.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {



    Optional<Customer> findCustomerByEmail(String email);
    Optional<Customer> findCustomerById(Long id);
    Customer findCustomerByFirstName(String name);

}
