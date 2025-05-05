package com.example.stockmarket.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "Employee")
public class Employee {

    @Id
    @SequenceGenerator(
            name = "employee_sequence",
            sequenceName = "employee_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "employee_sequence"
    )
    private long id;

    private String firstname;
    private String lastname;
    private String email;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee")
    private List<Customer> customers;



    public Employee() {

    }

    public Employee(String firstname, String lastname, String email/*, List<Customer> customers*/){
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        //this.customers = customers;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    @Override
    public String toString() {

        String customerEmails = customers.stream().map(Customer::getEmail).collect(Collectors.joining(", "));

        return "Employee{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", customersEmails=[" + customerEmails + "]" +
                '}';
    }
}
