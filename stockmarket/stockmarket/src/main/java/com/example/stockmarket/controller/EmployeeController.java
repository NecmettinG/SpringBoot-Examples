package com.example.stockmarket.controller;

import com.example.stockmarket.entity.Employee;
import com.example.stockmarket.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.stockmarket.entity.Customer;

import java.util.List;

@RestController
@RequestMapping(path = "employee")
public class EmployeeController{

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService){

        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Employee> getEmployees(){

        return employeeService.getAllEmployees();
    }

    @PostMapping
    public void addEmployee(@RequestBody Employee employee){

        employeeService.addNewEmployee(employee);
    }

    @DeleteMapping(path = "{employeeId}")
    public void deleteEmployee(@PathVariable("employeeId") Long employeeId){

        employeeService.deleteEmployee(employeeId);
    }

    @PutMapping(path = "{employeeId}")
    public void updateEmployee(@PathVariable("employeeId") Long employeeId,
                               @RequestParam(required = false) String firstName,
                               @RequestParam(required = false) String lastName,
                               @RequestParam(required = false) String email){

        employeeService.updateEmployee(employeeId, firstName, lastName, email);
    }

    @PutMapping(path = "manipulate")
    public void manipulateCustomer(@RequestParam(required = false) Long customerId,
                                   @RequestParam(required = false) String firstName,
                                   @RequestParam(required = false) String lastName,
                                   @RequestParam(required = false) String email){

        employeeService.manipulateCustomer(customerId, firstName, lastName, email);
    }

    @GetMapping(path = "get customers")
    public List<Customer> getCustomers(){

        return employeeService.getAllCustomers();
    }

}
