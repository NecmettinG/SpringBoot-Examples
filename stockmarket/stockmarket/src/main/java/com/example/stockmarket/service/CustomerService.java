package com.example.stockmarket.service;

import com.example.stockmarket.entity.Customer;
import com.example.stockmarket.entity.Employee;
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
public class CustomerService{

    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, EmployeeRepository employeeRepository){

        this.customerRepository = customerRepository;
        this.employeeRepository = employeeRepository;

    }

    public List<Customer> getCustomers(){

        return customerRepository.findAll();
    }

    public void addNewCustomer(Customer customer){

        Optional<Customer> customerOptional = customerRepository.findCustomerByEmail(customer.getEmail());

        if (customerOptional.isPresent()){

            throw new EntityExistsException("Email already exists. Please enter another email.");
        }

        customerRepository.save(customer);
    }


    public void deleteCustomer(Long customerId){

        boolean exists = customerRepository.existsById(customerId);

        if(!exists){

            throw new IllegalStateException("There is no such customer to delete.");
        }

        customerRepository.deleteById(customerId);
    }


    @Transactional
    public void updateCustomer(Long customerId, String firstName, String lastName, String email){


        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(()-> new EntityNotFoundException("There is no such customer to update."));

        if(firstName != null && firstName.length() > 0 && !Objects.equals(customer.getFirstName(), firstName)){

            customer.setFirstName(firstName);
        }

        if(lastName != null && lastName.length() > 0 && !Objects.equals(customer.getLastName(), lastName)){

            customer.setLastName(lastName);
        }

        if(email != null && email.length() > 0 && !Objects.equals(customer.getEmail(), email)){

            Optional<Customer> customerOptional = customerRepository.findCustomerByEmail(email);

            if(customerOptional.isPresent()){

                throw new IllegalStateException("the email you entered is same with the present one");

            }

            customer.setEmail(email);
        }

    }

    @Transactional
    public void assignAdvisor(Long customerId, Long advisorId){

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(()-> new EntityNotFoundException("There is no such customer to assign an advisor."));

        Employee advisor = employeeRepository.findById(advisorId)
                .orElseThrow(()-> new EntityNotFoundException("There is no such employee to be assigned to a customer."));

        customer.setEmployee(advisor);

    }

}
