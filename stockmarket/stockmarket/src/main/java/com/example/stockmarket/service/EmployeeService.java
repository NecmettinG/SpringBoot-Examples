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
public class EmployeeService{

    private final EmployeeRepository employeeRepository;
    private final CustomerRepository customerRepository;
    private final CustomerService customerService;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, CustomerRepository customerRepository, CustomerService customerService) {

        this.employeeRepository = employeeRepository;
        this.customerRepository = customerRepository;
        this.customerService = customerService;
    }

    public List<Employee> getAllEmployees(){

        return employeeRepository.findAll();
    }

    public void addNewEmployee(Employee employee){

        Optional<Employee> employeeOptional = employeeRepository.findEmployeeByEmail(employee.getEmail());

        if(employeeOptional.isPresent()){

            throw new EntityExistsException("Employee already exists.");
        }

        employeeRepository.save(employee);
    }

    public void deleteEmployee(Long employeeId){

        boolean exists = employeeRepository.existsById(employeeId);

        if(!exists){

            throw new EntityNotFoundException("Employee not found with this id.");
        }

        employeeRepository.deleteById(employeeId);
    }

    @Transactional
    public void updateEmployee(Long employeeId, String firstName, String lastName, String email){

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("There is no such employee to update."));

        if(firstName != null && firstName.length() > 0 && !Objects.equals(employee.getFirstname(), firstName)){

            employee.setFirstname(firstName);
        }

        if(lastName != null && lastName.length() > 0 && !Objects.equals(employee.getLastname(), lastName)){

            employee.setLastname(lastName);
        }

        if(email != null && email.length() > 0 && !Objects.equals(employee.getEmail(), email)){

            Optional<Employee> employeeOptional = employeeRepository.findEmployeeByEmail(email);

            if(employeeOptional.isPresent()){

                throw new IllegalStateException("the email you entered is same with the present one");

            }

            employee.setEmail(email);
        }
    }

    @Transactional
    public void manipulateCustomer(Long customerId, String firstName, String lastName, String email){

        customerService.updateCustomer(customerId, firstName, lastName, email);
    }

    public List<Customer> getAllCustomers(){

        return customerService.getCustomers();
    }

}
