package com.example.stockmarket.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "Customer")
public class Customer {

    @Id
    @SequenceGenerator(
            name = "customer_sequence",
            sequenceName = "customer_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "customer_sequence"
    )
    private long id;
    private String firstName;
    private String lastName;

    private String email;

    @ManyToOne
    @JoinColumn(name = "employeeId")
    private Employee employee;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer") //customer is the variable name from Asset class.
    private List<Asset> assets;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer") //customer is the variable name from Order class.
    private List<Order> orders;

    public Customer() {
        //This constructor is empty on purpose for avoiding errors.
    }

    public Customer(String firstName, String lastName, String email/*, Employee employee, List<Asset> assets, List<Order> orders*/){

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        //this.employee = employee;
        //this.assets = assets;
        //this.orders = orders;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public List<Asset> getAssets() {

        return assets;
    }

    public void setAssets(List<Asset> assets) {

        this.assets = assets;
    }

    public List<Order> getOrders() {

        return orders;
    }

    public void setOrders(List<Order> orders) {

        this.orders = orders;
    }


    @Override
    public String toString() {

        String assetName = assets.stream().map(Asset::getAssetName).collect(Collectors.joining(", "));
        String orderId = orders.stream().map(Order::getIdStr).collect(Collectors.joining(", "));

        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", employee=" + employee.getEmail() +
                ", assets= [" + assetName + "]" +
                ", orders= [" + orderId + "]" +
                '}';
    }
}
