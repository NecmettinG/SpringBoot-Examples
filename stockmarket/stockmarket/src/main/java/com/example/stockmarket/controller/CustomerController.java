package com.example.stockmarket.controller;

import com.example.stockmarket.entity.Customer;
import com.example.stockmarket.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "customer")
public class CustomerController{

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService){

        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> getCustomers(){

        return customerService.getCustomers();
    }

    @PostMapping
    public void addCustomer(@RequestBody Customer customer){

        customerService.addNewCustomer(customer);
    }

    @DeleteMapping(path = "{customerId}")
    public void deleteCustomer(@PathVariable("customerId") Long customerId){

        customerService.deleteCustomer(customerId);
    }

    @PutMapping(path = "{customerId}")
    public void updateCustomer(@PathVariable("customerId") Long customerId,
                               @RequestParam(required = false) String firstName,
                               @RequestParam(required = false) String lastName,
                               @RequestParam(required = false) String email){

        customerService.updateCustomer(customerId, firstName, lastName, email);
    }

    @PutMapping(path = "{customerId}/assign")
    public void assignEmployee(@PathVariable("customerId") Long customerId,
                               @RequestParam(required = false) Long employeeId){

        customerService.assignAdvisor(customerId, employeeId);
    }

}
