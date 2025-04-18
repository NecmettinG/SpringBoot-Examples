package com.example.stockmarket.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Asset")
public class Asset {

    @Id
    @SequenceGenerator(
            name = "asset_sequence",
            sequenceName = "asset_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "asset_sequence"
    )
    private long id;

    @ManyToOne
    @JoinColumn(name = "customerId")
    private Customer customer;

    private String assetName;
    private double size;
    private double usableSize; //I will use size = 1 and usableSize = TRY amount on TRY. (Bunu anlamadim, orderdaki price sayicam bunu.)

    public Asset() {

    }

    public Asset(Customer customer, String assetName, double size, double usableSize) {
        this.customer = customer;
        this.assetName = assetName;
        this.size = size;
        this.usableSize = usableSize;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public double getUsableSize() {
        return usableSize;
    }

    public void setUsableSize(double usableSize) {
        this.usableSize = usableSize;
    }

    @Override
    public String toString() {
        return "Asset{" +
                "id=" + id +
                ", customer=" + customer.getEmail() +
                ", assetName='" + assetName + '\'' +
                ", size=" + size +
                ", usableSize=" + usableSize +
                '}';
    }
}
