package com.example.stockmarket.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.Period;

@Entity
@Table(name = "StockOrder")
public class Order {

    @Id
    @SequenceGenerator(
            name = "order_sequence",
            sequenceName = "order_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "order_sequence"
    )
    private long id;

    @ManyToOne
    @JoinColumn(name = "customerId")
    private Customer customer;

    private String assetName;

    @Enumerated(EnumType.STRING)
    private Side side;

    private double size;
    private double price;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDate creationDate;

    public Order() {

    }

    public Order(String assetName, Side side, double size, double price/*, Status status, LocalDate creationDate, Customer customer*/) {

        this.assetName = assetName;
        this.side = side;
        this.size = size; //Size represents how many shares customer wants to buy.
        this.price = price; //Price represents how much customer wants to pay for per share.
        //You have to calculate total TRY amount with size * price.
        //this.status = status;
        this.creationDate = LocalDate.now();
        //this.customer = customer;

    }

    public String getIdStr(){

        return String.valueOf(this.id);
    }

    public long getId() {

        return id;
    }

    public void setId(long id) {

        this.id = id;
    }

    public Customer getCustomer() {

        return customer;
    }

    public void setCustomer(Customer customer) {

        this.customer = customer;
    }

    public String getAssetName() {

        return assetName;
    }

    public void setAssetName(String assetName) {

        this.assetName = assetName;
    }

    public Side getSide() {

        return side;
    }

    public void setSide(Side side) {

        this.side = side;
    }

    public double getSize() {

        return size;
    }

    public void setSize(double size) {

        this.size = size;
    }

    public double getPrice() {

        return price;
    }

    public void setPrice(double price) {

        this.price = price;
    }

    public Status getStatus() {

        return status;
    }

    public void setStatus(Status status) {

        this.status = status;
    }

    public LocalDate getCreationDate() {

        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {

        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customer=" + customer.getEmail() +
                ", assetName='" + assetName + '\'' +
                ", side=" + side +
                ", size=" + size +
                ", price=" + price +
                ", status=" + status +
                ", creationDate=" + creationDate +
                '}';
    }
}
